package com.IB.LE2.input.UI.components;

import com.IB.LE2.asset.graphics.AnimatedSprite;
import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.asset.graphics.Sprite;
import com.IB.LE2.input.UI.components.basic.UI_Root;

public class UI_Sprite extends UI_Root {

	public AnimatedSprite anim;

	public UI_Sprite(int x, int y, Sprite sprite) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}

	public UI_Sprite(int x, int y, AnimatedSprite anim) {
		this.anim = anim;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public AnimatedSprite getAnim() {
		return anim;
	}

	public Sprite getSprite() {
		if (anim != null)
			return anim.getSprite();
		else
			return sprite;
	}

	public void render(Screen screen) {
		if (visible)
		screen.renderSprite(RenderX(x), RenderY(y), getSprite(), false);
	}

	@Override
	public void update() {

	}

}
