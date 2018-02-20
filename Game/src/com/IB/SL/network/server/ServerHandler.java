package com.IB.SL.network.server;

import java.util.HashMap;
import java.util.UUID;

import com.IB.SL.Boot;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.level.Level;
import com.IB.SL.level.worlds.Tiled_Level;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ServerHandler extends SimpleChannelInboundHandler<String>
{

	private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	private final HashMap<String, String> users = new HashMap<>();
	
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
			Channel incoming = ctx.channel();
			System.out.println("[SERVER] - " + incoming.remoteAddress() + " has joined\n");
			for(Channel channel : channels) {
				channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " has joined\n");
				//TODO: Send current .xml level
			}
			addUser(ctx);
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
			Channel incoming = ctx.channel();
			System.out.println("[SERVER] - " + incoming.remoteAddress() + " has left\n");
			for(Channel channel : channels) {
				channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " has left\n");
			}
			removeUser(ctx);
	}

	public void addUser(ChannelHandlerContext ctx)
		{
			channels.add(ctx.channel());
			ctx.channel().writeAndFlush("You have successfully connected to the server!\n");
	        UUID uuid = UUID.randomUUID();
	        String UUID = uuid.toString();
			users.put(ctx.channel().remoteAddress().toString(), UUID); // TODO ADD AUTOMATED USER ID
			
			ctx.channel().writeAndFlush("LEV|PATH=" + ((Tiled_Level)Boot.getLevel()).path + "@x=" + ((Tiled_Level)Boot.getLevel()).spawnpoint.x() + ",y=" + ((Tiled_Level)Boot.getLevel()).spawnpoint.y() + "\n");
			
			for (Entity e : Boot.getLevel().players) {
				if (!e.removed) {
				ctx.channel().writeAndFlush(Level.entityStringBuilder(e) + "\n");
				}
			}
		}

	public void removeUser(ChannelHandlerContext ctx)
		{
			for(Player p : Boot.getLevel().players) {
				if (p.UUID.equals(users.get(ctx.channel().remoteAddress().toString()))) {
					for(Channel channel : channels) {
						channel.writeAndFlush("REM|id=" + p.UUID + "\n");
					}
					p.remove();
				}
			}
			users.remove(ctx.channel().remoteAddress().toString());
			channels.remove(ctx.channel());
		}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
		{
			Channel incoming = ctx.channel();
			
			System.out.println("[" + incoming.remoteAddress() + "] " + msg + "\n");
			
			//ADD|eid=0&id=25@x=0,y=1
			if(msg.startsWith("ADD|")) {
				addEntity(ctx, msg);
			}
			
			if(msg.startsWith("POS|")) {
				for(Channel channel : channels) {
					if (!channel.remoteAddress().equals(ctx.channel().remoteAddress())) {
						channel.writeAndFlush(msg + "\n");
					}
				}
				
				String toFind = (msg.substring(msg.indexOf("id=") + 3, msg.indexOf("@")));
				double x = Double.parseDouble(msg.substring(msg.indexOf("x=") + 2, msg.indexOf(",")));
				double y = Double.parseDouble(msg.substring(msg.indexOf("y=") + 2));
				
				for (Player p : Boot.getLevel().players) {
					if (p.getUUID().equals(toFind)) {
						p.pos().set(x, y);
						break;
					}
				}
			}
			
			if(msg.startsWith("VEL|")) {
				for(Channel channel : channels) {
					if (!channel.remoteAddress().equals(ctx.channel().remoteAddress())) {
						channel.writeAndFlush(msg + "\n");
					}
				}
				
				String toFind = (msg.substring(msg.indexOf("id=") + 3, msg.indexOf("@")));
				double x = Double.parseDouble(msg.substring(msg.indexOf("x=") + 2, msg.indexOf(",")));
				double y = Double.parseDouble(msg.substring(msg.indexOf("y=") + 2));
				
				for (Player p : Boot.getLevel().players) {
					if (p.getUUID().equals(toFind)) {
						p.vel().set(x, y);
						break;
					}
				}
			}
			
			for (Channel channel : channels) {
				channel.writeAndFlush("[" + incoming.remoteAddress() + "] " + msg + "\n");
			}
		}
	
	public void addEntity(ChannelHandlerContext ctx, String msg)
		{
			Entity e = Level.createEntity(msg);
			Boot.get().getLevel().add(e);
			if (e instanceof Player) {
			e.UUID = this.users.get(ctx.channel().remoteAddress().toString());
			}
			
			for (Channel channel : channels) {
				if (!channel.remoteAddress().equals(ctx.channel().remoteAddress())) {
					channel.writeAndFlush(Level.entityStringBuilder(e) + "\n");
				} else {
					channel.writeAndFlush("REG|id=" + e.UUID + "\n");
				}
			}
		}
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        cause.printStackTrace();
        ctx.close();
    }
	
}
