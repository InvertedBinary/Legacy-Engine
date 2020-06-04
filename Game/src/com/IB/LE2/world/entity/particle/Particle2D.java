package com.IB.LE2.world.entity.particle;

import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.asset.graphics.Sprite;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.math.PVector;
import com.IB.LE2.world.entity.Entity;

public class Particle2D extends Entity {
	private static final long serialVersionUID = 1L;

	transient protected int time;
	transient protected int life;
	transient protected int amount;

	transient protected double xa, ya;

	public Particle2D(PVector pos, PVector Vo, Sprite sp, int life, int amount) {
		init(pos, Vo, sp, life, amount);
	}

	public void init(PVector pos, PVector Vo, Sprite sp, int life, int amount) {
		this.pos().set(pos);
		this.vel().set(Vo);

		this.sprite = sp;

		this.life = life;
		this.amount = amount;
	}

	private transient PVector Gravity = new PVector(0, VARS.Ag);

	public void update() {
		time++;
		if (time > life)
			remove();

		if (y() > (45 * 32)) {
			setY((38 * 32));
		}

		this.vel().add(Gravity);

		ya = vel().y();
		xa = vel().x();

		move(xa, ya);

	}

	public void render(Screen screen) {
		screen.renderSprite((int) x(), (int) y(), sprite, true);
	}

	private void move(double x, double y) {

		if (!collision(x() + x, y() + y)) {
			this.setX(x() + x);
		} else {
			vel().x(vel().x() - xa / 3);
		}

		if (!collision(x(), y() + y)) {
			this.setY(y() + y);
		} else {
			if (ya > 0.5) {
				vel().y(vel().y() - ya / 2);
				vel().x(xa - xa / 3);
			}
		}
	}
	
	private boolean collision(double x, double y) {
		return false;
	}
}
