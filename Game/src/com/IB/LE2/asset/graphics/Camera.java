package com.IB.LE2.asset.graphics;

public class Camera {
	private static int x = 0;
	private static int y = 0;
	private static int xOffset = 0, yOffset = 0;
	private static int width = 0;
	private static int height = 0;
	
	private static String curFocus = "Init";
	private static int curPriority = -1;
	
	public void move(int x, int y, String focus) {
		if (curFocus.equals(focus)) {
			move(x, y);
		}
	}
	
	public void move(int x, int y) {
		Camera.x = x;
		Camera.y = y;
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
			Camera.curFocus = focus;
			Camera.curPriority = priority;
			System.out.println("New priority accepted! New focus: " + focus);
			return true;
		}
		return false;
	}
	
	public void setOffset(int xOffset, int yOffset) {
		Camera.xOffset = xOffset;
		Camera.yOffset = yOffset;
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
