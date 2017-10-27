package com.IB.SL.entity.inventory.effects;

import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.Effect;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;

public class Poison extends Effect{
	int rate = 0;
	int life;

	public Poison(Entity e, int life, int rate) {
		this.life = life;
		this.e = e;
		this.name = "Poison";
		this.rate = rate;
		this.sprite = Sprite.display_Poison;
	}
	
	public void effectEquip() {
		System.out.println(e + " is now poisoned!");
	}
	
	public void effectDequip() {
		System.out.println(e + " is no longer poisoned!");
	}
	
	public void update() {
		super.update();
		
		if (time % rate == 0) {
			if (e != null) {
				
				try {
						if (e instanceof PlayerMP) {
							if (e.mobhealth > 1) {
							level.damagePlayer((int)x, (int)y, (PlayerMP)e, 0, 1, name, 0);
							}
						} else {							
							if (e.mobhealth > 1) {								
							level.damage((int)x, (int)y, (Mob)e, 0, 1, name, 0);							
							}
						}
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
			screen.renderLight((int) e.getX()- 200, (int) e.getY() - 200, 200, 20, 30 + (time % rate), 10);
		}
	}
	
}
