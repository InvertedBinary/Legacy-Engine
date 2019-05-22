package com.IB.LE2.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class GameServer
{
	public final int port;

	public GameServer(int port)
		{
			System.out.println("Attempting a server on port " + port + "..");
			
			this.port = port;
		}

	EventLoopGroup bossGroup = new NioEventLoopGroup();
	EventLoopGroup workerGroup = new NioEventLoopGroup();

	public void run() throws Exception {
           try {
        	   ServerBootstrap bs = new ServerBootstrap()
        			   .group(bossGroup, workerGroup)
        			   .channel(NioServerSocketChannel.class)
                       //.option(ChannelOption.SO_KEEPALIVE, true)
         	           //.handler(new LoggingHandler(LogLevel.INFO))
        			   .childHandler(new ServerInitializer());
        	   
        	   bs.bind(port).sync().channel().closeFuture().sync();
        	   
           } finally {
        	   bossGroup.shutdownGracefully();
        	   workerGroup.shutdownGracefully();
           }
    
    }

}
