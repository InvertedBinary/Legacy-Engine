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
		this.x = v.x;
		this.y = v.y;
	}
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void translate(Vertex v) {
		translate(v.x, v.y);
	}
	
}
