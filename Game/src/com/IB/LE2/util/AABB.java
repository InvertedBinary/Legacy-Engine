package com.IB.LE2.util;

import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.util.shape.LineSegment;
import com.IB.LE2.util.shape.Vertex;

public class AABB
{
	public LineSegment top, bottom, left, right;
	double x, y, w, h;

	public AABB(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		initLines();
	}
	
	public AABB(AABB b)
		{
			this.x = b.x;
			this.y = b.y;
			this.w = b.w;
			this.h = b.h;
			initLines();
		}
	
	public void initLines() {
			this.bottom = new LineSegment(new Vertex((float)x, (float)(y + h)), (float)(x + w), (float)(y + h));
	}

	public void drawAABB(Screen screen) {
			screen.DrawRect((int)x, (int)y, (int)w, (int)h, 0xffFF00FF, true);
	}
	
	public void move(double x, double y) {
			this.moveTo(this.x + x, this.y + y);
	}

	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	
}
