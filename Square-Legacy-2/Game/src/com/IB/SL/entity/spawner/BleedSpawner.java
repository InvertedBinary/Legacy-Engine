package com.IB.SL.entity.spawner;

import com.IB.SL.entity.particle.Bleed;
import com.IB.SL.level.Level;

public class BleedSpawner extends Spawner{

	@SuppressWarnings("unused")
	private int life;
	
	public BleedSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
			level.add(new Bleed(x, y, life, amount));
			remove();
			}
		}
	}

