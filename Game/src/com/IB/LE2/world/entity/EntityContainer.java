package com.IB.LE2.world.entity;

import java.util.ArrayList;
import java.util.List;

import com.IB.LE2.Boot;
import com.IB.LE2.world.entity.emitter.Emitter;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.entity.mob.PlayerMP;
import com.IB.LE2.world.entity.particle.Particle;
import com.IB.LE2.world.entity.projectile.Projectile;

public class EntityContainer {
	public List<Entity> entities = new ArrayList<>();
	public List<Projectile> projectiles = new ArrayList<>();
	public List<Particle> particles = new ArrayList<>();
	public List<PlayerMP> players = new ArrayList<>();
	public List<Emitter> emitters = new ArrayList<>();
	public List<Entity> all = new ArrayList<>();

	public EntityContainer() {

	}

	public boolean transferEntity(Entity e) {
		return false;
	}
	
	public void remove(Entity e) {
		all.remove(e);
		entities.remove(e);
		projectiles.remove(e);
		particles.remove(e);
		players.remove(e);
		emitters.remove(e);
	}
	
	protected void remove() {
		for(int i = 0; i < all.size(); i++)
	         if(all.get(i).isRemoved()) all.remove(i);
		for (int i = 0; i < entities.size(); i++) 
			if (entities.get(i).isRemoved()) entities.remove(i);
		for (int i = 0; i < projectiles.size(); i++)
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		for (int i = 0; i < particles.size(); i++)
			if (particles.get(i).isRemoved()) particles.remove(i);
		for (int i = 0; i < players.size(); i++) 
			if (players.get(i).isRemoved()) players.remove(i);
		for (int i = 0; i < emitters.size(); i++)
			if (emitters.get(i).isRemoved()) emitters.remove(i);
	}

	public void add(Entity e) {
		e.init(Boot.getLevel());
		all.add(e);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((PlayerMP) e);
			e.added();
		} else if (e instanceof Emitter) {
			emitters.add((Emitter) e);
		} else {
			entities.add(e);
		}
	}


}
