package com.IB.SL.util.shape;

import java.util.ArrayList;

import com.IB.SL.Boot;
import com.IB.SL.VARS;
import com.IB.SL.graphics.Screen;
import com.IB.SL.util.Debug;
import com.IB.SL.util.PropertyEngine;

public class Polygon extends PropertyEngine {

	float xo, yo;
	
	public ArrayList<LineSegment> sides = new ArrayList<>();
	public ArrayList<Vertex> vertices = new ArrayList<>();
	public ArrayList<Vertex> FACTORY_VERTS = new ArrayList<>();

	public Polygon() {
		init();
	}
	
	public Polygon(float... args) {
		if (args.length % 2 != 0) {
			Boot.log("Malformed Polygon!", true);
			return;
		}
		for (int i = 0 ; i < args.length; i += 2) {
			addVertex(args[i], args[i + 1]);
		}
		init();
	}
	
	public Polygon(Vertex... args) {
		for (int i = 0 ; i < args.length; i++) {
			addVertex(args[i]);
		}
		init();
	}
	
	public Polygon(float x, float y) {
		this.xo = x;
		this.yo = y;
		init();
	}
	
	
	public Polygon(Polygon bounds) {
		this.xo = bounds.xo;
		this.yo = bounds.yo;
		for (Vertex v : bounds.vertices) {
			this.vertices.add(v);
		}
		init();
	}

	public void init() {
		set(VARS.REND_LOCALLY, false);
		calcSides();
		CC_VERTS();
	}
	
	public void calcSides() {
		this.sides.clear();
		if (vertices.size() > 2) {
			for (int i = 0; i < vertices.size(); i++) {
				Vertex v = vertices.get(i);
				Vertex v2;
				if (i < vertices.size() - 1) {
					 v2 = vertices.get(i + 1);
				} else {
					 v2 = vertices.get(0);
				}
				
				LineSegment ls = new LineSegment(v, v2);
				this.sides.add(ls);
			}
			//System.out.println("NUM SIDES: " + sides.size() + " NUM VERTICES: " + vertices.size());
		}
	}
	
	public void CC_VERTS() {
		for (int i = 0; i < vertices.size(); i++) {
			this.FACTORY_VERTS.add(vertices.get(i));
		}
	}
	
	public float estWidth() {
		float result = 0;
		if (vertices.size() > 0) {
			float minx = vertices.get(0).x, maxx = 0;
			for (int i = 0; i < vertices.size(); i++) {
				float xv = vertices.get(i).x;
				if (xv < minx) {
					minx = xv;
				} else if (xv > maxx) {
					maxx = xv;
				}
			}
			result = maxx - minx;
		}
		return result;
	}
	
	public float estHeight() {
		float result = 0;
		if (vertices.size() > 0) {
			float miny = vertices.get(0).y, maxy = 0;
			for (int i = 0; i < vertices.size(); i++) {
				float yv = vertices.get(i).y;
				if (yv < miny) {
					miny = yv;
				} else if (yv > maxy) {
					maxy = yv;
				}
			}
			result = maxy - miny;
		}
		return result;
	}
	
	public void addVertex(Vertex v) {
		this.vertices.add(v);
	}
	
	public void addVertex(float x, float y) {
		addVertex(createVertex(x, y));
	}
	
	public Vertex createVertex(float x, float y) {
		return new Vertex(x, y);
	}
	
	public void translate(float nx, float ny) {
		for (int i = 0; i < vertices.size(); i++) {
			vertices.get(i).translate(nx, ny);
		}
		endTransform();
	}
	
	public void angularTranslate(float d, float angle) {
		float nx = ((float)Math.cos(angle) * d);
		float ny = ((float)Math.sin(angle) * d);
		translate(nx, -ny); //ny MUST be opposite due to top left origin
		endTransform();
	}
	
	public void rotate(float angle) {
		for (int i = 0; i < vertices.size(); i++) {
			vertices.get(i).rotate(angle);
		}
		endTransform();
	}
	
	public void rotate(float angle, float x, float y) {
		for (int i = 0; i < vertices.size(); i++) {
			vertices.get(i).rotate(angle, new Vertex(x, y));
		}
		endTransform();
	}
	
	public void rotate(float angle, Polygon p) {
		for (int i = 0; i < vertices.size(); i++) {
			vertices.get(i).rotate(angle, p.centerPoint());
		}
		endTransform();
	}
	
	public void endTransform() {
		calcSides();
	}
	
	public Polygon compositeShape(Polygon p) {
		Polygon result = new Polygon();
		result.vertices = this.vertices;
		for (int i = 0; i < p.vertices.size(); i++) {
			result.vertices.add(p.vertices.get(i));
		}
		return result;
	}
	
	public float getDistance(Vertex p1, Vertex p2) {
		return (float)Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
	}
	
	public Vertex centerPoint() {
		Vertex result = new Vertex(0, 0);
		int xt = 0, yt = 0;
		for (int i = 0; i < vertices.size(); i++) {
			xt += vertices.get(i).x;
			yt += vertices.get(i).y;
		}
		xt /= vertices.size();
		yt /= vertices.size();
		
		result = new Vertex(xt, yt);
		return result;
	}
	
	public boolean intersects(Polygon p) {
		for (int i = 0; i < sides.size(); i++) {
			for (int j = 0; j < p.sides.size(); j++) {
				if (sides.get(i).intersectsLine(p.sides.get(j))) {
					return true;
				}
			}
		}
		return false;		
	}
	
	public boolean intersects(LineSegment ls) {
		boolean result = false;
		for (int i = 0; i < sides.size(); i++) {
			LineSegment Pls = sides.get(i);
			if (ls.intersectsLine(Pls)) {
				return true;
			}
		}
		return result;
	}
	
	public void RESET_SHAPE_TO_DEFAULT() {
	}
	
	public void drawConnectShape(Screen screen, Polygon p) {
		Debug.drawLine(screen, (int)this.centerPoint().x, (int)this.centerPoint().y, (int)p.centerPoint().x, (int)p.centerPoint().y, 0xff00FF00, false);
	}

	public void drawWireframe(Screen screen) {
		for (int i = 0; i < vertices.size(); i++) {
			Vertex v = vertices.get(i);
			
			for (int j = 0; j < vertices.size(); j++) {
				Vertex v2 = vertices.get(j);
				Debug.drawLine(screen, (int)v.x, (int)v.y, (int)v2.x, (int)v2.y, 0xffFF00FF, get(VARS.REND_LOCALLY));
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
			Debug.drawLine(screen, (int)v.x, (int)v.y, (int)v2.x, (int)v2.y, 0xff00FFFF, get(VARS.REND_LOCALLY));
		}
	}
	
}
