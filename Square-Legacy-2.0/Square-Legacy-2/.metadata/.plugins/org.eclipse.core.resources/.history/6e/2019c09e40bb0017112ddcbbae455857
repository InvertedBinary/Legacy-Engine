package com.IB.SL.entity.abilities;

import com.IB.SL.Game;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.input.Mouse;

public abstract class Ability extends Projectile{
	
	public transient Sprite displaySprite;
	public int inSlot;
	public int manaCost = 0;
	public int staminaCost = 0;

	public int unlock;
	protected Projectile p;
	
	public Ability(double x, double y, double dir) {
		super(x, y, dir);
		this.displaySprite = sprite;
	}

	public boolean clickEvent() {
		return false;
	}

	public String getName() {
		return this.name;
	}
	
	public void renderAoE(Screen screen) {
		
	}
	
	public boolean use(Projectile p, Ability ability, Mob origin) {
		if (p != null) {			
		Game.getGame().getLevel().add(p);
		}
		return true;
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

/*	public boolean unlock(SkillTree tree, Ability ab) {
		boolean result = false;
		if (!ab.unlocked) {
		if (ab.unlock <= Game.getGame().getPlayer().Lvl) {
			Game.getGame().getPlayer().skillPoints -= 2;
			ab.unlocked = true;
			result = true;
			
			
			if (Game.getGame().getPlayer().abilities.add(ab));

		}
		}
		return result;
	}*/
	
}
