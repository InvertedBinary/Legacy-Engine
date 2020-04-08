package com.IB.LE2.network.game.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.IB.LE2.Boot;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GameClient implements Runnable
{

	private final String host;
	private final int port;
	private boolean running = false;
    private final ClientHandler clientHandler = new ClientHandler();
	private ExecutorService executor = null;
	
	public String id = "-1";

	
	public GameClient(String host, int port)
		{
			this.host = host;
			this.port = port;
		}
	
	public synchronized void startClient()
		{
			if (!running) {
				executor = Executors.newFixedThreadPool(1);
				executor.execute(this);
				running = true;
			}
		}

	public void sendMessage(String msg)
		{
			if (Boot.isConnected) {
			clientHandler.sendMessage(msg);
			}
		}
	
	
	public synchronized boolean stopClient()
		{
			boolean bReturn = true;
			if (running) {
				if (executor != null) {
					executor.shutdown();
					try {
						executor.shutdownNow();
						if (executor.awaitTermination(calcTime(10, 0.66667), TimeUnit.SECONDS)) {
							if (!executor.awaitTermination(calcTime(10, 0.33334), TimeUnit.SECONDS)) {
								bReturn = false;
							}
						}
					} catch (InterruptedException ie) {
						executor.shutdownNow();
						Thread.currentThread().interrupt();
					} finally {
						executor = null;
					}
				}
				running = false;
			}
			return bReturn;
		}

	private long calcTime(int nTime, double dValue)
		{
			return (long) ((double) nTime * dValue);
		}
	
	@Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline(); 
                    pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new StringEncoder());
                    pipeline.addLast(clientHandler);
                }
            });

            ChannelFuture f = b.connect(host, port).sync();
            Boot.isConnected = true;
            //Channel channel = bs.connect(host, port).sync().channel();
			//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
            //sendData("TEST" + "\r\n");
            f.channel().closeFuture().sync();
			//while(running) {
				//System.out.println("sending a message:");
				//sendData("TEST" + "\r\n");
				/*if (Boot.get().getPlayer() != null) {
				channel.writeAndFlush("X:" + Boot.get().getPlayer().vel().x + ",Y:" + Boot.get().getPlayer().vel().y);
				}*/
			//}
        } catch (InterruptedException ex) {
        } finally {
        	Boot.log("Reached client finally..", true);
            workerGroup.shutdownGracefully();
            Boot.isConnected = false;
        }
    }
	}
