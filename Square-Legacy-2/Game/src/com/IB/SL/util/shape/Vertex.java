package com.IB.SL.util.shape;

public class Vertex {

	public float x, y;
	
	public Vertex(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void Set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void transform(Vertex v) {
		Set(v.x, v.y);
	}
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void translate(Vertex v) {
		translate(v.x, v.y);
	}
	
	public void rotate(float angle) {
		float nx = (float)(x * Math.cos(angle) - y * Math.sin(angle));
		float ny = (float)(y * Math.cos(angle) + x * Math.sin(angle));
		Set(nx, ny);
	}
}
