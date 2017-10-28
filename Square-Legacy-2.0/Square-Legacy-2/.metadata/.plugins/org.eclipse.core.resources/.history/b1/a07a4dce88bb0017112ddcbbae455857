package com.IB.SL.entity.spawner;

import com.IB.SL.entity.inventory.item.consumables.ManaPot;
import com.IB.SL.level.Level;

public class ManaPotSpawner extends Spawner{

	@SuppressWarnings("unused")
	private int life;
	
	public ManaPotSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
			level.add(new ManaPot(x, y, life, amount));
			remove();
	
		}
		}
	}

