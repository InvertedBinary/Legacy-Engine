package com.IB.SL.entity.inventory;

import com.IB.SL.entity.Entity;
import com.IB.SL.graphics.Screen;

public class Effect extends Entity {
	
	protected String name = "";
	protected int life = 0;
	protected int time = 0;
	protected Entity e;
	
	public void effectEquip() {
	}
	
	public void effectDequip() {
	}
	
	public String getName() {
		return name;
	}

	public void update() {
		time++;
		life--;
		if (time > life || e.health() <= 0 || e == null || e.removed) {
			effectDequip();
			e.effects.removeEffect(this);
			remove();
			return;
		}
	}
	
	public void render(Screen screen) {
		
	}
}
