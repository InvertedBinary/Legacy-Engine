package com.IB.SL.entity.abilities;

import com.IB.SL.Game;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.spawner.WallParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class HealingSpell extends Ability{
	
	
	public HealingSpell(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		range = random.nextInt(80) + 260;
		MDF = 5 + (int)(mob.stat_MDF * 0.15);
		
		basicInitialization();
	}
	
	public void basicInitialization() {
		this.name = "Healing_Spell";
		speed = 2;
		damage = 0;
		FIRE_RATE = 320;
		unlock = 0;
		this.manaCost = 8;
		sprite = Sprite.HealingSpell;
		displaySprite = Sprite.HealingSpell;
		this.id = 9;
	}

	
	
	@Override
	public boolean use(Projectile p, Ability ability, Mob origin) {
		super.use(p, ability, origin);
		if (origin.mobhealth == origin.maxhealth)
			return false;
		else {

			MDF = 5 + (int) (origin.stat_MDF * 0.15);
			double amountToHeal = MDF;
			if (MDF + origin.mobhealth > origin.maxhealth) {
				amountToHeal = origin.maxhealth - origin.mobhealth;
			} else {
				amountToHeal = MDF;
			}
			origin.mobhealth += amountToHeal;
			Sound.Play(Sound.Spell2, false);
			Game.getGame().getLevel().add(new WallParticleSpawner((int) origin.getX(), (int) origin.getY(), 20, 6, Game.getGame().getLevel()));
			return true;
		}
	}
	
	public void render(Screen screen) {
	}
}



