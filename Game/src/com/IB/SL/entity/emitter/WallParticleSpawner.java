package com.IB.SL.entity.emitter;

import com.IB.SL.entity.particle.WallParticle;
import com.IB.SL.level.Level;

public class WallParticleSpawner extends Emitter{

	@SuppressWarnings("unused")
	private int life;
	
	public WallParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
				level.add(new WallParticle(x, y, life, amount));
				remove();
			}
		}
	}

