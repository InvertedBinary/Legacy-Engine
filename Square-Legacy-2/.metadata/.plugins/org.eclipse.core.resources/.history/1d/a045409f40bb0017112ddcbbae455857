package com.IB.SL.entity.abilities;

import com.IB.SL.Game;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.spawner.WallParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class BlinkSpell extends Ability{
	
	public static int time = 0;
	
	public BlinkSpell(double x, double y, double dir) {
		super(x, y, dir);
		range = random.nextInt(80) + 260;
		speed = 2;
		
		basicInitialization();
	}
	
	public void basicInitialization() {
		this.name = "Blink_Spell";
		sprite = Sprite.WizardProjectile2;
		unlock = 3;
		damage = 5;
		FIRE_RATE = 300;
		manaCost = 3;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.displaySprite = sprite.Blink_Display;
		this.id = 6;

	}
	
	public boolean use(Projectile p, Ability ability, Mob origin) {
		super.use(p, ability, origin);
		if (((Player)origin).ridingOn == null) {
		if(Game.getGame().getLevel().returnTileXY(Screen.xo, Screen.yo).solid() == false || Game.getGame().devModeOn) {
			Game.getGame().getLevel().add(new WallParticleSpawner((int) origin.getX(), (int) origin.getY(), 20, 200, Game.getGame().getLevel()));
			Game.getGame().getPlayer().x = (Screen.xo << Game.TILE_BIT_SHIFT) + 8;
			Game.getGame().getPlayer().y = (Screen.yo << Game.TILE_BIT_SHIFT) + 8;
			Game.getGame().getLevel().add(new WallParticleSpawner((int) origin.getX(), (int) origin.getY(), 20, 200, Game.getGame().getLevel()));
			Sound.Play(Sound.Spell2,  false);
			return true;
		} else {
			return false;
		}
		} else {
			return false;			
		}
	}
	
	
	public void render(Screen screen) {
	}
}
