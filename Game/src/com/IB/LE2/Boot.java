package com.IB.LE2;

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
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import com.IB.LE2._GL.AlphaLWJGL.OGL_TEST;
import com.IB.LE2._GL.GL_Real.GL_Main;
import com.IB.LE2.network.game.client.GameClient;
import com.IB.LE2.network.game.server.GameServer;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.level.Level;
import com.moremeridian.nc.net.client.Client;

public class Boot {
	public static int width = 640; // 300 //520
	public static int height = 360; // 168 //335
	public static int scale = 2;

	public static String Title;
	public static String AppName = "Untitled LE2 Project";
	public static String BuildDate = "10/31/17";
	public static int BuildNumber = 1;

	private static Game Game;
	private static OGL_TEST GameAlphaOGL;
	private static GL_Main GameOGL;

	private static GameServer Server;
	public static GameClient Client;

	public static Client MeridianClient;

	private static int port = 7381;
	private static String host = "localhost";

	public static HashMap<String, Boolean> launch_args;
	public static Preferences iniPrefs;

	public static boolean isConnected = false;
	public static boolean drawDebug = false;

	public static void main(String[] args) {
		launch_args = new HashMap<String, Boolean>();

		System.out.println("----------- R U N - T I M E   A R G S  ------------");
		for (String s : args) {
			launch_args.put(s, true);
			System.out.println(s + ": " + true);
		}
		System.out.println("---------------------------------------------------");

		if (launch_args.containsKey("-DedicatedServer")) {
			OpenServer();
		} else {
			Client = new GameClient(host, 7381);
		}

		/*
		 * MeridianClient = new Client("127.0.0.1", MeridianClient.class); try {
		 * MeridianClient.StartClient(); } catch (Exception e) {
		 * System.out.println("Unable to connect to a Meridian Server."); }
		 */

		LaunchGame();
	}

	public static void LaunchGame() {
		try {
			Ini ini = new Ini(new File("le2.ini"));
			iniPrefs = new IniPreferences(ini);

			System.out.println();
			System.out.println("Prefs.ini");

			for (int i = 0; i < iniPrefs.childrenNames().length; i++) {
				String prefNode = iniPrefs.childrenNames()[i];
				Preferences node = iniPrefs.node(prefNode);

				System.out.println("/" + prefNode + "/");

				for (int j = 0; j < node.keys().length; j++) {
					String prefKey = node.keys()[j];
					System.out.println(" * " + prefKey + " = " + node.get(prefKey, null));
				}
			}

			System.out.println();

			width = prefsInt("Graphics", "PixelsWidth", width);
			height = prefsInt("Graphics", "PixelsHeight", height);
			scale = prefsInt("Graphics", "DrawScale", scale);

			AppName = prefsStr("App", "ApplicationName", AppName);
			BuildNumber = prefsInt("App", "BuildNumber", BuildNumber);
			BuildDate = prefsStr("App", "BuildDate", BuildDate);

			Title = AppName + " [Build " + BuildNumber + " : " + BuildDate + "]";

		} catch (Exception e) {
			e.printStackTrace();
		}

		switch (iniPrefs.node("Graphics").getInt("EngineMode", 0)) {
		case 0:
			Game = new Game();
			Game.Launch(Game);
			break;
		case 1:
			GameAlphaOGL = new OGL_TEST(Title);
			break;
		case 2:
			GameOGL = new GL_Main(Title);
			break;
		}
	}

	public static int prefsInt(String node, String key, int dfVal) {
		return iniPrefs.node(node).getInt(key, dfVal);
	}

	public static boolean prefsBool(String node, String key, boolean dfVal) {
		return iniPrefs.node(node).getBoolean(key, dfVal);
	}

	public static String prefsStr(String node, String key, String dfVal) {
		return iniPrefs.node(node).get(key, dfVal);
	}

	public static double prefsDouble(String node, String key, double dfVal) {
		return iniPrefs.node(node).getDouble(key, dfVal);
	}

	private static void OpenServer() {
		Server = new GameServer(Boot.port);

		try {
			Server.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void OpenConnection(String host) {
		try {
			Client = new GameClient(host, 7381);
			Client.startClient();
		} catch (Exception e) {
			log("Unsuccessful Connection Attempt.. is the host server (@" + host + " running?", true);
		}
	}
	
	public static Game get() {
		return Game;
	}

	public static Level getLevel() {
		return get().getLevel();
	}

	public static Player getPlayer() {
		return get().getPlayer();
	}

	public static void setWindowIcon(String path) {
		Game.frame.setIconImage(Toolkit.getDefaultToolkit().getImage((path)));
	}

	public static Cursor setMouseIcon(String path) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = null;
		image = Toolkit.getDefaultToolkit().getImage((path));

		Point hotspot = new Point(0, 0);
		Cursor cursor = toolkit.createCustomCursor(image, hotspot, "Stone");
		Game.frame.setCursor(cursor);
		return cursor;
	}

	public static void centerMouse() {
		int centreFrameX = Game.frame.getX() + (Game.frame.getWidth() / 2);
		int centreFrameY = Game.frame.getY() + (Game.frame.getHeight() / 2);
		moveMouse(new Point(centreFrameX, centreFrameY));
	}

	public static void moveMouse(Point p) {
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

	public void setMousePos(int framex, int framey) {
		moveMouse(new Point(framex, framey));
	}

	public static int randInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public static double randDouble(int min, int max) {
		return ThreadLocalRandom.current().nextDouble(min, max);
	}

	public static void log(String text, boolean important) {
		if (!important) {
			System.out.println(" >> " + text);
		} else {
			System.err.println(" >> ALERT: " + text);
		}
	}

	public static void log(String text, String outboundClass, boolean important) {
		boolean reportIncidentTime = true;
		long nanoTimeOfLastEvent = -1;

		if (reportIncidentTime) {
			outboundClass = "[NT:" + System.nanoTime() + "] " + outboundClass;
		}

		if (!important) {
			System.out.println(outboundClass + " >> " + text);
		} else {
			System.err.println(outboundClass + " >> ALERT: " + text);

			nanoTimeOfLastEvent = System.nanoTime();
		}
	}

	public static void restart() {
		StringBuilder cmd = new StringBuilder();
		cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
		for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
			cmd.append(jvmArg + " ");
		}
		cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
		cmd.append(Window.class.getName()).append(" ");

		try {
			Runtime.getRuntime().exec(cmd.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
