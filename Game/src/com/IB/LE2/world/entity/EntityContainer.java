package com.IB.LE2.world.entity;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.helpers.DefaultHandler;

import com.IB.LE2.world.entity.emitter.Emitter;
import com.IB.LE2.world.entity.mob.PlayerMP;
import com.IB.LE2.world.entity.particle.Particle;
import com.IB.LE2.world.entity.projectile.Projectile;

public class EntityContainer extends DefaultHandler
{
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Projectile> Projectiles = new ArrayList<Projectile>();
	public List<Particle> particles = new ArrayList<Particle>();
	public List<PlayerMP> players = new ArrayList<PlayerMP>();
	public List<Emitter> emitters = new ArrayList<Emitter>();
	public List<Entity> entitiesList = new ArrayList<Entity>();

	public EntityContainer()
	{
		
	}
	
	public boolean transferEntity(Entity e)
	{
		return false;
	}
	
}
