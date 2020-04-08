package com.IB.LE2.util;

public class Vector2i {

	public int x;
	public int y;

	public Vector2i() {
		set(0, 0);
	}

	public Vector2i(Vector2i vector) {
		set(vector.x, vector.y);
	}

	public Vector2i(double x, double y) {
		set(x, y);
	}

	public void set(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Vector2i add(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	public Vector2i subtract(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Vector2i))
			return false;
		Vector2i vec = (Vector2i) object;
		if (vec.getX() == this.getX() && vec.getY() == this.getY())
			return true;
		return false;
	}
	
	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; //any constant
	}

}
