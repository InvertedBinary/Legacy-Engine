package com.IB.SL.entity.spawner;

import com.IB.SL.entity.inventory.item.consumables.StaminaPot;
import com.IB.SL.level.Level;

public class StaminaPotSpawner extends Spawner{

	@SuppressWarnings("unused")
	private int life;
	
	public StaminaPotSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.ITEM, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
			level.add(new StaminaPot(x, y, life, amount));
			remove();

			}
		}
	}

