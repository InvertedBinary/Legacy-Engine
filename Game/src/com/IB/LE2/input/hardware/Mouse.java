package com.IB.LE2.input.hardware;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.IB.LE2.Game;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	public static final byte LEFT_CLICK = 1;
	public static final byte RIGHT_CLICK = 2;
	public static final byte MIDDLE_CLICK = 3;
	public static final byte MOUSE_RELEASED = -1;
	
	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	
	private static int dragX = 0;
	private static int dragY = 0;
	
	private static int previousX;
	private static int previousY;
	
	public static int notch = 0;
	private static boolean doubleClick;
	final static String nl = "n";
	
	public static boolean inFrame = false;
	
	public static int getX() {
		return mouseX;// + (300 - Game.getGame().frame.getWidth() / Game.scale);
	}
	public static int getY() {
		return mouseY;// + (168 - Game.getGame().frame.getHeight() / Game.scale);
	}
	public static int getButton() {
		return getMouseB();
	}
	
	public static void update() {
        dragX = 0;
        dragY = 0;
	}
	
	public void moveMouse(Point p) {
	    GraphicsEnvironment ge = 
	        GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gs = ge.getScreenDevices();

	    // Search the devices for the one that draws the specified point.
	    for (GraphicsDevice device: gs) { 
	        GraphicsConfiguration[] configurations =
	            device.getConfigurations();
	        for (GraphicsConfiguration config: configurations) {
	            Rectangle bounds = config.getBounds();
	            if(bounds.contains(p)) {
	                // Set point to screen coordinates.
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
	    // Couldn't move to the point, it may be off screen.
	    return;
	}
	
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

        if (mouseX < previousX)
            dragX = -1;
        else if (mouseX > previousX)
            dragX = 1;
        
        if (mouseY < previousY)
            dragY = -1;
        else if (mouseY > previousY)
            dragY = 1;

        previousX = mouseX;
        previousY = mouseY;
	}


	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		Game.mouseMotionTime = 40;
	}


	public void mouseClicked(MouseEvent e) {

		
	}


	public void mouseEntered(MouseEvent e) {

		
	}
	
	
	
	
	
	@Override
	  public void mouseWheelMoved(MouseWheelEvent e) {
	        String message;
	        int notches = e.getWheelRotation();
	        if (notches < 0) {
	            message = "Mouse wheel moved UP\n"
	                         + -notches + " notch(es)\n";
	            
            	notch+= -notches;

	            
	        } else {
	            message = "Mouse wheel moved DOWN\n"
	                         + notches + " notch(es)\n";
	            if (notch > -1) {
	            	notch-= notches;
	            }
	        }
	        
	       if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
	            message += "    Scroll type: WHEEL_UNIT_SCROLL";
	            message += "    Scroll amount: " + e.getScrollAmount()
	                    + " unit increments per notch";
	            message += "    Units to scroll: " + e.getUnitsToScroll()
	                    + " unit increments";
	        }
	       // System.out.println(message + e);
	     //  System.out.println(notch);
	     System.out.println(notch);
	      /* try {
	    	   if (Game.get().gameState == Game.get().gameState.INGAME_A || Game.get().gameState == Game.get().gameState.INGAME) {
	    		 if (!Game.get().getPlayer().buildMode) {
	    			 if (notch > Game.get().getPlayer().abilities.lastAbilitySlot()) {
	    				 notch = 0;
	    			 }
	    			 if (notch < 0) {
	    				 notch = Game.get().getPlayer().abilities.lastAbilitySlot();
	    			 }
	    		    Game.get().getPlayer().abilities.checkAbility(Game.get().getScreen(), notch, true);
	    		 } else {
	    			   if (notch > Game.get().getPlayer().history.size() - 1) {
	    				   notch = 0;
	    			   }
	    			   if (notch < 0) {
	    				   notch = Game.get().getPlayer().history.size() - 1;
	    			   }
	    			 Game.get().getPlayer().swapBlock(notch);
	    		 }
	    		 
	    	   
	       }
	       } catch (NullPointerException e1) {
	    	   System.err.println("Ability at slot " + notch + " is null (Nonexistant)");
	       }*/
	     }

	
	public void mouseExited(MouseEvent e) {
		//Game.getGame().switchState(Game.getGame().gameState.Pause);
		
	}


	public void mousePressed(MouseEvent e) {
		setMouseB(e.getButton());
		Game.mouseMotionTime = 40;
		  previousX = e.getX();
	}

	
	
	public void mouseReleased(MouseEvent e) {
		setMouseB(-1);
		
		dragX = 0;
		dragY = 0;
		
	}
	
	/*public void doubleClick(MouseEvent e) {
		if (e.getClickCount() == 2 && !e.isConsumed() && e.getButton() == 1) {
		     e.consume();
		     doubleClick = true;
		    // doubleClick = false;
		}
	}
	
	public static boolean doubleClickEvent() {
		return doubleClick;
	}*/
	
	public static int getMouseB() {
		return mouseB;
	}
	
	public static void setMouseB(int mouseB) {
		Mouse.mouseB = mouseB;
	}
	
	public static int dragX() {
		return dragX;
	}
	
	public static int dragY() {
		return dragY;
	}
	
//	@Override
	/*public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}*/
	

}
