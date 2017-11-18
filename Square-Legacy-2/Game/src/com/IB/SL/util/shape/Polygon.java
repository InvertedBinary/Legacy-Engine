package com.IB.SL.util.shape;

import java.util.ArrayList;

import com.IB.SL.Boot;
import com.IB.SL.graphics.Screen;
import com.IB.SL.util.Debug;

public class Polygon {

	float xo, yo;
	public ArrayList<Vertex> vertices = new ArrayList<>();
	public ArrayList<Vertex> FACTORY_VERTS = new ArrayList<>();

	public Polygon() {
		
	}
	
	public Polygon(float... args) {
		if (args.length % 2 != 0) {
			Boot.log("Malformed Polygon!", true);
			return;
		}
		for (int i = 0 ; i < args.length; i += 2) {
			addVertex(args[i], args[i + 1]);
		}
	}
	
	public Polygon(Vertex... args) {
		for (int i = 0 ; i < args.length; i++) {
			addVertex(args[i]);
		}
	}
	
	public Polygon(float x, float y) {
		this.xo = x;
		this.yo = y;
	}
	
	public void CC_VERTS() {
		for (int i = 0; i < vertices.size(); i++) {
			this.FACTORY_VERTS.add(vertices.get(i));
		}
	}
	
	public void addVertex(Vertex v) {
		this.vertices.add(v);
	}
	
	public void addVertex(float x, float y) {
		addVertex(createVertex(x, y));
	}
	
	public Vertex createVertex(float x, float y) {
		Vertex v = new Vertex(x, y);
		return v;
	}
	
	public void translate(float nx, float ny) {
		for (int i = 0; i < vertices.size(); i++) {
			vertices.get(i).translate(nx, ny);
		}
	}
	
	public void angularTranslate(float d, float angle) {
		float nx = ((float)Math.cos(angle) * d);
		float ny = ((float)Math.sin(angle) * d);
		translate(nx, -ny); //ny MUST be opposite due to top left origin
	}
	
	public void rotate(float angle) {
		for (int i = 0; i < vertices.size(); i++) {
			vertices.get(i).rotate(angle);
		}
	}
	
	public Polygon compositeShape(Polygon p) {
		Polygon result = new Polygon();
		result.vertices = this.vertices;
		for (int i = 0; i < p.vertices.size(); i++) {
			result.vertices.add(p.vertices.get(i));
		}
		return result;
	}
	
	public void RESET_SHAPE_TO_DEFAULT() {
	}
	
	public void drawWireframe(Screen screen) {
		for (int i = 0; i < vertices.size(); i++) {
			Vertex v = vertices.get(i);
			
			for (int j = 0; j < vertices.size(); j++) {
				Vertex v2 = vertices.get(j);
				Debug.drawLine(screen, (int)v.x, (int)v.y, (int)v2.x, (int)v2.y, 0xffFF00FF, false);
			}
		}
	}
	
	public void draw(Screen screen) {
		for (int i = 0; i < vertices.size(); i++) {
			Vertex v = vertices.get(i);
			Vertex v2;
			if (i < vertices.size() - 1) {
				 v2 = vertices.get(i + 1);
			} else {
				 v2 = vertices.get(0);
			}
			Debug.drawLine(screen, (int)v.x, (int)v.y, (int)v2.x, (int)v2.y, 0xffFF00FF, false);
		}
	}
	
}
