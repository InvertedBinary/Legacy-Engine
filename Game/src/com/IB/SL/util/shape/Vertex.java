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
	
	public void rotate(float angle, Vertex center) {
		float xx = x;
		float yy = y;
		double x1 = xx - center.x;
		double y1 = yy - center.y;

		double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
		double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);

		xx = (float) (x2 + center.x);
		yy = (float) (y2 + center.y);
		
		Set(xx, yy);
	}
	
	@Override
	public String toString()
		{
			return "| VERTEX ~ (" + x + ", " + y + ") |";
		}
}
