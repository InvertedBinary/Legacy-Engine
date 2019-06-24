package com.IB.LE2.input.UI.components;

import java.awt.event.KeyEvent;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.input.Mouse;
import com.IB.LE2.input.UI.components.basic.UI_Clickable;
import com.IB.LE2.input.UI.components.basic.UI_Keylistener;
import com.IB.LE2.input.UI.components.basic.UI_Root;
import com.IB.LE2.input.UI.components.listeners.UI_TextInputListener;
import com.IB.LE2.media.graphics.Font16x;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;

public class UI_TextField extends UI_Root implements UI_Clickable, UI_Keylistener {
	
	UI_TextInputListener listener;
	private int x, y, maxchars;
	private int width, height;
	private boolean scrollable, numeric, sensitive_input;
	
	private final int padding = 4;
	private final int font_size = 16;
	
	private Font16x font = Game.font16bit;

	private String display_text = "";
	public String prompt_text = "Text Field...";
	
	@SuppressWarnings("unused")
	private int start_selection_pos = 0;
	
	private int start_display_index = 0;
	private int cursor_pos = 0;
	
	private char cursor_frame1 = 'l';
	private char cursor_frame2 = ':';
	private char cursor_char = cursor_frame1;
	
	private final String valid_punctuation = ".,;:!@$%()-+";
	private final int char_spacing = -3;
	
	private int border_size = 1;
	private int border_color = 0xffFFFFFF;
	private int background_color = 0xff302B23;
	
	private boolean ShrinkToSize = false;
	private int background_width = 0;
	
	private Sprite CoverSprite;
	
	private boolean hovering = false;
	
	private int blink_timer = 0;
	
	public UI_TextField(int x, int y, int maxchars, boolean scrollable, boolean numeric, boolean protected_field) {
		this.x = x;
		this.y = y;
		this.maxchars = maxchars;
		this.scrollable = scrollable;
		this.numeric = numeric;
		this.sensitive_input = protected_field;
		
		this.width = padding*2 + (maxchars) * font_size;
		this.height = padding*2 + font_size;
		
		CoverSprite = new Sprite(width, height, 0xEE000000);
	}

	public UI_TextField addListener(UI_TextInputListener listener) {
		this.listener = listener;
		return this;
	}
	
	@Override
	public boolean InBounds() {
		return this.checkBounds(x, y, background_width, height);
	}

	@Override
	public void Clicked() {
		if (!focused)
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
		this.hovering = true;
	}

	@Override
	public void UnsetHover() {
		this.hovering = false;
	}

	@Override
	public boolean KeyPressed(KeyEvent e) {
		if (!focused) return false;
		
		char keychar = e.getKeyChar();
		int keycode = e.getKeyCode();

		switch (keycode) {
			case KeyEvent.VK_ESCAPE:
				this.focused = false;
				break;
				
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_BACK_SPACE:
				if (!Boot.get().getInput().ctrl)
					this.DeleteChar(this.cursor_pos);
				else
					this.DeleteBulk(this.cursor_pos);
				break;
				
			case KeyEvent.VK_ENTER:
				this.SubmitText();
				break;
			
			case KeyEvent.VK_KP_LEFT:
			case KeyEvent.VK_LEFT:
				this.ShiftCursorLeft(1);
				break;
			case KeyEvent.VK_KP_RIGHT:
			case KeyEvent.VK_RIGHT:
				this.ShiftCursorRight(1);
				break;
				
			default:
				if (PassesFilter(keychar)) {
					this.Insert(keychar);
					if (listener != null)
						listener.KeyEntered(keychar, true);
				}

				break;
		}

		if (listener != null)
		listener.KeyEntered(keychar, false);
		
		System.out.println("TEXT: " + text + "|");
		return true;
	}
	
	public boolean PassesFilter(char c) {
		if (!scrollable && text.length() == maxchars)
			return false;
		
		if (numeric)
			return Character.isDigit(c);
		
		return (Character.isAlphabetic(c) || Character.isDigit(c)
				|| (c == ' ' && !text.endsWith(" ") && text.length() != 0)
				|| (valid_punctuation.contains("" + c)));
	}
	
	@Deprecated
	public void DropLastWord() {
		if (text.contains(" ")) {
			if (!text.endsWith(" ")) {
				String cachetext = text;
				SetText(text.substring(0, text.lastIndexOf(' ')));
				ShiftCursorLeft(cachetext.length() - text.length());
				
				if (this.start_display_index > 0)
					start_display_index = ((start_display_index - cachetext.length() - text.length()) < 0) ? 0 : (start_display_index - cachetext.length() - text.length());
				
			} else {
				DropLastChar();
			}
		} else {
			SetText("");
			this.CursorToHome();
		}
	}
	
	@Deprecated
	public void DropLastChar() {
		if (text.length() > 0)
		SetText(text.substring(0, text.length() - 1));
		this.ShiftCursorLeft(1);
		
		if (this.start_display_index > 0)
			start_display_index--;
	}
	
	public void Insert(int number) {
		if (number > 9 || number < 0) 
			return;
		
		Insert(Integer.toString(number));
	}
	
	public void Insert(char letter) {
		Insert(Character.toString(letter));
	}
	
	public void Insert(String text) {
		String newtext = 
				  this.text.substring(0, (this.cursor_pos)) 
				+ text
				+ this.text.substring((this.cursor_pos), this.text.length());
		
		SetText(newtext);
		this.ShiftCursorRight(1);
		
		if (this.text.length() > maxchars)
			this.start_display_index++;
	}
	
