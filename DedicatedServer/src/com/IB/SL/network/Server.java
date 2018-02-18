package com.IB.SL.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server
{
	public final int port;

	public Server(int port)
		{
			System.out.println("Attempting a server on port " + port + "..");

			this.port = port;
		}

    public void run() throws Exception {
           EventLoopGroup bossGroup = new NioEventLoopGroup();
           EventLoopGroup workerGroup = new NioEventLoopGroup();
    
           try {
        	   ServerBootstrap bs = new ServerBootstrap()
        			   .group(bossGroup, workerGroup)
        			   .channel(NioServerSocketChannel.class)
        			   .childHandler(new ServerInitializer());
        	   
        	   bs.bind(port).sync().channel().closeFuture().sync();
           
           } finally {
        	   bossGroup.shutdownGracefully();
        	   workerGroup.shutdownGracefully();
           }
    
    }

}
