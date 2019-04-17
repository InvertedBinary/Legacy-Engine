package com.IB.SL.util;

import com.IB.SL.Boot;
import com.IB.SL.graphics.Font8x;
import com.IB.SL.graphics.Screen;

public class Debug {

	private Debug() {
		
	}
	
	public static void drawRect(Screen screen, int x, int y, int width, int height, boolean fixed){
	      screen.drawRect(x, y, width, height, 0xFF0000, fixed);
	   }
	   
	   public static void drawRect(Screen screen, int x, int y, int width, int height, int color, boolean fixed){
	      screen.drawRect(x, y, width, height, color, fixed);
	   }
	
	   public static void drawLine(Screen screen, int x0, int y0, int x1, int y1, int colour, boolean fixed){
		   screen.drawVectors(Boot.get().getLevel().BresenhamLine(x0, y0, x1, y1), colour, fixed);
		}
	   
	   public static void drawTriangle(Screen screen, Font8x font8x8, double x, double y, double x2, double y2) {

			double a = (x  - (x2 * 16));
			double b = (y - (y2 * 16));
			double c = Math.sqrt((Math.pow(a, 2) + Math.pow(b, 2)));
			double rc = (double)Math.round(c * Math.pow(10, 1)) / Math.pow(10, 1);
			
			Debug.drawLine(screen, (int)x, (int)y, (int)x2 * 16, (int)y2 * 16, 0xff00FF00, true);
			font8x8.render((int)(x - (a + 16) / 2) - (("c: " + rc).length() * 8), (int)(y - (b + 16) / 2) + 32, 1, 0xff00FF00, "c: " + rc, screen, true, true);
			Debug.drawLine(screen, (int)x, (int)y, (int)(x - a), (int)y, 0xffFF0000, true);
			font8x8.render((int)(x - (a + 16) / 2), (int)y - 12, 1, 0xffFF0000, "a: " + a, screen, true, true);
			Debug.drawLine(screen, (int)(x - a), (int)y, (int)(x - a), (int)(y - b), 0xff0000FF, true);
			font8x8.render((int)(x - (a)), (int)(y - (b + 16) / 2), 1, 0xff0000FF, "b: " + b, screen, true, true);

			font8x8.render((int)(8), 32, 1, 0xff00FF00, "C: " + c, screen, true, true);

	   }
	   
}
