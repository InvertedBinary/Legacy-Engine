package com.IB.SL.entity.inventory.effects;

import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.Effect;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;

public class Regen extends Effect{
	int rate = 0;
	
	public Regen(Entity e, int life, int rate) {
		this.life = life;
		this.e = e;
		this.name = "Regeneration";
		this.rate = rate;
		this.sprite = Sprite.display_Regen;
	}
	
	public void effectEquip() {
		System.out.println(e + " is now regenerating!");
	}
	
	public void effectDequip() {
		System.out.println(e + " is no longer regenerating!");
	}
	
	public void update() {
		super.update();
		
		if (time % rate == 0) {
			if (e != null) {
				
				try {
					e.mobhealth += e.maxhealth / 20;
					if (e.mobhealth > e.maxhealth) e.mobhealth = e.maxhealth;
						
				} catch (Exception e) {
				}
				
			/*level.add(new FireParticleSpawner((int) (e.x), (int) (e.y - 8), 2, 2, level, time));
			level.add(new FireParticleSpawner((int) (e.x + 8), (int) (e.y), 2, 2, level, time));
			level.add(new FireParticleSpawner((int) (e.x - 8), (int) (e.y), 2, 2, level, time));*/

			}
			
		}
	}
	
	public void render(Screen screen) {
		if (e instanceof PlayerMP) {
			screen.renderLight((int) e.getX()- 200, (int) e.getY() - 200, 200, 80 - (time % rate), 30, 30);
		}
	}
	
}
