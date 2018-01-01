package com.IB.SL.util.shape;

public class Rectangle extends Polygon {

	float w, h;
	
	public Rectangle(float xo, float yo, float w, float h) {
		this.xo = xo;
		this.yo = yo;
		this.w = w;
		this.h = h;
		initShape();
	}
	
	public void initShape() {
		addVertex(xo, yo); //Top left
		addVertex(xo + w, yo); //Top right
		addVertex(xo + w, yo + h); //Bottom right
		addVertex(xo, yo + h); // Bottom left
		this.vertices.add(new Vertex(xo, yo));
		init();
	}
	
}
