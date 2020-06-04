package com.IB.LE2.world.entity.particle;

import com.IB.LE2.Game;
import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.asset.graphics.Sprite;

public class DamageIndicator extends Particle {

	// private Particle particle;
	transient private String text;
	transient private int color = 0xff000000;

	public DamageIndicator(int x, int y, int life, int amount, String text, int color) {
		super(x, y, life, amount);

		this.setX(x);
		this.setY(y);
		this.xx = x;
		this.yy = y;
		this.sprite = Sprite.get("BleedParticle");
		this.text = text;
		// this.invulnerable = true;
		this.color = color;
		this.life = life + (random.nextInt(15) - 4);
	}

	public void update() {
		time++;
		if (time >= 7400)
			time = 0;
		if (time > life)
			remove();
	}

	public void render(Screen screen) {
		try {
			Game.font8bit.render((int) x(), (int) y(), -4, this.color, this.text, screen, true, false);
			// screen.renderText((int)x - 8, (int)y - 14, text, 0xff);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

}
