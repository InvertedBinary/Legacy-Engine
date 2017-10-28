package com.IB.SL.level;

import com.IB.SL.util.Vector2i;

public class RayCast {
		   
		   private boolean collided;
		   private Vector2i position;
		   public Vector2i rayVector;
		   
		   
		   public boolean hasCollided() {
		      return collided;
		   }
		   public void setCollided(boolean collided) {
		      this.collided = collided;
		   }
		   public Vector2i getPosition() {
		      return position;
		   }
		   public void setPosition(Vector2i position) {
		      this.position = position;
		   }
}
