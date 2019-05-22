package com.IB.LE2.input.UI.components;

import com.IB.LE2.input.UI.components.basic.UI_Clickable;
import com.IB.LE2.input.UI.components.basic.UI_Root;
import com.IB.LE2.input.UI.components.listeners.UI_ButtonListener;
import com.IB.LE2.media.audio.Audio;
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;

public class UI_Button extends UI_Root implements UI_Clickable {
	
	public Sprite sprite;
	public AnimatedSprite animSprite;
	
	public int x;
	public int y;
	public int z;
	
	public int width;
	public int height;

	public boolean hovering;
	
	public boolean render = true;
	public boolean isanim = false;
	public boolean transAnim = false;
	
	private UI_ButtonListener listener;

	public UI_Button(int x, int y, Sprite sprite) {
		this.sprite = sprite;
		init(x, y, sprite.getWidth(), sprite.getHeight());
	}
	
	public UI_Button(int x, int y, Sprite sprite, boolean transAnim) {
		this.sprite = sprite;
		this.transAnim = transAnim;
		init(x, y, sprite.getWidth(), sprite.getHeight());
	}
	
	public UI_Button(int x, int y, int width, int height) {
		this.render = false;
		init(x, y, width, height);
	}
	
	public UI_Button(int x, int y, AnimatedSprite sprite) {
		this.animSprite = sprite;
		this.isanim = true;
		sprite.setFrame(0);
		init(x, y, sprite.getSprite().getWidth(), sprite.getSprite().getHeight());
	}
	
	public void init(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public UI_Button addListener(UI_ButtonListener listener) {
		this.listener = listener;
		return this;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public boolean InBounds() {
		return checkBounds(x, y, width, height);
	}
	
	@Override
	public void Clicked() {
		if (listener != null)
		listener.ButtonClick();
		
		Audio.Play("Click");
	}
	
	@Override
	public void Hovered() {
		if (listener != null)
		listener.ButtonHover();
		
		this.hovering = true;
		if (isanim && this.animSprite.getFrame() != 1) {
			this.animSprite.setFrame(1);
		}
	}
	
	@Override
	public void UnsetHover() {
		if (isanim) {
			this.animSprite.setFrame(0);
		}
		
		this.hovering = false;
	}
	
	@Override
	public void Dragged() {
	}
	
	public void render(Screen screen) {
		if (render) {
			if (isanim) {
				this.sprite = animSprite.getSprite();
			}
			
			if (transAnim && !this.hovering || this.isanim) {
				screen.renderAlphaSprite(x, y, sprite);
			} else {
				screen.renderSprite(x, y, sprite, false);
			}
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Sprite getSprite() {
		if (!this.isanim)
			return sprite;
		else
			return animSprite.getSprite();
	}

	@Override
	public void unload() {
		hovering = false;
	}

	@Override
	public void OnDownClick() {
		Audio.Play("Click"); // Probably should let Lua handle this audio feedback eventually
	}

}
