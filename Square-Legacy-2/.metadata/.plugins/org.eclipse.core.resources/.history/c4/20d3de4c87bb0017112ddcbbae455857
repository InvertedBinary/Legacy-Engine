package com.IB.SL.entity.inventory.effects;

import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.Effect;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.spawner.FireParticleSpawner;
import com.IB.SL.graphics.Sprite;

public class onFire extends Effect{
	
	public onFire(Entity e, int life) {
		this.life = life;
		this.e = e;
		this.name = "Burning";
		this.sprite = Sprite.rotate(Sprite.fireParticle, 360);
	}
	
	public void effectEquip() {
		System.out.println(e + " is now burning!");
	}
	
	public void effectDequip() {
		System.out.println(e + " is no longer burning!");
	}
	
	public void update() {
		super.update();
		
		if (time % 35 == 0) {
			if (e != null) {
				
				try {
						if (e instanceof PlayerMP) {
							level.damagePlayer((int)x, (int)y, (PlayerMP)e, 0, 1, name, 0);
						} else {							
							level.damage((int)x, (int)y, (Mob)e, 0, 1, name, 0);							
						}
				} catch (Exception e) {
				}
				
			level.add(new FireParticleSpawner((int) (e.x), (int) (e.y - 8), 2, 2, level, time));
			level.add(new FireParticleSpawner((int) (e.x + 8), (int) (e.y), 2, 2, level, time));
			level.add(new FireParticleSpawner((int) (e.x - 8), (int) (e.y), 2, 2, level, time));

			}
			
		}
	}
	
	public void render() {
		
	}
	
}
