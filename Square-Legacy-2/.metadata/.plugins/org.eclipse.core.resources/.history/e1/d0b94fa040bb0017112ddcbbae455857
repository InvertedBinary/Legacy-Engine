package com.IB.SL.entity.abilities;

import com.IB.SL.Game;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.spawner.BleedSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class Equilibrium extends Ability{
	
	public Equilibrium(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		range = random.nextInt(80) + 260;
		MDF = 5 + (int)(mob.stat_MDF * 0.25);
		
		basicInitialization();
	}
	
	public void basicInitialization() {
		speed = 2;
		damage = 0;
		this.name = "Equilibrium";
		sprite = Sprite.WizardProjectile2;
		this.displaySprite = sprite;
		unlock = 4;
		FIRE_RATE = 320;
		manaCost = 0;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		displaySprite = Sprite.Equil_Display;
		this.id = 7;
	}
	
	
	public void update() {
	}

		
	
	public boolean use(Projectile p, Ability ability, Mob origin) {
		super.use(p, ability, origin);
		if (origin.mana == origin.maxmana)
			return false;
		else {

			MDF = 5 + (int) (origin.stat_MDF * 0.15);
			double amountMana = MDF;
			if (MDF + origin.mobhealth > origin.maxhealth) {
				amountMana = origin.maxmana - origin.mana;
			} else {
				amountMana = MDF;
			}
			origin.mana += amountMana;
			origin.mobhealth -= origin.maxhealth * 0.2;
			Sound.Play(Sound.Spell2, false);
			Game.getGame().getLevel().add(new BleedSpawner((int) origin.getX(), (int) origin.getY(), 20, 200, Game.getGame().getLevel()));
			return true;
		}
	}
		
	
	
	public void render(Screen screen) {
	}
}



/*	for (int i = 0; i < Level.players.size(); i++) {
	if (x < Level.players.get(i).getX() + 5
        && x > Level.players.get(i).getX() - 5
        && y <  Level.players.get(i).getY() + 5
        && y >  Level.players.get(i).getY() - 5
        ) {
        remove();
        Level.players.get(i).mobhealth -= 1;
        Game.Health =  Level.players.get(i).mobhealth;
        level.add(new WallParticleSpawner((int) (x + nx), (int) (y + ny), 6, 2, level));
        level.add(new BleedSpawner((int) (x + nx), (int) (y + ny), 10, 8, level));
        
		if (Level.players.get(i).mobhealth <= 0){
			Level.players.get(i).remove();
			level.add(new ParticleSpawner((int) (x + nx), (int) (y + ny), 300000, 200, level));
				System.out.println("YOU DIED");
    			Game.Dead = false;
    			time++;
    			if (Level.players.get(i).isRemoved()) {
    				Player.respawn = true;
    		}
    	}
	}
}*/