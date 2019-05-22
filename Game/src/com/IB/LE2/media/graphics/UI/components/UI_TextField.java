package com.IB.LE2.media.graphics.UI.components;

import java.awt.event.KeyEvent;

import com.IB.LE2.Boot;
import com.IB.LE2.media.graphics.Font16x;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.UI.components.basic.UI_Clickable;
import com.IB.LE2.media.graphics.UI.components.basic.UI_Keylistener;
import com.IB.LE2.media.graphics.UI.components.basic.UI_Root;
import com.IB.LE2.media.graphics.UI.components.listeners.UI_TextInputListener;

public class UI_TextField extends UI_Root implements UI_Clickable, UI_Keylistener {
	
	UI_TextInputListener listener;
	private int x, y, maxchars;
	private int width, height;
	private boolean scrollable, numeric, sensitive_input;
	
	private final int border = 4;
	private final int font_size = 16;
	
	private boolean focused;
	
	private Font16x font = Boot.get().font16bit;
	
	private String display_text = "";
	private String text = "";
	
	private int cursor_pos = 0;
	private char cursor_frame1 = 'l';
	private char cursor_frame2 = ':';
	
	public UI_TextField(int x, int y, int maxchars, boolean scrollable, boolean numeric, boolean protected_field) {
		this.x = x;
		this.y = y;
		this.maxchars = maxchars;
		this.scrollable = scrollable;
		this.numeric = numeric;
		this.sensitive_input = protected_field;
		
		this.width = border*2 + width*font_size;
		this.height = border*2 + height;
	}

	public UI_TextField addListener(UI_TextInputListener listener) {
		this.listener = listener;
		return this;
	}
	
	@Override
	public boolean InBounds() {
		return false;
	}

	@Override
	public void Clicked() {
		this.focused =! focused;
	}

	@Override
	public void OnDownClick() {
		
	}

	@Override
	public void Dragged() {
		
	}

	@Override
	public void Hovered() {
		
	}

	@Override
	public void UnsetHover() {
		
	}

	@Override
	public void KeyPressed(KeyEvent e) {
		char keychar = e.getKeyChar();
		int  keycode = KeyEvent.getExtendedKeyCodeForChar(keychar);
		
		switch (keycode) {
			case KeyEvent.VK_ESCAPE:
				this.focused = false;
				break;
				
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_BACK_SPACE:
				if (!Boot.get().getInput().ctrl)
					this.DropLastChar();
				else
					this.DropLastWord();
				break;
				
			case KeyEvent.VK_ENTER:
				this.SubmitText();
				break;
				
			default:
				if (PassesFilter(keychar)) {
					this.Append(keychar);
					if (listener != null)
						listener.KeyEntered(keychar);
				}

				break;
		}

		
		System.out.println("TEXT: " + text + "|");
	}
	
	public boolean PassesFilter(char c) {
		if (numeric)
			return Character.isDigit(c);
		
		return (Character.isAlphabetic(c) || Character.isDigit(c)
				|| (c == ' ' && !text.endsWith(" ") && text.length() != 0));
	}
	
	public String GenTextWithCursor() {
		return text;
	}
	
	public void SetCursor(int pos) {
		this.cursor_pos = pos;
	}
	
	public void CursorHome() {
		this.cursor_pos = 0;
	}
	
	public void CursorEnd() {
		this.cursor_pos = text.length() - 1;
	}
	
	public void DropLastWord() {
		if (text.contains(" "))
			if (!text.endsWith(" "))
				SetText(text.substring(0, text.lastIndexOf(' ')));
			else
				DropLastChar();
		else 
			SetText(""); 
	}
	
	public void DropLastChar() {
		if (text.length() > 0)
		SetText(text.substring(0, text.length() - 1));
	}
	
	public void Append(int x) {
		if (x > 9 || x < 0) 
			return;
		
		Append("" + x);
	}
	
	public void Append(char c) {
		Append("" + c);
	}
	
	public void Append(String text) {
		/*if (this.numeric)
			if (!text.chars().allMatch(Character::isDigit))
				return;*/
		
		SetText(this.text + text);
	}
	
	private void SetText(String text) {
		this.text = text;
		this.display_text = "";
		
		if (this.sensitive_input)
			for (int i = 0; i < text.length(); i++) {
				display_text += "*";
			}
		else display_text = text;
	}
	
	public void Clear() {
		this.text = "";
		this.display_text = "";
	}
	
	public void SubmitText() {
		if (listener != null)
		this.listener.SubmitInput(text);
	}


	
	@Override
	public void update() {
		if (this.text.length() == 0)
			display_text = "Text Field..";
	}

	@Override
	public void render(Screen screen) {
		font.render(x, y, display_text, screen, true, false);
	}

}
