package com.IB.SL.entity;

import com.IB.SL.Boot;
import com.IB.SL.graphics.Screen;
import com.IB.SL.util.Debug;

public class PVector {

	public double x;
	public double y;

	public PVector() {
		set(0, 0);
	}

	public PVector(PVector vector) {
		set(vector.x, vector.y);
	}

	public PVector(double d, double e) {
		set(d, e);
	}

	public void set(double d, double e) {
		this.x = (int) d;
		this.y = (int) e;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public PVector add(PVector vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}
	
	public PVector addResult(PVector vector) {
		PVector p = new PVector(x + vector.x, y + vector.y);
		return p;
	}

	public PVector subtract(PVector vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	public PVector setX(double x) {
		this.x = x;
		return this;
	}

	public PVector setY(double y) {
		this.y = y;
		return this;
	}

	public boolean equals(Object object) {
		if (!(object instanceof PVector))
			return false;
		PVector vec = (PVector) object;
		if (vec.getX() == this.getX() && vec.getY() == this.getY())
			return true;
		return false;
	}
	
	public String toString() {
		return "X: " + x + ", Y: " + y;
	}
	
	public void draw(Screen screen) {
		Debug.drawTriangle(screen, Boot.get().font8x8, x, y, x * 1.5, y * 1.5);
	}
}
