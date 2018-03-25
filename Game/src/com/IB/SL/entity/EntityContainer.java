package com.IB.SL.entity;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.helpers.DefaultHandler;

import com.IB.SL.entity.emitter.Emitter;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.particle.Particle;
import com.IB.SL.entity.projectile.Projectile;

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
