package com.IB.SL.entity.projectile;

import java.util.List;

import com.IB.SL.entity.inventory.effects.Stun;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;


public class SwarmMine extends Projectile{
	
	public static int FIRE_RATE = 120;

	public SwarmMine(double x, double y, double dir) {
		super(x, y, dir);
		range = 600;
		speed = 1;
		damage = 7;
		sprite = Sprite.rotate(Sprite.SwarmMine, angle);
		this.breakParticle = 0;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.id = 7011;
	}
	
	public void update() {
		List<PlayerMP> players = level.players;
		PlayerCollision(players, this); 
	     
		
		
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
			 remove();
	}
	
		move();
		
	}

	protected void move() {
		time++;
		if (time > 1200) {
			remove();
		}
		
	}
	
	public void addEffect(PlayerMP player) {
		player.effects.addEffect(new Stun(player, 180));
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