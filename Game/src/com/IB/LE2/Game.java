package com.IB.LE2;

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

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.IB.LE2.asset.audio.Audio;
import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.input.UI.UI_Manager;
import com.IB.LE2.input.UI.components.UI_Font;
import com.IB.LE2.input.UI.menu.TagMenu;
import com.IB.LE2.input.hardware.Keyboard;
import com.IB.LE2.input.hardware.Mouse;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.WindowHandler;
import com.IB.LE2.util.FileIO.Assets;
import com.IB.LE2.util.FileIO.Disk;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.entity.mob.PlayerMP;
import com.IB.LE2.world.level.Level;
import com.IB.LE2.world.level.tile.Tile;
import com.IB.LE2.world.level.worlds.TiledLevel;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

@SuppressWarnings("static-access")

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static Tile tile;
	private Thread thread;
	public JFrame frame;
	public Keyboard key;

	public double xScroll, yScroll;

	private Player player;
	public int frames = 0;
	public static int mouseMotionTime = 0;
	// private boolean invokedLoad = false;

	public boolean AutoSave = true;

	private boolean running = false;
	public static transient UI_Font font16bit;
	public static transient UI_Font font8bit;
	public static transient TagMenu MainMenu;
	public static boolean showAVG;
	public static boolean recAVG_FPS = false;

	public boolean FrameAdjusted = false;
	public static ArrayList<Level> levels = new ArrayList<Level>();

	int saveTime = 0;
	/**
	 * 0 = stop; 1 = menu; 2 = [m]Protocol: (in-game); 3 = [a]Protocol: (in-game); 4
	 * = pause; 5 = modded/tampered; 6 = dead; 7 = Splash;
	 */
	private Screen screen;
	public WindowHandler windowHandler;
	public BufferedImage image = new BufferedImage(Boot.width, Boot.height, BufferedImage.TYPE_INT_RGB);
	// private VolatileImage vImage = this.createVolatileImage(width, height);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public int conTime = 0;

	public Game() {
		StartDiscord();
		Audio.Initialize();
		Assets.ExecuteLoadOrder();
		// Audio.PlayMusic("Hope", "Hope.mid");
		Dimension size = new Dimension(Boot.width * Boot.scale, Boot.height * Boot.scale);
		setPreferredSize(size);
		screen = new Screen(Boot.width, Boot.height);
		frame = new JFrame();
		windowHandler = new WindowHandler(this);
		key = new Keyboard();

		TiledLevel TL = new TiledLevel(Boot.prefsStr("App", "BootLevel", "b10"));
		setLevel(TL);

		setPlayer(new PlayerMP(TL.Spawnpoint, key, "New Player", "-1"));
		addKeyListener(key);
		Mouse mouse = new Mouse();
		font16bit = UI_Font.getFont("SL");
		font8bit = UI_Font.getFont("SL8x8");

		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addMouseWheelListener(mouse);

		frame.setMinimumSize(new Dimension(Boot.prefsInt("Frame", "MinWidth", Boot.width),
				Boot.prefsInt("Frame", "MinHeight", Boot.height)));

		this.frame.getRootPane().addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				// System.out.println("Resized?");
				FrameAdjusted = true;
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	public void StartDiscord() {
		DiscordEventHandlers handler = new DiscordEventHandlers();
		DiscordRPC.discordInitialize("402613263986327552", handler, true);
	}

	public static String lvl_name = "test;";

	public static void DiscordPlayerPosPresence() {
		DiscordRichPresence rich = new DiscordRichPresence();
		rich.details = "On Level: " + (lvl_name);
		rich.state = "Located at: (" + (((int) Boot.get().getPlayer().x()) >> 5) + " , "
				+ (((int) Boot.get().getPlayer().y()) >> 5) + ")";
		rich.largeImageKey = "ogimage";
		rich.largeImageText = "Meridian";
		rich.joinSecret = "MTI4NzM0OjFpMmhuZToxMjMxMjM=";

		DiscordRPC.discordUpdatePresence(rich);
	}

	public static void DiscordMenuPresence(String menuName) {
		DiscordRichPresence rich = new DiscordRichPresence();
		rich.details = "At A Menu:";
		rich.state = menuName;
		rich.largeImageKey = "ogimage";
		rich.largeImageText = "Meridian";

		DiscordRPC.discordUpdatePresence(rich);
	}

	public TagMenu getMenu() {
		return (TagMenu) UI_Manager.Current();
	}

	public static void setLevel(Level level) {
		if (levels.size() > 0) {
			((TiledLevel)levels.get(0)).StopLua();
			levels.add(levels.get(0));
			levels.set(0, level);
		} else
			levels.add(level);
	}
	
	public static Level getCreateLevel(String lvln) {
		for (Level lvl : levels) {
			if (lvl.name.equals(lvln)) {
				System.out.println("Map exists, returning loaded version!");
				((TiledLevel)lvl).reload();
				return lvl;
			}
		}
		
		System.out.println("Map unloaded, reading..");
		return new TiledLevel(lvln);
	}
	
	public static boolean levelExists(String lvln) {
		for (Level lvl : levels) {
			if (lvl.name.equals(lvln))
				return true;
		}
		
		return false;
	}
	
	public void captureScreen(JFrame currentFrame, String fileName) throws AWTException {
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

	public static int getWindowWidth() {
		return Boot.width * Boot.scale;
	}

	public static int getWindowHeight() {
		return Boot.height * Boot.scale;
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		quit();
	}

	public void run() {
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

				// speedModif++;
				// if (speedModif % 1 == 0) {
				update();
				// speedModif = 0;
				// }

				key.update();
				UI_Manager.update();
				Mouse.update();
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
				// System.out.println(updates + " ups, " + frames + " fps");

				frame.setTitle(Boot.Title + " | " + updates + " ups, " + frames + " fps");

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

	public void autoSave() {
		saveTime++;
		if ((saveTime % 400) == 0) {
			// System.out.println("SAVING THE GAME");
		}
	}

	public void updateMode() {
		autoSave();
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

	boolean FirstMenuLoad = false;

	public void update() {
		conTime++;
		
		if (conTime > 120) {
			conTime = 0;
		}

		if (mouseMotionTime > 0) {
			this.mouseMotionTime--;
		}
		
		this.AdjustImageToFrame();

		Level current = getLevel();
		current.update();

		for (Level lvl : levels) {
			if (lvl != current)
				((TiledLevel) lvl).UpdateUnloaded();
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			image.setAccelerationPriority(1);
			return;
		}

		screen.clear();

		double xSp = key.getKeyState("pan") ? (getPlayer().getMidpointX()) + (screen.xo * 2) - screen.width / 2
				: getPlayer().getMidpointX() - screen.width / 2;
		double ySp = (getPlayer().getMidpointY()) - screen.height / 2;

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

		getLevel().render((int) (xScroll), (int) (yScroll), screen);

		UI_Manager.render(screen);

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

		if (Mouse.getButton() == 2) {
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
				g.drawString("Press 'M' To Reload Current Menu", 10, 66);
				// g.drawString("xScroll: " + xScroll + " yScroll: " + yScroll, 10, 60);
				// g.drawString("Tile: " + getLevel().returnTile() + " || Overlay: " +
				// getLevel().returnOverlayTile(), 10, 60);
				// g.drawString("X: " + (int) getPlayer().x() / TileCoord.TILE_SIZE + ", Y: " +
				// (int) getPlayer().y() / TileCoord.TILE_SIZE, 10, 20);
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

	public void Launch(Game game) {
		if (!Boot.launch_args.containsKey("-mode_dedi")) {
			Boot.setWindowIcon(Disk.AppDataDirectory + "/bin/icon.png");
			game.frame.setResizable(Boot.prefsBool("Frame", "Resizeable", false));
			if (Boot.launch_args.containsKey("-resizeable")) {
				game.frame.setResizable(true);
			}
			game.frame.setTitle(Boot.Title);
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

			Boot.setMouseIcon(Disk.AppDataDirectory + "/bin/cursor.png");
			Boot.centerMouse();
		}

		game.start();

		MainMenu = new TagMenu(Boot.prefsStr("UI", "StartupMenu", "Main"));
		UI_Manager.Load(MainMenu);
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
			frame.setSize(new Dimension(Boot.prefsInt("Graphics", "PixelsWidth", Boot.width) * Boot.scale,
					Boot.prefsInt("Graphics", "PixelsHeight", Boot.height) * Boot.scale));
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

	public Screen getScreen() {
		return screen;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public static Level getLevel() {
		return levels.get(0);
	}

	public Keyboard getInput() {
		return this.key;
	}

	public void quit() {
		DiscordRPC.discordShutdown();
		System.out.println("Saving & Closing Application");
		// save(false);
		Boot.Client.stopClient();
		System.exit(0);
	}
}
