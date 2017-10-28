package com.IB.SL.entity.inventory.item.equipables;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.graphics.Screen;
import com.IB.SL.input.Mouse;

public abstract class Weapon extends EquipableItem {
	
	protected Projectile p;
	
	public void attack(Projectile p, Weapon weapon, Mob origin) {
		System.out.println(p);
		Game.getGame().getLevel().add(p);
	}
	
	public void secondary(Projectile p, Weapon weapon, Mob origin) {
		 	System.out.println(p);
			Game.getGame().getLevel().add(p);
	}
	
	public void renderAoE(Screen screen) {
		
	}
	
	public static double angle() {
		double dx = Mouse.getX() - Game.getWindowWidth() / 2;
		double dy = Mouse.getY() - Game.getWindowHeight() / 2;
		double dir = Math.atan2(dy + 28, dx + 16);
		return dir;
	}
	
	public Projectile getProjectile() {
		return p;
	}
	
}
