package com.IB.SL.util.shape;

import java.util.ArrayList;

import com.IB.SL.VARS;
import com.IB.SL.entity.Entity;
import com.IB.SL.graphics.Screen;
import com.IB.SL.util.PropertyEngine;
import com.IB.SL.util.math.PVector;

public class PhysicsBody extends PropertyEngine {
	public Polygon bounds;
	public Entity container;
	
	public float mass = 70;
	public float w = this.mass * VARS.Ag;
	public byte maskbits = VARS.BIT_TILE | VARS.BIT_ENTITY;
	
	public PVector vel = new PVector();
	public PVector pos = new PVector();
	
	public PhysicsBody(Entity e, Polygon p) {
		init(e, p);
	}
	
	public PhysicsBody(int x, int y, Entity e, Polygon p) {
		this.pos.x(x);
		this.pos.y(y);
		init(e, p);
	}
	
	public void init(Entity e, Polygon p) {
		this.container = e;
		this.bounds = p;
		set(VARS.PHYS_AWAKE, true);
		set(VARS.PHYS_CANSLEEP, false);
		set(VARS.PHYS_NOGRAV, false);
		set(VARS.REND_WIREFRAME, false);
	}
	
	public void update() {
		Vertex v = bounds.vertices.get(0);
		
		if (pos.x() != v.x || pos.y() != v.y) {
		bounds.translateTo((float) pos.x(), (float) pos.y());
		}
	}

	public void move() {
		if (!get(VARS.PHYS_NOGRAV)) vel.y(vel.y() + (VARS.Ag / 9980));

		pos.add(vel);
	}
	
	public ArrayList<Vertex> verts() {
			return bounds.vertices;
	}
	
	public Vertex getVert(int index) {
			return verts().get(index);
	}
	
	public void draw(Screen screen) {
		//System.out.println(((Rectangle)bounds).w + " : " + pos.x);
		if (!get(VARS.REND_WIREFRAME)) {
		this.bounds.draw(screen);
		} else {
		this.bounds.drawWireframe(screen);
		}
	}
}
