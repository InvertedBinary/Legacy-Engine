package com.IB.LE2.input.UI.components;

import com.IB.LE2.Boot;
import com.IB.LE2.input.UI.components.basic.UI_Clickable;
import com.IB.LE2.input.UI.components.basic.UI_Root;
import com.IB.LE2.input.UI.components.listeners.UI_SliderListener;
import com.IB.LE2.input.hardware.Mouse;
import com.IB.LE2.media.audio.Audio;
import com.IB.LE2.media.graphics.Screen;

public class UI_Slider extends UI_Root implements UI_Clickable {
	
	public int width, pos;
	
	public int startPos;
	
	public int railCol = 0xFFFFFF;
	public int slideCol = 0x000000;
	
	UI_SliderListener listener;
	
	public UI_Slider(int x, int y, int width, int StartPos) {
		this.x = x;
		this.y = y;
		this.width = width;
		
		this.SetSliderPos(StartPos);
		this.startPos = this.pos;
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
				this.listener.PositionChanged();
				PlayAudioFeedback();
			}
		}
	}
	
	public void PlayAudioFeedback() {
		Audio.Play("Click");
	}
	
	private void UpdateSliderPos() {
		SetSliderPos((Mouse.getX() / Boot.scale) - x);
	}
	
	private void SetSliderPos(int newPos) {
		if (newPos > width) {
			newPos = width;
		}
		
		if (newPos < 0) {
			newPos = 0;
		}
		
		this.pos = newPos;
	}
	
	public void render(Screen screen) {
		screen.DrawFillRect(x, y, width, 1, this.railCol, false);
		screen.DrawFillRect(x + pos - 1, y - 4, 2, 8, this.slideCol, false);
	}

	public void addListener(UI_SliderListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean InBounds() {
		return checkBounds(x, y - 6, width, 10);
	}

	@Override
	public void Clicked() { this.startPos = pos; }

	@Override
	public void Hovered() {  }

	@Override
	public void UnsetHover() {  }
	
	@Override
	public void Dragged() {
		this.dragging = true;
	}

	@Override
	public void OnDownClick() {
		this.startPos = pos;
		
		this.UpdateSliderPos();
		this.listener.PositionChanged();
		PlayAudioFeedback();
	}
	
}
