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
	public float length;
	public float angle;
	
	public int color = 0xffFFFFFF;
	
	public LineSegment(Vertex p, float length, float angle) {
		this.origin = p;
		this.length = length;
		this.angle = angle;
		
		float xlen = ((float)Math.cos(angle) * length);
		float ylen = ((float)Math.sin(angle) * length);
		
		this.addVertex(p);
		endPoint = createVertex(xlen + origin.x, ylen + origin.y);
		this.addVertex(endPoint);
		
		this.left_pt = origin;
		this.right_pt = endPoint;
		
		this.top_pt = origin;
		this.bot_pt = endPoint;
		
		if (origin.x > endPoint.x) {
			this.left_pt = endPoint;
			this.right_pt = origin;
		}
		
		if (origin.y > endPoint.y) {
			this.top_pt = endPoint;
			this.bot_pt = origin;
		}
		
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

		this.angle = (float) Math.atan2(this.right_pt.y - this.left_pt.y, this.right_pt.x - this.left_pt.x);
	}
	
	public Vertex midpoint() {
		return new Vertex((this.left_pt.x + this.right_pt.x) / 2, (this.top_pt.y + this.bot_pt.y) / 2);
	}
	
	public void drawLine(Screen screen, boolean fixed) {
		Debug.drawLine(screen, (int)origin.x, (int)origin.y, (int)endPoint.x, (int)endPoint.y, color, true);
	}
	
	public void initShape() {
		this.slope = (-this.right_pt.y - (-this.left_pt.y)) / (this.right_pt.x - this.left_pt.x);
		init();
	}
	
	public LineSegment Perpendicular() {
		return new LineSegment(this.midpoint(), this.length, this.angle - (float)Math.PI/2);
	}
	
	public LineSegment UnitPerpendicular() {
		return new LineSegment(this.midpoint(), 1, this.angle - (float)Math.PI/2);
	}
	
	public boolean CollidesWithLine(LineSegment ls) {
			if (this.slope == ls.slope) {
				if (Math.round(this.top_pt.y) == Math.round(ls.top_pt.y)) {
					if (this.right_pt.x < ls.left_pt.x || this.left_pt.x > ls.right_pt.x) {
						return false;
					}
					return true;
				}
			}
			
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
			
		    if (o == 0 && onSegment(p1, p2, q1)) return true; 
		    if (o2 == 0 && onSegment(p1, q2, q1)) return true; 
		    if (o3 == 0 && onSegment(p2, p1, q2)) return true; 
		    if (o4 == 0 && onSegment(p2, q1, q2)) return true; 
		  
		    return false;
		}
	
	public boolean onSegment(Vertex p, Vertex q, Vertex r) 
	{ 
	    if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && 
	        q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y)) 
	       return true; 
	  
	    return false; 
	} 
	
	public boolean CrossesLine(LineSegment ls) {
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

		// clock or counter-clock wise
		return (val > 0)? 1 : 2; 
		
		
		}
	}
