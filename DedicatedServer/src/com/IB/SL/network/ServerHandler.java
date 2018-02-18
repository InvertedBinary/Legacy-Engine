package com.IB.SL.network;

import java.util.HashMap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ServerHandler extends SimpleChannelInboundHandler<String>
{

	private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	private static final HashMap<String, Long> users = new HashMap<>();
	
	
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
	
	public void addUser(ChannelHandlerContext ctx) {
			channels.add(ctx.channel());
			users.put(ctx.channel().remoteAddress().toString(), 12345L); //TODO ADD AUTOMATED USER ID
	}
	
	public void removeUser(ChannelHandlerContext ctx) {
			channels.remove(ctx.channel());
			users.remove(ctx.channel().remoteAddress().toString());
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception
		{
			Channel incoming = ctx.channel();
			
			System.out.println("[" + incoming.remoteAddress() + "] " + message + "\n");
			for (Channel channel : channels) {
				channel.writeAndFlush("[" + incoming.remoteAddress() + "] " + message + "\n");
			}
		}
	

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        cause.printStackTrace();
        ctx.close();
    }
	
}
