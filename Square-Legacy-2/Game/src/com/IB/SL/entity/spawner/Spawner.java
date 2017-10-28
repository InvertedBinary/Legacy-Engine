package com.IB.SL.entity.spawner;

import com.IB.SL.entity.Entity;
import com.IB.SL.level.Level;

public  abstract class Spawner extends Entity {


	
	public enum Type {
		MOB, PARTICLE, ITEM;
	}

	@SuppressWarnings("unused")
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
		
	}
	
}
