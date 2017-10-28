package com.IB.SL.entity.spawner;

import com.IB.SL.entity.particle.Rock;
import com.IB.SL.level.Level;

public class RockSpawner extends Spawner{

	@SuppressWarnings("unused")
	private int life;
	
	public RockSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
			level.add(new Rock(x, y, life, amount));
			remove();
			}
		}
	}

