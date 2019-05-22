package com.IB.LE2.world.entity.projectile;

import java.util.List;

import com.IB.LE2.Game;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.world.entity.emitter.WallParticleSpawner;
import com.IB.LE2.world.entity.mob.PlayerMP;

@Deprecated
public class WizardProjectile2 extends Projectile{
	
	public static int FIRE_RATE = 30;
	public static int time = 0;
	public boolean particles = false;;
	public WizardProjectile2(double x, double y, double dir, boolean particles) {
		super(x, y, dir);
		range = random.nextInt(80) + 260;
		speed = 2.6;
		damage = 1;
		sprite = Sprite.WizardProjectile2;
		this.particles = particles;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.id = 5;
	}
	
	public void update() {
		time++;
		List<PlayerMP> players = level.players;
		PlayerCollision(players, this); 
		
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {		
			level.add(new WallParticleSpawner((int) (x + nx), (int) (y + ny), 2, 2, level));
			 remove();
	} else if (particles) {
		level.add(new WallParticleSpawner((int) (x + nx), (int) (y + ny), 2, 2, level));
	}

		move();
		}

	protected void move() {
		
		x += nx;
		y += ny;
		if (distance() > range) remove();
		
		
		if (Game.devModeInfoOn) {
		System.out.println("Distance (In Tiles): " + distance() / 16);
	}
	}




	
	
	

	
	public double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin -y) * (yOrigin - y)));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int)x - 8,(int)y - 14, this);
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