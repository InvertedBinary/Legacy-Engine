package com.IB.SL.input;

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

import com.IB.SL.Game;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	public static int dragDir = -1;
	int previousX;
	public static int notch = 0;
	private static boolean doubleClick;
	final static String nl = "n";
	
	   String print;

	public static int getX() {
		return mouseX;// + (300 - Game.getGame().frame.getWidth() / Game.scale);
	}
	public static int getY() {
		return mouseY;// + (168 - Game.getGame().frame.getHeight() / Game.scale);
	}
	public static int getButton() {
		return getMouseB();
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

        int x = e.getX();
        if (x < previousX) {
            dragDir = 0;
        } else if (x > previousX) {
            dragDir = 1;
        }

        previousX = x;
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
	       try {
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
	       }
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
//	@Override
	/*public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}*/
	

}
