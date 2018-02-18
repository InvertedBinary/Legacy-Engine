package com.IB.SL.entity.emitter;

import com.IB.SL.Boot;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.PVector;
import com.IB.SL.entity.particle.Particle2D;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;

public class Emitter extends Entity {
	private static final long serialVersionUID = 1L;
	
	int time = 0;
	int interval = -1;
	
	int amount = 0;
	int life = 0;
	
	PVector V_emit;

	public Emitter(int x, int y, int amount, Level level) {
		init(level);
		this.setX(x);
		this.setY(y);
	}
	
	public void render(Screen screen) {

	}
	
	public void update() {
		time++;
		if (time > 7400) time = 0;
		
		if (interval != -1) {
			if (time > interval) {
				emit();
				time = 0;
			}
		}
	}
	
	public void emit() {
		PVector Pos_emit = new PVector(x(), y());
		for (int i = 0; i < amount; i++) {
			PVector V_emit = new PVector(this.V_emit);
			V_emit.setX(V_emit.x() + Boot.randDouble(0, 3));
			V_emit.setY(V_emit.y() + Boot.randDouble(-3, 0));
			level.add(new Particle2D(Pos_emit, V_emit, 
					sprite, life, amount));
		}
	}
	
	public Emitter(double x, double y, PVector V_emit, Sprite sp, int amount, int life, Level level) {
		init(x, y, V_emit, sp, amount, life, -1, level);
		emit();
		remove();
	}

	public Emitter(double x, double y, PVector V_emit, Sprite sp, int amount, int life, int interval, Level level) {
		init(x, y, V_emit, sp, amount, life, interval, level);
	}
	
	public void init(double x, double y, PVector V_emit, Sprite sp, int amount, int life, int interval, Level level) {
		setX(x);
		setY(y);
		this.V_emit = new PVector(V_emit);
		this.sprite = sp;
		this.amount = amount;
		this.life = life;
		this.interval = interval;
		this.level = level;
	}

}
