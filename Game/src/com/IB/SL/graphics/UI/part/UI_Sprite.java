package com.IB.SL.graphics.UI.part;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;

public class UI_Sprite extends UI_Root {
	
	public Sprite sprite;
	public int x;
	public int y;
	
	public UI_Sprite(int x, int y, Sprite sprite) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void render(Screen screen) {
		screen.renderSprite(x, y, sprite, false);
	}
	
}
