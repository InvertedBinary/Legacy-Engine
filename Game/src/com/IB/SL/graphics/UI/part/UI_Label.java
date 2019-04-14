package com.IB.SL.graphics.UI.part;

import java.awt.Desktop;
import java.net.URL;

import com.IB.SL.Game;
import com.IB.SL.graphics.Screen;
import com.IB.SL.input.Mouse;

public class UI_Label extends UI_Root implements UI_Clickable {
	public int x, y;
	public String text;
	
	public String hyperlink =  "";
	
	public int spacing = -2;
	public int font_size = 8;
	
	public int color = 0;
	public int hover_color = 0x0000FF;
	public int fallback_color = 0;
	
	public UI_Label(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
	}

	public void update() {
	}
	
	public void setDefaultColor(int color) {
		this.fallback_color = color;
		this.color = color;
	}
	
	@Override
	public boolean InBounds() {
		return checkBounds(x, y, font_size + (font_size + spacing) * text.length(), font_size);
	}

	@Override
	public void Clicked() {
		if (!hyperlink.equals("")) {
		    try {
		        Desktop.getDesktop().browse(new URL("https://" + hyperlink).toURI());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	}

	@Override
	public void Hovered() {
		this.color = hover_color;
	}

	@Override
	public void UnsetHover() {
		this.color = fallback_color;
	}

	@Override
	public void Dragged() { }
	
	public void render(Screen screen) {
		Game.font8bit.render(x - 1, y, spacing, 0x282828, text, screen, false, false);
		Game.font8bit.render(x, y, spacing, color, text, screen, false, false);

	}
	
}
