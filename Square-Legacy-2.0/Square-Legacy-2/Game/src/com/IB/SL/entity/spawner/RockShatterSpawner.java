package com.IB.SL.entity.spawner;

import com.IB.SL.entity.particle.RockShatter;
import com.IB.SL.level.Level;

public class RockShatterSpawner extends Spawner{

	@SuppressWarnings("unused")
	private int life;
	
	public RockShatterSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
			level.add(new RockShatter(x, y, life, amount));
			remove();

			}
		}
	}