	public void DeleteBulk(int position) {
		if (text.contains(" ")) {
			if (text.charAt(position - 1) != (' ')) {
				String cachetext = text;
				String pt = text.substring(0, text.substring(0, position).lastIndexOf(' '));
				String at = text.substring(position, text.length());
				System.out.println("PTAT: " + pt + at);
				SetText(pt + at);
				ShiftCursorLeft(cachetext.length() - text.length());
				
				if (this.start_display_index > 0)
					start_display_index = ((start_display_index - cachetext.length() - text.length()) < 0) ? 0 : (start_display_index - cachetext.length() - text.length());
				
			} else {
				DeleteChar(position);
			}
		} else {
			SetText("");
		}
	}
	
	public void DeleteChar(int position) {
		if (this.text.length() == 0) return;
		
		String newtext = this.text.substring(0, position - 1)
				 + this.text.substring(position, this.text.length());
		
		SetText(newtext);
		this.ShiftCursorLeft(1);
		
		if (this.start_display_index > 0)
			start_display_index--;
	}
	
	@Deprecated
	public void Append(int x) {
		if (x > 9 || x < 0) 
			return;
		
		Append("" + x);
	}
	
	@Deprecated
	public void Append(char c) {
		Append("" + c);
	}
	
	@Deprecated
	public void Append(String text) {
		/*if (this.numeric)
			if (!text.chars().allMatch(Character::isDigit))
				return;*/
		String newtext = this.text + text;
		SetText(newtext);
		
		this.ShiftCursorRight(1);
		
		/*if (this.text.length() > maxchars)
			this.start_display_index++;*/
	}
	
	public void SetText(String text) {
		this.text = text;
		//this.display_text = "";
		
		SetDisplayText(text);
		
		if (text.equals(""))
			this.CursorToHome();
	}
	
	private void SetDisplayText(String text) {
		if (this.sensitive_input)
			for (int i = 0; i < text.length() && i < maxchars; i++) {
				display_text += "*";
			}
		else if (this.text.length() > maxchars)
			display_text = text.substring(start_display_index, start_display_index + maxchars);
		else
			display_text = text;
		
		this.background_width = (!this.ShrinkToSize) ? ((maxchars+1) * (font_size + char_spacing)) + 1 :
			  					(display_text.length() * (font_size + char_spacing)) + 1;
		if (this.background_width == 1) this.background_width = (prompt_text.length() * (font_size + char_spacing)) + 1;
		
		CoverSprite = new Sprite(background_width, height, 0xAA000000);
	}
	
	public void Clear() {
		SetText("");
	}
	
	public void SubmitText() {
		if (listener != null)
		this.listener.SubmitInput(text);
	}
	
	public void ShiftCursor(int step) {
		int position = cursor_pos + step;
		
		if (position > this.maxchars && this.scrollable) {
			this.start_display_index++;
		}
		
		if (this.start_display_index > 0) {
			if (this.start_display_index == this.cursor_pos) {
				this.start_display_index--;
			}
		}
		
		MoveCursorTo(position);
	}
	
	public void ShiftCursorLeft(int step) {
		ShiftCursor(-step);
	}
	
	public void ShiftCursorRight(int step) {
		ShiftCursor(+step);
	}
	
	public void CursorToHome() {
		MoveCursorTo(0);
	}
	
	public void CursorToEnd() {
		MoveCursorTo(text.length());
	}

	
	public void MoveCursorTo(int pos) {
		if (pos > text.length())
			pos = text.length();
		
		if (pos < 0)
			pos = 0;
		
		this.cursor_pos = pos;
		
		System.out.println("Cursor Pos Changed.. " + cursor_pos);
		
		UpdateSplitText();
	}
	
	public void UpdateSplitText() {
	}
	
	public void BlinkCursor() {
		if (focused) {
			blink_timer++;

			if (blink_timer < 40) {
				cursor_char = cursor_frame1;
			} else
				if (blink_timer > 40 && blink_timer < 80) {
					cursor_char = cursor_frame2;
				} else
					if (blink_timer > 80) {
						blink_timer = 0;
					}
		} else {
			cursor_char = ' ';
		}
		
		//SetDisplayText();
	}
	
	@Override
	public void update() {
		if (this.text.length() == 0)
			if (!focused)
				SetDisplayText(this.prompt_text);
			else
				SetDisplayText("");
		
		if (focused && !hovering) {
			if (Mouse.getButton() != -1)
				focused = false;
		}
		
		BlinkCursor();
	}
	
	@Override
	public void render(Screen screen) {
		this.ShrinkToSize = false;
		String formatted_display = display_text;
		if (focused) {
			formatted_display = 
					  formatted_display.substring(0, (this.cursor_pos - this.start_display_index)) 
					+ cursor_char
					+ formatted_display.substring((this.cursor_pos - this.start_display_index), formatted_display.length());
			//System.out.println("FORMDSP: " + formatted_display);
			
			if (this.ShrinkToSize) //TODO: Clean this mess up..
				screen.drawFillRect(x, y, (background_width + font_size) + 2 * border_size, height + 2 * border_size, background_color, border_color, false);
			else
				screen.drawFillRect(x, y, background_width + 2 * border_size, height + 2 * border_size, background_color, border_color, false);
		} else {
			screen.drawFillRect(x, y, background_width + 2 * border_size, height + 2 * border_size, background_color, border_color, false);
		}
		
		//screen.drawRect(x, y, background_width + 1, height + 1, border_color, false);
		//screen.drawFillRect(x + 1, y + 1, background_width, height, background_color, false);
		font.render(x - font_size + padding/2 + 2, y + font_size / 4, char_spacing, 0xff000000, formatted_display, screen, false, false);
		font.render(x - font_size + padding/2 + 3, y + font_size / 4, char_spacing, 0xffFFFFFF, formatted_display, screen, false, false);
	
		if (!focused && !hovering)
			screen.renderAlphaSprite(x + 1, y + 1, this.CoverSprite);
	}

}
