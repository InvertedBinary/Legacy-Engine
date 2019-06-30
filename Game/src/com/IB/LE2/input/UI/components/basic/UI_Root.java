package com.IB.LE2.input.UI.components.basic;

import com.IB.LE2.Boot;
import com.IB.LE2.input.hardware.Mouse;
import com.IB.LE2.media.graphics.Screen;

public abstract class UI_Root {

	public String id = "-1";
	public String text = "";
	
	protected boolean focused = false;
	protected boolean enabled = true;
	
	//TODO: implement visibility toggle render(..) [if] return;
	protected boolean visible = true;
	
	public abstract void update();
	public abstract void render(Screen screen);
	
	public void unload() { }
	
	public void enable() {
		this.enabled = true;
	}

	public void disable() {
		this.enabled = false;
	}
	
	public boolean IsEnabled() {
		return this.enabled;
	}
	
	public void SetID(String ID) {
		this.id = ID;
	}
	
	public String GetID() {
		return id;
	}
	
	public void SetText(String text) {
		this.text = text;
	}

	public String GetText() {
		return text;
	}
	
	public void SetFocused(boolean b) {
		this.focused = b;
	}
	
	public boolean IsFocused() {
		return this.focused;
	}
	
	public void SetVisible(boolean b) {
		this.visible = b;
	}
	
	public boolean IsVisible() {
		return this.visible;
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
