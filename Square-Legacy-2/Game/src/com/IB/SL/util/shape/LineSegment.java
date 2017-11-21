package com.IB.SL.util.shape;

public class LineSegment extends Polygon {
	
	Vertex origin;
	Vertex endPoint;
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
	
	public LineSegment(Vertex p1, Vertex p2) {
		this.origin = p1;
		this.endPoint = p2;
		
		this.length = getDistance(p1, p2);
		
		this.addVertex(p1);
		this.addVertex(p2);
		
		initShape();
	}
	
	public void initShape() {
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
