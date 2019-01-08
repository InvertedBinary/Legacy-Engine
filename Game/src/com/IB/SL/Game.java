package com.IB.SL;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Font16x;
import com.IB.SL.graphics.Font8x;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.graphics.UI.TagMenu;
import com.IB.SL.graphics.UI.menu.UI_Menu;
import com.IB.SL.input.Keyboard;
import com.IB.SL.input.Mouse;
import com.IB.SL.level.Level;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.tile.Tile;
import com.IB.SL.level.worlds.TiledLevel;
import com.IB.SL.util.LoadProperties;
import com.IB.SL.util.SaveGame;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

@SuppressWarnings("static-access")

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;

	public static Tile tile;
	public GUI gui;
	private Thread thread;
	public JFrame frame;
	public Keyboard key;

	public double xScroll, yScroll;

	private Player player;
	public int frames = 0;
	public static int mouseMotionTime = 0;
	// private boolean invokedLoad = false;

	public boolean autoSave = true;

	private boolean running = false;
	public static transient Font16x font16bit;
	public static transient Font8x font8bit;
	public static int currentLevelId;
	public static boolean showAVG;
	public static boolean recAVG_FPS = false;

	public boolean FrameAdjusted = false;
	public static boolean devModeOn = false;
	private boolean devModeReleased = true;
	public LoadProperties loadProp;
	public static boolean devModeInfoOn = false;
	public TileCoord playerSpawn;
	public TileCoord playerRespawn = new TileCoord(52, 72);
	File screenshots = null;
	public Stack<Level> levels = new Stack<Level>();


	int saveTime = 0;
	/**
	 * 0 = stop; 1 = menu; 2 = [m]Protocol: (in-game); 3 = [a]Protocol: (in-game); 4
	 * = pause; 5 = modded/tampered; 6 = dead; 7 = Splash;
	 */
	private boolean releasedDevInfo = true;

	private Screen screen;
	public WindowHandler windowHandler;
	public BufferedImage image = new BufferedImage(Boot.width, Boot.height, BufferedImage.TYPE_INT_RGB);
	// private VolatileImage vImage = this.createVolatileImage(width, height);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public int conTime = 0;

	private void folderCreation()
		{
			File SavesFolder = new File(LoadProperties.basePath + "/Saves");

			if (SavesFolder.exists()) {
				System.out.println("File: " + SavesFolder + " exists, not creating a new directory");
			}
			if (!SavesFolder.exists()) {
				System.out.println("Creating Directory: " + SavesFolder);

				boolean result = SavesFolder.mkdir();
				if (result) {
					System.out.println(SavesFolder + " Created Successfully");
				}

			}

			if (screenshots.exists()) {
				System.out.println("File: " + screenshots + " exists, not creating a new directory");
			}
			if (!screenshots.exists()) {
				System.out.println("Creating Directory: " + screenshots.getAbsolutePath());

				boolean result = screenshots.mkdir();
				if (result) {
					System.out.println(screenshots.getAbsolutePath() + " Created Successfully");
				}
			}
		}

	/*
	 * private static final char PKG_SEPARATOR = '.'; private static final char
	 * DIR_SEPARATOR = '/'; private static final String CLASS_FILE_SUFFIX =
	 * ".class"; private static final String BAD_PACKAGE_ERROR =
	 * "Unable to get resources from path '%s'. Are you sure the package '%s' exists?"
	 * ;
	 * 
	 * public static List<Class<?>> find(String scannedPackage) { String scannedPath
	 * = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR); URL scannedUrl =
	 * Thread.currentThread().getContextClassLoader().getResource(scannedPath); if
	 * (scannedUrl == null) { throw new
	 * IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath,
	 * scannedPackage)); } File scannedDir = new File(scannedUrl.getFile());
	 * List<Class<?>> classes = new ArrayList<Class<?>>(); for (File file :
	 * scannedDir.listFiles()) { classes.addAll(find(file, scannedPackage)); }
	 * return classes; }
	 * 
	 * private static List<Class<?>> find(File file, String scannedPackage) {
	 * List<Class<?>> classes = new ArrayList<Class<?>>(); String resource =
	 * scannedPackage + PKG_SEPARATOR + file.getName(); if (file.isDirectory()) {
	 * for (File child : file.listFiles()) { classes.addAll(find(child, resource));
	 * } } else if (resource.endsWith(CLASS_FILE_SUFFIX)) { int endIndex =
	 * resource.length() - CLASS_FILE_SUFFIX.length(); String className =
	 * resource.substring(0, endIndex); try { classes.add(Class.forName(className));
	 * } catch (ClassNotFoundException ignore) { } } return classes; }
	 */

	ArrayList<String> materials = new ArrayList<String>();

	public Game()
		{
			loadProp = new LoadProperties();
			loadProp.createDataFolder();
			screenshots = new File(LoadProperties.basePath + "/screenshots");
			// Sound.loadOggs();
			folderCreation();

			this.StartDiscord();

			setGui(new GUI());
			Dimension size = new Dimension(Boot.width * Boot.scale, Boot.height * Boot.scale);
			setPreferredSize(size);
			screen = new Screen(Boot.width, Boot.height);
			frame = new JFrame();
			windowHandler = new WindowHandler(this);
			key = new Keyboard();
			tile = new Tile();
			tile.readXML("/XML/Tiles/TileDefinitions.xml");

			// setLevel(new XML_Level(Maps.ForestLevel));
			TiledLevel TL = new TiledLevel("/XML/Levels/b10");
			setLevel(TL);
			
			if (TL.spawnpoint != null) {
				playerSpawn = TL.spawnpoint;
			} else {
				playerSpawn = new TileCoord(0, 0);
			}

			// TileCoord playerSpawn = new TileCoord(296, 381);
			setPlayer(new PlayerMP(playerSpawn.x(), playerSpawn.y(), key, "New Player", "-1"));
			// level.add(getPlayer());
			addKeyListener(key);
			Mouse mouse = new Mouse();
			font16bit = new Font16x();
			font8bit = new Font8x();
			addMouseListener(mouse);
			addMouseMotionListener(mouse);
			addMouseWheelListener(mouse);

			getMenu().addMenus();
			//getMenu().load(getMenu().MainMenu, true);

			System.out.println("-=-=-=-Begin Loading xMenu-=-=-=-");
			TagMenu xMenu = new TagMenu("/XML/Menu/TestMenu", false);
			System.out.println("-=-=-=-Finished Loading xMenu-=-=-=-");
			
			getMenu().load(xMenu, true);
			
			frame.setMinimumSize(new Dimension(Boot.prefsInt("Frame", "MinWidth", Boot.width), Boot.prefsInt("Frame", "MinHeight", Boot.height)));
			
		this.frame.getRootPane().addComponentListener(new ComponentListener()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				//System.out.println("Resized?");
				FrameAdjusted = true;
			}

			@Override
			public void componentMoved(ComponentEvent e)
			{
			}

			@Override
			public void componentShown(ComponentEvent e)
			{
			}

			@Override
			public void componentHidden(ComponentEvent e)
			{
			}
		});
	}
	
	public void StartDiscord()
		{
			 DiscordEventHandlers handler = new DiscordEventHandlers();
			 DiscordRPC.discordInitialize("402613263986327552", handler, true);
		}

	public static String lvl_name = "test;";

	public static void createNewPresence()
		{
			 DiscordRichPresence rich = new DiscordRichPresence();
			 rich.details = "On Level: " + (lvl_name);
			 rich.state = "Located at: (" + (int)Boot.get().getPlayer().x()/32 + " , " +
					 (int)Boot.get().getPlayer().y()/32 + ")";
			 rich.largeImageKey = "ogimage";
			 rich.largeImageText = "Meridian";
			
			 DiscordRPC.discordUpdatePresence(rich);
		}

	public UI_Menu getMenu()
		{
			return this.gui.menu;
		}

	public void setLevel(Level level)
		{
			this.levels.push(level);
		}

	public void captureScreen(JFrame currentFrame, String fileName) throws AWTException
		{
			System.out.println("Saved Screenshot as: " + fileName + "_" + System.currentTimeMillis() + ".png");
			Robot robot = new Robot();
			Rectangle capRectange = currentFrame.getBounds();
			BufferedImage exportImage = robot.createScreenCapture(capRectange);
			try {
				ImageIO.write(exportImage, "png", new File(fileName + "_" + System.currentTimeMillis() + ".png"));
			} catch (IOException e) {

				System.out.println(e);

			}

		}

	public static int getWindowWidth()
	{
		return Boot.width * Boot.scale;
	}

	public static int getWindowHeight()
	{
		return Boot.height * Boot.scale;
	}

	public synchronized void start()
	{
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public synchronized void stop()
	{
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		quit();
	}

	/*
	 * inet = InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 });
	 * System.out.println("Sending Ping Request to " + inet);
	 * System.out.println(inet.isReachable(5000) ? "Host is reachable" :
	 * "Host is NOT reachable");
	 */
	
	public void run()
		{
			long lastTime = System.nanoTime();
			long timer = System.currentTimeMillis();
			final double ns = 1000000000.0 / 60.0;
			double delta = 0;
			frames = 0;
			int updates = 0;
			requestFocus();

			while (running) {
				long now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;
				while (delta >= 1) {

					//speedModif++;
					//if (speedModif % 1 == 0) {
					if (UI_Menu.suspend != UI_Menu.SUS_UPD && UI_Menu.suspend != UI_Menu.SUS_ALL)
					update();
					//speedModif = 0;
					//}

					key.update();
					gui.update();
					updateMode();

					if (Boot.prefsBool("Graphics", "LockFPS", false)) {
						if (!Boot.launch_args.containsKey("-mode_dedi")) {
							if (frame.isVisible())
								render();
						}
					frames++;
					}
					
					updates++;
					delta--;
					
				}
				
				if (!Boot.prefsBool("Graphics", "LockFPS", false)) {
					if (!Boot.launch_args.containsKey("-mode_dedi")) {
						if (frame.isVisible())
							render();
					}
				frames++;
				}

				if (System.currentTimeMillis() - timer >= 1000) {
					timer += 1000;
					//System.out.println(updates + " ups, " + frames + " fps");

					frame.setTitle(Boot.title + " | " + updates + " ups, " + frames + " fps");

					if (this.recAVG_FPS) {
						fpsTotal += frames;
						System.out.println("FPS: " + frames + " fpsIndex: " + ++fpsIndex + " AVG: " + fpsAVG);
						fpsAVG = fpsTotal / fpsIndex;
					}

					updates = 0;
					frames = 0;
				}
			}
			 DiscordRPC.discordShutdown();
			stop();
		}

	public static int fpsIndex = 0;
	public static int fpsTotal = 0;
	public static int fpsAVG = 0;

	public ArrayList<String> getCharDirs()
		{
			ArrayList<String> result = new ArrayList<String>();
			File file = new File(SaveGame.createDataFolder() + "\\Saves\\");
			String[] names = file.list();

			for (String name : names) {
				// System.out.println(SaveGame.createDataFolder() + "\\Saves\\" + name);
				if (new File(SaveGame.createDataFolder() + "\\Saves\\" + name).isDirectory()) {
					// System.out.println(name);
					result.add(name);
				}
			}
			return result;
		}

	public void switchCharacter(String name)
		{
			boolean disabledSave = false;
			if (autoSave) {
				System.out.println("Switching Char -- Disabled AutoSave");
				autoSave = false;
				disabledSave = true;
			}
			//this.PlayerName = name;
			getPlayer().name = name;
			// getPlayer().reset(getPlayer());
			getPlayer().invokeLoad(getPlayer());
			System.out.println("Switched To: " + getPlayer().name);

			if (disabledSave) {
				System.out.println("Finished Switching Char -- Enabled AutoSave");
				autoSave = true;
				disabledSave = false;
			}
			try {
				loadProp.loadPrefs(this);
			} catch (Exception e) {
				autoSave = true;
			}

		}

	public void updatePause()
		{
			// System.out.println("[Game: ~773] GAMESTATE: PAUSE");
		}

	public void save(boolean autoOverride)
		{
			// if (gameState != gameState.MENU) {
			loadProp.savePrefs(this);
			if (autoSave || autoOverride) {
				if (getLevel().players.size() > 0) {
					if (getLevel().getClientPlayer() != null) {
						getLevel().getClientPlayer().invokeSave(getLevel().getClientPlayer());
					}
				}
			}
			// }
		}

	public void autoSave()
		{
			saveTime++;
			if ((saveTime % 400) == 0) {
				save(false);
				// loadProp.saveEquips((PlayerMP) this.getPlayer());
				// save(this.player.inventory.items);
				// System.out.println("SAVING THE GAME");
			}
		}

	public void updateMode()
		{
			// adminCmds();

			autoSave();
			if (key.DevMode && !devModeOn && devModeReleased && Mouse.getButton() == 2) {
				devModeOn = true;
				devModeReleased = false;
			}

			if (!key.DevMode) devModeReleased = true;

			if (key.DevMode && devModeOn && devModeReleased) {
				devModeOn = false;
				devModeReleased = false;
			}

			if (key.toggleDevModeInfo && !devModeInfoOn && releasedDevInfo && devModeOn) {
				devModeInfoOn = true;
				releasedDevInfo = false;
			}

			if (!key.toggleDevModeInfo) releasedDevInfo = true;

			if (key.toggleDevModeInfo && devModeInfoOn && releasedDevInfo) {
				devModeInfoOn = false;
				releasedDevInfo = false;
			}

			if (key.capture) {
				if (screenshots.exists()) {
					try {
						captureScreen(frame, screenshots + "/Square_Legacy");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	
	public void AdjustImageToFrame() {
		if (this.FrameAdjusted && Mouse.getButton() == -1)
		screen.clear();
		
		Boot.width = (frame.getWidth() - frame.getInsets().left - frame.getInsets().right) / Boot.scale; 
		Boot.height = (frame.getHeight() - frame.getInsets().top - frame.getInsets().bottom) / Boot.scale;
		
		screen.clear();
		screen.width = Boot.width;
		screen.height = Boot.height;
		screen.pixels = new int[Boot.width * Boot.height];
		
		image = new BufferedImage(Boot.width, Boot.height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		this.FrameAdjusted = false;
	}

	public void update()
		{
			conTime++;
			
			if(conTime > 120) {
				conTime = 0;
			}
			 
			if (mouseMotionTime > 0) {
				this.mouseMotionTime--;
			}
			
			this.AdjustImageToFrame();
			
			getLevel().update();
		}

	public void render()
		{

			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				image.setAccelerationPriority(1);
				return;
			}
			
			screen.clear();

			double xSp = key.pan ? getPlayer().x() + (screen.xo * 2) - screen.width / 2
					: getPlayer().x() - screen.width / 2;
			double ySp = getPlayer().y() - screen.height / 2;
			

			double rScroll = xSp + (screen.width);
			double bScroll = ySp + (screen.height);

			double maxw = getLevel().width << VARS.TILE_BIT_SHIFT;
			double maxh = getLevel().height << VARS.TILE_BIT_SHIFT;

				if (xSp < 0) {
					xScroll = 0;
				} else {
					Boot.get().xScroll = ((rScroll + 1) >= maxw) ? (maxw - (rScroll - xSp)) : xSp;
				}
				Boot.get().yScroll = ((bScroll + 1) >= maxh) ? (maxh - (bScroll - ySp)) : ySp;
	
			if (UI_Menu.suspend != UI_Menu.SUS_ALL) {
				getLevel().render((int) (xScroll), (int) (yScroll), screen);
			}
			
			gui.render(screen);

			if (showAVG) {
				if (fpsAVG < 200) {
					font8bit.render(-5, Boot.height - 17, -3, 0xDB0000, "Average FPS: " + fpsAVG, screen, false, true);
				} else {
					font8bit.render(-5, Boot.height - 17, -3, 0x00ff00, "Average FPS: " + fpsAVG, screen, false, true);
				}
			}

			// System.arraycopy(screen.pixels, 0, pixels, 0, screen.pixels.length);
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}

			Graphics g = bs.getDrawGraphics();
			Color Opaque = new Color(5, 0, 0, 120);

			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

			g.setColor(Opaque);

			if (devModeOn == true || Mouse.getButton() == 2) {
				try {
					g.setColor(Opaque);
					// g.fillRect(10, 80, 100, 1);
					g.fill3DRect(0, 0, 545, 95, false);
					g.fill3DRect(Mouse.getX() - 110, Mouse.getY() + 50, 250, 30, false);
					g.setColor(Color.lightGray);
					g.fillRect(Mouse.getX() - 4, Mouse.getY() - 4, 38, 38);
					g.setFont(new Font("Verdana", 0, 16));
					g.setColor(Color.WHITE);
					g.drawString("Application: " + frame.getTitle(), 10, 22);
					g.drawString("Mouse X: " + (int) Mouse.getX() / Boot.scale + ", Mouse Y: " + Mouse.getY() / Boot.scale,
							Mouse.getX() - 103, Mouse.getY() + 70);
					g.drawString("Player[UUID]: " + getLevel().getPlayers(), 10, 44);
					// g.drawString("xScroll: " + xScroll + " yScroll: " + yScroll, 10, 60);
					//g.drawString("Tile: " + getLevel().returnTile() + " || Overlay: " + getLevel().returnOverlayTile(), 10, 60);
					//g.drawString("X: " + (int) getPlayer().x() / TileCoord.TILE_SIZE + ", Y: " + (int) getPlayer().y() / TileCoord.TILE_SIZE, 10, 20);
					// screen.drawLine(getPlayer(), level.entities);
					g.setColor(Color.gray);
					// g.fill3DRect(1020, 618, 300, 300, true);
					g.setColor(Color.WHITE);

					g.setFont(new Font("Verdana", 0, 18));

					/*
					 * if (gameState == 5) { g.fill3DRect(1362, 4, 55, 30, false);
					 * g.setColor(Color.WHITE); g.setFont(new Font("Verdana",0, 18));
					 * g.drawString("Map", 1372, 25); }
					 */

					if (devModeOn == true && devModeInfoOn) {
						g.setFont(new Font("Verdana", 0, 16));
						g.drawString("Developer Mode: Mouse Grid, Coordinate, Player [UUID], Scrolls", 10, 80);
						g.setFont(new Font("Verdana", 0, 16));
						g.fill3DRect(1362, 4, 55, 30, false);
						g.setColor(Color.WHITE);
						g.setFont(new Font("Verdana", 0, 18));
						g.drawString("Map", 1372, 25);
						// g.drawString("Button: " + Mouse.getButton(), 415, 80);
					}

				} catch (Exception e) {

				}
			}

			// fontLayer.render(g);
			g.dispose();
			bs.show();

			// frame.remove(this);
			// width = frame.getWidth() / scale;
			// height = frame.getHeight() / scale;
			// frame.add(this);
			// System.out.println(width);
		}

	public void Launch(Game game)
		{
			if (!Boot.launch_args.containsKey("-mode_dedi")) {
			Boot.setWindowIcon("/icon.png");
			game.frame.setResizable(Boot.prefsBool("Frame", "Resizeable", false));
			if (Boot.launch_args.containsKey("-resizeable")) {
				game.frame.setResizable(true);
			}
			game.frame.setTitle(Boot.title);
			game.frame.add(game);
			// game.frame.remove(game);
			// game.frame.setOpacity(0.01F);
			game.frame.pack();
			game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			game.frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			int windowMode = Boot.prefsInt("Frame", "FullscreenMode", 0);
			if (windowMode == 1)
				game.setBorderlessFullscreen(true);
			else if (windowMode == 2)
				setTrueFullscreen();
			
			Boot.setMouseIcon("/Textures/cursor.png");
			Boot.centerMouse();
			}
			game.start();
		}
	
	public boolean ChangingFullscreenState = false;
	public void setBorderlessFullscreen(boolean state) {
		ChangingFullscreenState = true;
		if (state) {
			frame.dispose();
			frame.setUndecorated(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		} else {
			frame.dispose();
			frame.setUndecorated(false);
			frame.setExtendedState(JFrame.NORMAL);
			frame.setSize(new Dimension(Boot.prefsInt("Graphics", "PixelsWidth", Boot.width) * Boot.scale, Boot.prefsInt("Graphics", "PixelsHeight", Boot.height) * Boot.scale));
			frame.setLocationRelativeTo(null);
		}
		if (!frame.isVisible())
			frame.setVisible(true);
		
		ChangingFullscreenState = false;
	}
	
	public void setTrueFullscreen() {
			frame.setBounds(getGraphicsConfiguration().getBounds());
			getGraphicsConfiguration().getDevice().setFullScreenWindow(frame);
	}

	public Screen getScreen()
		{
			return screen;
		}

	public Player getPlayer()
		{
			return player;
		}

	public void setPlayer(Player player)
		{
			this.player = player;
		}

	public Level getLevel()
		{
			return levels.peek();
		}

	public GUI getGui()
		{
			return gui;
		}

	public Keyboard getInput()
		{
			return this.key;
		}

	public void setGui(GUI gui)
		{
			this.gui = gui;
		}

	public void quit()
		{
		    DiscordRPC.discordShutdown();
			System.out.println("Saving & Closing Application");
			save(false);
			Boot.c.stopClient();
			System.exit(0);
		}
}
