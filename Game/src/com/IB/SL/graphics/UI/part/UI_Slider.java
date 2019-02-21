package com.IB.SL.graphics.UI.part;

import com.IB.SL.Boot;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.UI.listeners.UI_SliderListener;
import com.IB.SL.input.Mouse;

public class UI_Slider extends UI_Root {
	
	public int x, y, width, pos;
	
	public int railCol = 0xFFFFFF;
	public int slideCol = 0x000000;
	
	UI_SliderListener listener;
	
	public UI_Slider(int x, int y, int width, int StartPos) {
		this.x = x;
		this.y = y;
		this.width = width;
		
		if (StartPos < 0)
			StartPos = 0;
		
		if (StartPos > width)
			StartPos = width;
		
		this.pos = StartPos;
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
	
	private boolean dragging = false;
	public void update() {
		if (checkBounds(x, y - 4, width, 4)) {
			if (Mouse.getButton() == 1) {
				this.dragging = true;
			}
		}
		
		if (Mouse.getButton() != 1)
			this.dragging = false;
		
		if (dragging) {
			int newPos = (Mouse.getX() / Boot.scale) - x;
			if (newPos > width) {
				newPos = width;
			}
			
			if (newPos < 0) {
				newPos = 0;
			}
			
			this.pos = newPos;
			this.listener.PositionChanged();
		}
	}
	
	public void render(Screen screen) {
		screen.drawFillRect(x, y, width, 1, this.railCol, false);
		screen.drawFillRect(x + pos - 1, y - 4, 2, 8, this.slideCol, false);
	}

	public void addListener(UI_SliderListener listener) {
		this.listener = listener;
	}
	
}
