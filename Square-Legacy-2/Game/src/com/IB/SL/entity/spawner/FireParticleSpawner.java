package com.IB.SL.entity.spawner;

import com.IB.SL.entity.particle.Fire;
import com.IB.SL.level.Level;

public class FireParticleSpawner extends Spawner{

	@SuppressWarnings("unused")
	private int life;
	
	public FireParticleSpawner(int x, int y, int life, int amount, Level level, double dir) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
			level.add(new Fire(x, y, life, amount, dir));
			remove();
			}
		}
	}

