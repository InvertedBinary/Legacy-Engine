package com.IB.SL.graphics;

public class Camera {
	public int x = 0;
	public int y = 0;
	public int xOffset = 0, yOffset = 0;
	public int width = 0;
	public int height = 0;
	
	String curFocus = "Init";
	int curPriority = -1;
	
	public Camera(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void move(int x, int y, String focus) {
		if (curFocus.equals(focus)) {
		move(x, y);
		}
	}
	
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean requestFocus(int x, int y, String focus, int priority) {
		if (requestFocus(focus, priority)) {
			move(x, y, focus);
			return true;
		}
		return false;
	}
	
	public boolean requestFocus(String focus, int priority) {
		if (curPriority < priority) {
			this.curFocus = focus;
			this.curPriority = priority;
			System.out.println("New priority accepted! New focus: " + focus);
			return true;
		}
		return false;
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	
	
	public void loseFocus(String focus, int priority) {
		if (focus.equals(curFocus)) {
			if (curPriority == priority) {
				focus = "None";
				curPriority = -1;
			}
		}
	}
}
