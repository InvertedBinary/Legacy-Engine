package com.IB.LE2.network.game.client;

import com.IB.LE2.Boot;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.level.Level;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ClientHandler extends SimpleChannelInboundHandler<String>
{
	ChannelHandlerContext ctx;

	/*public void sendData(String message)
		{
			if (ctx != null) {
				ChannelFuture cf = ctx.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
				if (!cf.isSuccess()) {
					Boot.log("Failed to send data!: " + cf.cause(), true);
				}
			} else {
				Boot.log("Error, attemping to send data before the CTX has been initialized..", true);
			}
		}*/
	
	public void sendData(String data)
		{
			if (ctx != null) {
				ctx.channel().writeAndFlush(Unpooled.copiedBuffer(data, CharsetUtil.UTF_8));
			}
			
		}

	public void sendMessage(String msg)
		{
			sendData(msg + "\r\n");
		}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, String msg) throws Exception
		{
			System.out.println(msg);
			
			if(msg.startsWith("ADD|")) {
				Boot.get().getLevel().add(Level.createEntity(msg));
			}
			
			if(msg.startsWith("REG|")) { //REG|id=0
				Boot.get().getPlayer().UUID = msg.substring(msg.indexOf("id=") + 3);
				Boot.Client.id = Boot.get().getPlayer().UUID;
				Boot.log("Connection verified, you are ID: " + Boot.get().getPlayer().UUID, true);
			}
			
			if(msg.startsWith("REM|")) {
				String toFind = msg.substring(msg.indexOf("id=") + 3);
				for(Player p : Boot.getLevel().players) {
					if (p.getUUID().equals(toFind)) {
					p.remove();
					}
				}
			}
			
			if (msg.startsWith("LEV|")) {
				String TILED_PATH = msg.substring(msg.indexOf("PATH=") + 5, msg.indexOf("@"));
				double x = Double.parseDouble(msg.substring(msg.indexOf("x=") + 2, msg.indexOf(",")));
				double y = Double.parseDouble(msg.substring(msg.indexOf("y=") + 2));
				
				Boot.get().getPlayer().setPositionTiled(x, y, TILED_PATH, false);
			}
			
			if (msg.startsWith("POS|")) {
				String toFind = (msg.substring(msg.indexOf("id=") + 3, msg.indexOf("@")));
				if (!toFind.equals(Boot.get().getPlayer().UUID)) {
					double x = Double.parseDouble(msg.substring(msg.indexOf("x=") + 2, msg.indexOf(",")));
					double y = Double.parseDouble(msg.substring(msg.indexOf("y=") + 2));

					for (Player p : Boot.getLevel().players) {
						if (p.getUUID().equals(toFind)) {
							p.pos().set(x, y);
							break;
						}
					}
				}
			}

			if (msg.startsWith("VEL|")) {
				String toFind = (msg.substring(msg.indexOf("id=") + 3, msg.indexOf("@")));
				if (!toFind.equals(Boot.get().getPlayer().UUID)) {
					double x = Double.parseDouble(msg.substring(msg.indexOf("x=") + 2, msg.indexOf(",")));
					double y = Double.parseDouble(msg.substring(msg.indexOf("y=") + 2));

					for (Player p : Boot.getLevel().players) {
						if (p.getUUID().equals(toFind)) {
							p.vel().set(x, y);
							break;
						}
					}
				}
			}
		}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
		{
			this.ctx = ctx;
		}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
		{
			cause.printStackTrace();
			ctx.close();
		}
}
