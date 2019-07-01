package com.IB.LE2.input.UI;

import java.util.ArrayList;

import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Font16x;
import com.IB.LE2.media.graphics.Font8x;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.world.entity.mob.Player;

public class GUI extends CheckBounds {
	private static final long serialVersionUID = 1L;

	transient public Font16x font;
	transient public Font8x font8x8;

	public transient int displayTime = 0;
	transient public int displayTimeM = 0;
	transient public int displayTimeS = 0;

	Player tempLoadInfo = null;

	/*transient public AnimatedSprite healthbar = new AnimatedSprite(SpriteSheet.anim_hb, 72, 32, 61);
	transient public AnimatedSprite manabar = new AnimatedSprite(SpriteSheet.anim_mb, 72, 32, 61);
	transient public AnimatedSprite staminabar = new AnimatedSprite(SpriteSheet.anim_sb, 72, 32, 61);
	transient public AnimatedSprite expbar = new AnimatedSprite(SpriteSheet.anim_eb, 156, 32, 151);*/
	// TODO: Custom progress bars in menus through tags

	public GUI() {
		font = new Font16x();
		font8x8 = new Font8x();
	}

	public void update() {
		// expBar.update();
		//for (int i = 0; i < UI_Manager.menu_stack.size(); i++) {
		//	UI_Manager.menu_stack.get(i).UpdateUnloaded();
		//}
		
		UI_Manager.UpdateMacros();
		UI_Manager.update();
	}

	public void render(Screen screen) {
		UI_Manager.render(screen);
	}

	public void renderString(Screen screen, String PersonNameGetter, int x, int y, int spacing, int color,
			boolean fixed, boolean background, boolean font8) {
		if (!font8) {
			font.render(x, y, spacing, color, PersonNameGetter, screen, fixed, background);
		} else {
			font8x8.render(x, y, spacing, color, PersonNameGetter, screen, fixed, background);
		}
	}

	public Sprite renderBar(int size, AnimatedSprite sprite, double max, double current) {
		double currentHealth = current;
		double maxHealth = max;
		double special = maxHealth - 1;
		double barSpace = size;
		double slope = maxHealth / barSpace;
		double hb = barSpace;
		double s1 = 0;

		ArrayList<Double> variables = new ArrayList<Double>();

		for (int i = 0; i < barSpace - 2; i++) {
			if (i == 0) {
				if (maxHealth > barSpace) {
					s1 = special - slope;// 38 OR 4.75
				} else if (maxHealth <= barSpace) {
					s1 = maxHealth - slope;// 38 OR 4.75
				}
				variables.add(s1);
			} else {
				variables.add(variables.get(i - 1) - slope);
			}
		}

		if (currentHealth < maxHealth) {
			if (currentHealth <= 0) {
				hb = 0;
			} else {
				hb = 1;
				for (int i = 0; i < barSpace - 2; i++) {
					if (currentHealth >= variables.get(i)) {
						hb = barSpace - i - 1;
						break;
					}
				}
			}
		}
		sprite.setFrame((int) size - (int) hb);
		return sprite.getSprite();
	}

}
