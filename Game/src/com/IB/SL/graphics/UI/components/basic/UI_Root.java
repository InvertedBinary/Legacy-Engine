package com.IB.SL.graphics.UI.part;

import com.IB.SL.Boot;
import com.IB.SL.graphics.Screen;
import com.IB.SL.input.Mouse;

public abstract class UI_Root
{

	public boolean enabled = true;
	
	public abstract void update();
	public abstract void render(Screen screen);
	
	public void unload() { }
	
	public void enable() {
		this.enabled = true;
	}

	public void disable() {
		this.enabled = false;
	}

	public boolean checkBounds(int x, int y, int width, int height) {
		int scale = Boot.scale;
		x *= scale;
		y *= scale;
		width *= scale;
		height *= scale;

		if (Mouse.getX() < x + width && Mouse.getX() > x && Mouse.getY() < y + height && Mouse.getY() > y) {
			return true;
		} else {
			return false;
		}
	}

}
