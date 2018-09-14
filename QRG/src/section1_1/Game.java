package section1_1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;


public class Game extends Canvas implements Runnable
{
	final int WIDTH = 1280;
	final int HEIGHT = 720;

	public BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	boolean running = false;
	
	public Game(JFrame frame)
	{
		
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		frame.setTitle("Simple Drawing Application");
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String args[])
	{
		JFrame f = new JFrame();
		Game g = new Game(f);
		g.start();
	}
	
	public void start()
	{
		running = true;
		this.run();
	}

	@Override
	public void run()
	{
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();

				updates++;
				delta--;
			}
			render();

			frames++;

			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				updates = 0;
				frames = 0;
			}
		}
		//stop();
	}
	
	public int randInt(int floor, int ceil)
	{
		return ThreadLocalRandom.current().nextInt(floor, ceil + 1);
	}
	
	int dx = randInt(0, WIDTH);
	int dy = randInt(0, HEIGHT);
	int speed = 3;
	int xdir, ydir;
	
	public void update() {
		if (xdir == 0)
			xdir = speed * randInt(-1, 1);
		if (ydir == 0)
			ydir = speed * randInt(-1, 1);
			
		if (dy + 80 > HEIGHT)
			ydir = -speed;
		
		if (dy < 0)
			ydir = speed;
		
		if (dx + 52 > WIDTH)
			xdir = -speed;
		
		if (dx < 0)
			xdir = speed;
		
		dx += xdir;
		dy += ydir;
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		
	    g.setColor(Color.green);
	    g.fillRect(0, 0, WIDTH, HEIGHT);
	    
	    g.setColor(Color.blue);
	    g.fillRect(dx, dy, 50, 50);
	    
	    g.setColor(Color.black);
	    g.setFont(new Font("Verdana", 0, 16));
	    g.drawString("Simple Graphics Stuff", 5, 21);
	    
		g.dispose();
		bs.show();
	}
}
