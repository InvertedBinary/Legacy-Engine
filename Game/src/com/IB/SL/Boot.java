package com.IB.SL;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import com.IB.SL.AlphaLWJGL.OGLEngine;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.level.Level;
import com.IB.SL.network.Client;
import com.IB.SL.network.server.GameServer;

public class Boot
{

	public static String title = "Legacy Engine [Build 1 : 10/31/17]";
	private static Game g;
	private static OGLEngine GameOGL;
	private static GameServer s;
	public static Client c;
	
	private static int port = 7381;
	public static String host = "localhost";
	public static HashMap<String, Boolean> launch_args;
	public static boolean isConnected = false;
	public static boolean drawDebug = false;
	
	
	public static void main(String[] args)
		{
			launch_args = new HashMap<String, Boolean>();

			System.out.println("----------- R U N - T I M E   A R G S  ------------");
			for (String s : args) {
				launch_args.put(s, true);
				System.out.println(s + ": " + true);
			}
			System.out.println("---------------------------------------------------");
			
			
			if (!launch_args.containsKey("-mode_dedi")) {
				c = new Client(host, 7381);

				if (launch_args.containsKey("-doconnect")) {
					tryConnect(true);
				}
			} else {
				tryServer();
			}
			
			tryLaunchGame();
		}
	
	public static void tryLaunchGame()
	{
			//g = new Game(title);
			//g.Launch(g);

			GameOGL = new OGLEngine(title);
	}
	
	
	public static void tryServer()
		{
			s = new GameServer(Boot.port);
			try {
				s.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public static void tryConnect(boolean useHostFile)
		{
		if (useHostFile) {
			try {
				String path = "./server.txt";
				FileInputStream fis = new FileInputStream(path);
				BufferedReader in = new BufferedReader(new InputStreamReader(fis));	
				host = in.readLine();
				in.close();
				System.out.println("Host file found! Will attempt a connection to: " + host);
			} catch (IOException e1) {
				e1.printStackTrace();
				host = "localhost";
				System.out.println("Host file is corrupt, using localhost!");
			}
		}
			try {
				c = new Client(host, 7381);
		        c.startClient();
			} catch (Exception e) {
				log("Unsuccessful Connection Attempt.. is the server running?", true);
			}
		}
	
	public static Game get() { return g; }
	public static Level getLevel() { return get().getLevel(); }
	public static Player getPlayer() { return get().getPlayer(); }

	public static void setWindowIcon(String path)
		{
			g.frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Game.class.getResource(path)));
		}

	public static Cursor setMouseIcon(String path)
		{
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = null;
			image = Toolkit.getDefaultToolkit().getImage(Game.class.getResource(path));

			Point hotspot = new Point(0, 0);
			Cursor cursor = toolkit.createCustomCursor(image, hotspot, "Stone");
			g.frame.setCursor(cursor);
			return cursor;
		}

	public static void centerMouse()
		{
			int centreFrameX = g.frame.getX() + (g.frame.getWidth() / 2);
			int centreFrameY = g.frame.getY() + (g.frame.getHeight() / 2);
			moveMouse(new Point(centreFrameX, centreFrameY));
		}

	public static void moveMouse(Point p)
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] gs = ge.getScreenDevices();
			for (GraphicsDevice device : gs) {
				GraphicsConfiguration[] configurations = device.getConfigurations();
				for (GraphicsConfiguration config : configurations) {
					Rectangle bounds = config.getBounds();
					if (bounds.contains(p)) {
						Point b = bounds.getLocation();
						Point s = new Point(p.x - b.x, p.y - b.y);
						try {
							Robot r = new Robot(device);
							r.mouseMove(s.x, s.y);
						} catch (AWTException e) {
							e.printStackTrace();
						}

						return;
					}
				}
			}
			return;
		}

	public void setMousePos(int framex, int framey)
		{
			moveMouse(new Point(framex, framey));
		}

	public static int randInt(int min, int max)
		{
			return ThreadLocalRandom.current().nextInt(min, max + 1);
		}

	public static double randDouble(int min, int max)
		{
			return ThreadLocalRandom.current().nextDouble(min, max);
		}

	public static void log(String text, boolean important)
		{
			if (!important) {
				System.out.println(" >> " + text);
			} else {
				System.err.println(" >> ALERT: " + text);
			}
		}

	public static void log(String text, String outboundClass, boolean important)
		{
			if (!important) {
				System.out.println(outboundClass + " >> " + text);
			} else {
				System.err.println(outboundClass + " >> ALERT: " + text);
			}
		}
}
