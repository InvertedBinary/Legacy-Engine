package com.IB.SL.entity.particle;

import com.IB.SL.VARS;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.PVector;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;

public class Particle2D extends Entity {
	private static final long serialVersionUID = 1L;
	
	transient protected int time;
	transient protected int life;
	transient protected	int amount;
	
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
	
	PVector Gravity = new PVector(0, VARS.Ag);
	public void update() {
		time++;
		if (time > life) remove();
		
		if (y() > (45 * 32)) {
			setY((38 * 32));
		}
		
		this.vel().add(Gravity);
		
		ya = vel().y;
		xa = vel().x;
		
		move(xa, ya);
		
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int)x(), (int)y(), sprite, true); 
	}
	
	private void move(double x, double y) {
		
		if (!collision(x() + x, y() + y)) {
			this.setX(x() + x);
		} else {
			vel().x = -xa/3;
		}
		
		if (!collision(x(), y() + y)) {
			this.setY(y() + y);
		} else {
			if (ya > 0.5) {
			vel().y = -ya/2;
			vel().x = xa - xa/3;
			}
		}
	}

	public boolean collision(double x, double y) {
		boolean solidtwo = false;
		for(int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * 32) / 32;
			double yt = (y - c / 2 * 32) / 32;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int)Math.floor(xt);
			if (c / 2 == 0) iy = (int)Math.floor(yt);
			if (level.getTile(ix, iy).solid()) solidtwo = true;
		}
		return solidtwo;
	}

}
