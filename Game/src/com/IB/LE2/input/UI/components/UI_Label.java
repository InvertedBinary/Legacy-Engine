package com.IB.LE2.input.UI.components;

import java.awt.Desktop;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.IB.LE2.Game;
import com.IB.LE2.input.UI.components.basic.UI_Clickable;
import com.IB.LE2.input.UI.components.basic.UI_Root;
import com.IB.LE2.media.graphics.Screen;

public class UI_Label extends UI_Root implements UI_Clickable {
	
	public String hyperlink =  "";
	
	public int spacing = -2;
	public int font_size = 8;
	
	public int color = 0;
	public int hover_color = 0x0000FF;
	public int fallback_color = 0;
	
	private int lines = 1;
	
	public UI_Label(int x, int y, String text) {
		this.x = x;
		this.y = y;
		SetText(text);
	}
	
	public void SetText(String text) {
		super.SetText(text);
		
		Matcher m = Pattern.compile("\r\n|\r|\n").matcher(text);
		while (m.find()) {
		    lines++;
		}
	}

	public void update() {
	}
	
	public void setDefaultColor(int color) {
		this.fallback_color = color;
		this.color = color;
	}
	
	@Override
	public boolean InBounds() {
		return !this.hyperlink.equals("") && checkBounds(RenderX(x), RenderY(y), font_size + (font_size + spacing) * text.length(), font_size * lines);
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
		Game.font8bit.render(RenderX(x) - 1, RenderY(y), spacing, 0x282828, text, screen, false, false);
		Game.font8bit.render(RenderX(x), RenderY(y), spacing, color, text, screen, false, false);
	}

	@Override
	public void OnDownClick()
	{
		// TODO Auto-generated method stub
		
	}
	
}
