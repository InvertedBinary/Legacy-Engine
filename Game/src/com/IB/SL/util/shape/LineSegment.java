package com.IB.SL.util.shape;

import com.IB.SL.graphics.Screen;
import com.IB.SL.util.Debug;

public class LineSegment extends Polygon {
	
	public Vertex origin;
	public Vertex endPoint;
	
	public Vertex left_pt;
	public Vertex right_pt;
	public Vertex top_pt;
	public Vertex bot_pt;
	
	public float slope;
	
	public int color = 0xffFFFFFF;
	float length;
	
	public LineSegment(Vertex p, float length, float angle) {
		this.origin = p;
		this.length = length;
		
		float xlen = ((float)Math.cos(angle) * length);
		float ylen = ((float)Math.sin(angle) * length);
		
		this.addVertex(p);
		endPoint = createVertex(xlen + origin.x, ylen + origin.y);
		this.addVertex(endPoint);
		
		initShape();
	}
	
	public LineSegment(float x, float y, float x2, float y2) {
		this.origin = new Vertex(x, y);
		this.endPoint = new Vertex(x2, y2);
		
		this.left_pt = origin;
		this.right_pt = endPoint;
		
		this.top_pt = origin;
		this.bot_pt = endPoint;
		
		if (x > x2) {
			this.left_pt = endPoint;
			this.right_pt = origin;
		}
		
		if (y > y2) {
			this.top_pt = endPoint;
			this.bot_pt = origin;
		}
		
		this.length = getDistance(origin, endPoint);
		
		this.addVertex(origin);
		this.addVertex(endPoint);
		
		initShape();
	}
	
	public LineSegment(Vertex p1, Vertex p2) {
		this.origin = p1;
		this.endPoint = p2;
		
		
		this.left_pt = origin;
		this.right_pt = endPoint;
		
		this.top_pt = origin;
		this.bot_pt = endPoint;
		
		if (p1.x > p2.x) {
			this.left_pt = endPoint;
			this.right_pt = origin;
		}
		
		if (p1.y > p2.y) {
			this.top_pt = endPoint;
			this.bot_pt = origin;
		}
		
		
		this.length = getDistance(p1, p2);
		
		this.addVertex(p1);
		this.addVertex(p2);
		
		initShape();
	}
	
	public void drawLine(Screen screen, boolean fixed) {
		Debug.drawLine(screen, (int)origin.x, (int)origin.y, (int)endPoint.x, (int)endPoint.y, color, true);
	}
	
	public void initShape() {
		this.slope = (this.endPoint.y - this.origin.y) / (this.endPoint.x - this.origin.x);
		init();
	}
	
	public boolean intersectsLine(LineSegment ls) {
		Vertex p1 = origin;
		Vertex q1 = endPoint;
		Vertex p2 = ls.origin;
		Vertex q2 = ls.endPoint;
		
		int o  = calcOrientation(p1, q1, p2);
		int o2 = calcOrientation(p1, q1, q2);
		int o3 = calcOrientation(p2, q2, p1);
		int o4 = calcOrientation(p2, q2, q1);
		
		//System.out.println("O: " + o + " , " + o2 + " , " + o3 + " , " + o4);
		
		if ((o != o2) && (o3 != o4)) {
			return true;
		}
		
		return false;
	}
	
	public int calcOrientation(Vertex p1, Vertex p2, Vertex p3) {
		float val = (p2.y - p1.y) * (p3.x - p2.x) - (p2.x - p1.x) * (p3.y - p2.y);

		if (val == 0) return 0;  // collinear

		// clock or counterclock wise
		return (val > 0)? 1: 2; 
		}
	}
