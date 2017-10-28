package com.IB.SL.graphics.UI.part;

import com.IB.SL.graphics.Screen;

public class UI_Slider extends UI_Root {
	
	public int x = 0;
	public int y = 0;
	public int x2 = 16;
	public int y2 = 3;
	public int pos = 10;
	
	public UI_Slider(int x, int y, int StartPos) {
		this.pos = StartPos;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getPos() {
		return pos;
	}
	
	public void update() {
		if (checkBounds(x, y - 5, x2, 10)) {
			System.out.println("Slider Greenlit!");
		}
	}
	
	public void render(Screen screen) {
		//Debug.drawFillRect(screen, x, y, x + x2, y2, 0x000000, true);

	}
	
}
