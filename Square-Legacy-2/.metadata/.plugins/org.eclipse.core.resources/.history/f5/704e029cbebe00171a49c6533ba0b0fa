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
import java.util.HashMap;

public class Boot {

	public static String title = "Square Legacy 2 [Build 0 : 10/29/17]";
	private static Game g = new Game(title);

	public static Game get() {
		return g;
	}
	
    public static  HashMap<String, Boolean>  launch_args;
	
	public static void main(String[] args) {
    	launch_args = new HashMap<String, Boolean>();

    	for (String s : args) {
    		launch_args.put(s, true);
    	}
		
		g.Launch(g);
		

	}
	
    public static void setWindowIcon(String path) {
    	g.frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				Game.class.getResource(path)));
    	
    }
    
    public static Cursor setMouseIcon(String path) {
    	Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = null;
		image = Toolkit.getDefaultToolkit().getImage(
				Game.class.getResource(path));

		Point hotspot = new Point(0, 0);
		Cursor cursor = toolkit.createCustomCursor(image, hotspot, "Stone");
		g.frame.setCursor(cursor);
		return cursor;
    }
	
	public static void centerMouse() {
		int centreFrameX = g.frame.getX() + (g.frame.getWidth() / 2);
		int centreFrameY = g.frame.getY() + (g.frame.getHeight() / 2);
		moveMouse(new Point(centreFrameX, centreFrameY));
	}
	
	public static void moveMouse(Point p) {
	    GraphicsEnvironment ge = 
	        GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gs = ge.getScreenDevices();
	    for (GraphicsDevice device: gs) { 
	        GraphicsConfiguration[] configurations =
	            device.getConfigurations();
	        for (GraphicsConfiguration config: configurations) {
	            Rectangle bounds = config.getBounds();
	            if(bounds.contains(p)) {
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
	
	public static void log(String text, boolean err) {
		if (!err) {			
			System.out.println(" >> " + text);
		} else {
			System.err.println(" >> ALERT: " + text);			
		}
	}
	
	public static void log(String text, String outboundClass, boolean err) {
		if (!err) {			
			System.out.println(" >> " + text);
		} else {
			System.err.print(outboundClass + " >> ALERT: ");
				System.out.println(text);
		}
	}
	

}
