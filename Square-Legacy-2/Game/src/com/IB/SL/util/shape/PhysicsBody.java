package com.IB.SL.util.shape;

import com.IB.SL.VARS;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.PVector;
import com.IB.SL.graphics.Screen;
import com.IB.SL.util.PropertyEngine;

public class PhysicsBody extends PropertyEngine {
	public Polygon bounds;
	public Entity container;
	
	public float mass = 70;
	public float w = this.mass * VARS.Ag;
	
	public PVector vel = new PVector();
	public PVector pos = new PVector();
	
	public PhysicsBody(Entity e, Polygon p) {
		init(e, p);
	}
	
	public PhysicsBody(int x, int y, Entity e, Polygon p) {
		this.pos.x = x;
		this.pos.y = y;
		init(e, p);
	}
	
	public void init(Entity e, Polygon p) {
		this.container = e;
		this.bounds = p;
		set(VARS.PHYS_AWAKE, true);
		set(VARS.PHYS_CANSLEEP, false);
		set(VARS.PHYS_CANFLOAT, false);
		set(VARS.REND_WIREFRAME, false);
	}
	
	public void move() {
		pos.add(vel);
		
		bounds.translate((float)pos.x, (float)pos.y);
	}
	
	public void draw(Screen screen) {
		if (!get(VARS.REND_WIREFRAME)) {
		this.bounds.draw(screen);
		} else {
		this.bounds.drawWireframe(screen);
		}
	}
}
