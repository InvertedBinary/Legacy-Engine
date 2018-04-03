package com.IB.SL.graphics.UI.part;

import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.input.Mouse;

public class UI_Button extends UI_Root {
	
	public Sprite sprite;
	public AnimatedSprite animSprite;
	public int x;
	public int y;
	public int width;
	public int height;
	public boolean hover = false;
	public boolean clicked = false;
	public boolean render = true;
	public boolean isanim = false;
	public boolean transAnim = false;

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
	
	public void update() {
		if (checkBounds(x, y, width, height)) {
			hover = true;
			if (isanim && this.animSprite.getFrame() != 1) {
				this.animSprite.setFrame(1);
			}
			if (Mouse.getButton() == 1) {
				this.clicked = true;
				Mouse.setMouseB(-1);
			} else {
				this.clicked = false;
			}
		} else {
			if (isanim) {
				this.animSprite.setFrame(0);
			}
			hover = false;
			clicked = false;
		}
	}
	
	public void render(Screen screen) {
		if (render) {
			if (isanim) {
				this.sprite = animSprite.getSprite();
			}
			
			if (transAnim && !this.hover || this.isanim) {
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
		return sprite;
	}
}
