package com.IB.LE2.asset.graphics.lighting;

public class TileLighting {
	   
	   public static int changeBrightness(int col, int amount) {
		   if (amount == 0) return col;
		   
		      int r = (col & 0xff0000) >> 16;
		      int g = (col & 0xff00) >> 8;
		      int b = (col & 0xff);
		      
		      if(amount < -180) amount = -180;
		      if(amount > 0) amount = 0;
		      
		      r += amount;
		      g += amount;
		      b += amount;

		      if (r < 0) r = 0;
		      if (g < 0) g = 0;
		      if (b < 0) b = 0;
		      if (r > 255) r = 255;
		      if (g > 255) g = 255;
		      if (b > 255) b = 255;
		      
		      return r << 16 | g << 8 | b;
	   }
	   
	   public static int changeBrightness(int col, int r, int g, int b) {
		      int cr = (col & 0xff0000) >> 16;
		      int cg = (col & 0xff00) >> 8;
		      int cb = (col & 0xff);
		      
		      r = Math.max(r, -180);
		      g = Math.max(g, -180);
		      b = Math.max(b, -180);
		      r = Math.min(r, 0);
		      g = Math.min(g, 0);
		      b = Math.min(b, 0);
		      
		      cr += r;
		      cg += g;
		      cb += b;
		      
		      
		      if (cr < 0) cr = 0;
		      if (cg < 0) cg = 0;
		      if (cb < 0) cb = 0;
		      if (cr > 255) cr = 255;
		      if (cg > 255) cg = 255;
		      if (cb > 255) cb = 255;
		      
		      return cr << 16 | cg << 8 | cb;
	   }
	   
	   public static int changeBrightnessNegative(int col, int amount) {
		   if (amount == 0) return col;
		   
		      int r = (col & 0xff0000) >> 16;
		      int g = (col & 0xff00) >> 8;
		      int b = (col & 0xff);
		      
		      if(amount < -180) amount = -180;
		      if(amount > 0) amount = 0;
		      
	    	  r -= amount;
			  g -= amount;
			  b -= amount;
				  
		      if (r < 0) r = 0;
		      if (g < 0) g = 0;
		      if (b < 0) b = 0;
		      if (r > 255) r = 255;
		      if (g > 255) g = 255;
		      if (b > 255) b = 255;
		      
		      return r << 16 | g << 8 | b;
	   }
	   
	   public static int tint(int col, double r, double g, double b) {
		      int red = (col & 0xff0000) >> 16;
		      int green = (col & 0xff00) >> 8;
		      int blue = (col & 0xff);
		      
		      red += (int) r;
		      green += (int) g;   
		      blue += (int) b;
		      
		      if (red < 0) red = 0;
		      if (green < 0) green = 0;
		      if (blue < 0) blue = 0;
		      if (red > 255) red = 255;
		      if (green > 255) green = 255;
		      if (blue > 255) blue = 255;
		      
		      return red << 16 | green << 8 | blue;
		   }
	   
}














