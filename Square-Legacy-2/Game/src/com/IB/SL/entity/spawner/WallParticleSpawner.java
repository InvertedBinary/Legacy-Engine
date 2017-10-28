package com.IB.SL.entity.spawner;

import com.IB.SL.entity.particle.WallParticle;
import com.IB.SL.level.Level;

public class WallParticleSpawner extends Spawner{

	@SuppressWarnings("unused")
	private int life;
	
	public WallParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
				level.add(new WallParticle(x, y, life, amount));
				remove();
			}
		}
	}

