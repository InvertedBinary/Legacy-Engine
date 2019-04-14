package com.IB.SL.graphics.UI.part;

import com.IB.SL.Boot;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.UI.listeners.UI_SliderListener;
import com.IB.SL.input.Mouse;
import com.IB.SL.util.Sound;

public class UI_Slider extends UI_Root implements UI_Clickable {
	
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
		if (dragging) {
			this.UpdateSliderPos();

			if (Mouse.getButton() != 1) {
				dragging = false;
				PlayAudioFeedback();
			}
		}
	}
	
	public void PlayAudioFeedback() {
		Sound.Play(Sound.Click);
	}
	
	private void UpdateSliderPos() {
		int newPos = (Mouse.getX() / Boot.scale) - x;
		if (newPos > width) {
			newPos = width;
		}
		
		if (newPos < 0) {
			newPos = 0;
		}
		
		if (newPos != pos) {
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

	@Override
	public boolean InBounds() {
		return checkBounds(x, y - 6, width, 10);
	}

	@Override
	public void Clicked() {
		PlayAudioFeedback();
		this.UpdateSliderPos();
	}

	@Override
	public void Hovered() { }

	@Override
	public void UnsetHover() { }
	
	@Override
	public void Dragged() {
		if (!dragging)
			PlayAudioFeedback();

		this.dragging = true;
	}
	
}
