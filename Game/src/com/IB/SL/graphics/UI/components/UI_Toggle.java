package com.IB.SL.graphics.UI.components;

import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.UI.components.basic.UI_Clickable;
import com.IB.SL.graphics.UI.components.basic.UI_Root;
import com.IB.SL.graphics.UI.components.listeners.UI_ToggleListener;

public class UI_Toggle extends UI_Root implements UI_Clickable {

	private UI_ToggleListener listener;
	
	private AnimatedSprite sprite;
	private boolean checked = false;
	private int x, y;
	
	public UI_Toggle(int x, int y, boolean checked, AnimatedSprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		
		SetChecked(checked);
	}
	
	public UI_Toggle addListener(UI_ToggleListener listener) {
		this.listener = listener;
		return this;
	}
	
	public void Toggle() {
		SetChecked(!checked);
	}
	
	public void SetChecked(boolean state) {
		if (state)
			Check();
		else
			Uncheck();
	}
	
	public void Uncheck() {
		this.sprite.setFrame(0);
		this.checked = false;
		
		if (!checked)
			listener.Untoggled();
		listener.StateChanged();
	}
	
	public void Check() {
		this.sprite.setFrame(1);
		this.checked = true;
		
		if (checked)
			listener.Untoggled();
		listener.StateChanged();
	}
	
	@Override
	public boolean InBounds() {
		return false;
	}

	@Override
	public void Clicked() {
		
	}

	@Override
	public void OnDownClick() {
		Toggle();
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
	public void update() {
		
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite(x, y, sprite.getSprite(), false);
	}

}
