package com.IB.SL.util.shape;

public class Line extends Polygon {
	
	float m;
	
	public Line(float m, float b) {
		this.xo = 0;
		this.yo = b;
		this.m = m;
	}
	
	public float getPoint(float x) {
		return ((x * this.m) + this.yo);
	}
	
	
}
