package com.IB.SL.entity.spawner;

import com.IB.SL.entity.particle.WalkingParticle;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;

public class WalkingParticleSpawner extends Spawner{

	@SuppressWarnings("unused")
	private int life;
	
	public WalkingParticleSpawner(int x, int y, int life, int amount, Level level, Sprite sprite) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
			level.add(new WalkingParticle(x, y, life, amount, sprite));
			remove();
			}
		}
	}

