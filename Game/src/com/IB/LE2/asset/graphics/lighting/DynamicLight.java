package com.IB.LE2.asset.graphics.lighting;

import com.IB.LE2.Boot;
import com.IB.LE2.util.VARS;
import com.IB.LE2.world.level.TileCoord;
import com.IB.LE2.world.level.worlds.TiledLevel;

public class DynamicLight {

	private double x, y;
	private double strength = 300;
	private double radius = 5;
	private double attenuation = 1;
	
	private double MAX_RADIUS = 100;
	
	private double curstrength = 0;
	
	public DynamicLight() {
		
	}
	
	public DynamicLight(double strength, double radius, double attenuation) {
		this.strength = strength;
		this.radius = radius;
		this.attenuation = attenuation;
	}
	
	public void update(double x, double y) {
		if (curstrength < strength) curstrength++;
		int tilex = (int)x >> VARS.TILE_BIT_SHIFT;
		int tiley = (int)y >> VARS.TILE_BIT_SHIFT;
		this.x = tilex;
		this.y = tiley;
		
		if (radius == -1) radius = MAX_RADIUS;
		TileCoord[] tiles = ((TiledLevel)Boot.getLevel()).getTileNeighbors(tilex, tiley, (int)radius);
		for (int i = 0; i < tiles.length; i++) {
			double distance = TiledLevel.calcDist(tiles[i].tx(), tiles[i].ty(), tilex, tiley);
			if (distance > radius && radius != -1) continue;
			
			int location = tiles[i].tx() + tiles[i].ty() * Boot.getLevel().width;
			
			if (distance == 0) distance = 1;
			double light_level = curstrength / distance - strength * attenuation;
			if (light_level < 0) light_level = 0;
			
			illuminate(location, light_level);
		}
	}

	public void illuminate(int tileLocation, double value) {
		if (tileLocation < 0 || tileLocation > Boot.getLevel().dynamic_lightmap.length - 1) return;
		
		if (Boot.getLevel().dynamic_lightmap[tileLocation] < value) 
			Boot.getLevel().dynamic_lightmap[tileLocation] = (int) Math.ceil(value);
	}
	
	public void setStrength(int value) {
		this.strength = value;
	}
	
	public double getStrength() {
		return strength;
	}
	
	public double getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(int attenuation) {
		this.attenuation = attenuation;
	}
	
}
