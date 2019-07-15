package com.IB.LE2.input.UI.components;

import com.IB.LE2.Boot;
import com.IB.LE2.input.UI.components.basic.UI_Clickable;
import com.IB.LE2.input.UI.components.basic.UI_Root;
import com.IB.LE2.input.hardware.Mouse;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;

public class UI_Canvas extends UI_Root implements UI_Clickable {

	private int width, height;
	private int draw_color = 0;
	private int background_color = 0xffFF00FF;
	private Sprite canvas;
	
	public UI_Canvas(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		ClearCanvas();
	}
	
	public void SetColor(int color) {
		this.draw_color = color;
	}
	
	public void SetBackground(int color) {
		this.background_color = color;
		
		this.canvas = new Sprite(width, height, background_color);
	}
	
	public void ClearCanvas() {
		SetBackground(background_color);
	}
	
	public void DrawAt(int x, int y) {
		this.canvas.pixels[x + y * width] = draw_color;
	}

	@Override
	public boolean InBounds() {
		return this.checkBounds(x, y, width, height);
	}

	@Override
	public void Clicked() {
	}

	@Override
	public void OnDownClick() {
	}

	@Override
	public void Dragged() {
		DrawAt(Mouse.getX()/Boot.scale - x, Mouse.getY()/Boot.scale - y);
	}

	@Override
	public void Hovered() {
	}

	@Override
	public void UnsetHover() {
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite(x, y, canvas, false);
	}
	
}
