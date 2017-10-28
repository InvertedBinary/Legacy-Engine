package com.IB.SL.entity.spawner;

import com.IB.SL.entity.inventory.item.consumables.HealthPot;
import com.IB.SL.level.Level;

public class HealthPotSpawner extends Spawner{

	@SuppressWarnings("unused")
	private int life;
	
	public HealthPotSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
			level.add(new HealthPot(x, y, life, amount));
			remove();
			}
		}
	}

