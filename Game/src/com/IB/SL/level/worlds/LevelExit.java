package com.IB.SL.level.worlds;

import org.w3c.dom.Element;

public class LevelExit {
	
	public String path;
	public int send_x, send_y;
	public int x, y, w, h;
	
	public LevelExit(Element eElement) {
		path = (eElement.getAttribute("to"));
		
		send_x = Integer.parseInt(eElement.getAttribute("x"));
		send_y = Integer.parseInt(eElement.getAttribute("y"));
		
		x = Integer.parseInt(eElement.getAttribute("xo"));
		y = Integer.parseInt(eElement.getAttribute("yo"));
		
		w = Integer.parseInt(eElement.getAttribute("w"));
		h = Integer.parseInt(eElement.getAttribute("h"));
	}
	
	public LevelExit(int x, int y, int w, int h, String path, int sx, int sy) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.path = path;
		this.send_x = sx;
		this.send_y = sy;
	}
	
	public String toString() {
		return "A level exit to: " + this.path + " at x: " + send_x + " and y: " + send_y;
	}
}