package com.IB.SL.entity;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.util.PropertyEngine;
import com.IB.SL.util.shape.PhysicsBody;
import com.IB.SL.util.shape.Rectangle;

public class Entity extends PropertyEngine implements Serializable {

	public transient Rectangle bounds = new Rectangle(0, 0, 32, 32);
	public transient PhysicsBody body = new PhysicsBody(this, bounds);
	
	public transient Sprite sprite;
	public transient boolean removed = false;
	public transient Level level;
	
	
	public int STAT_VIT;
	public int STAT_END;
	public int STAT_STR;
	public int STAT_AGI;
	public int STAT_RES;
	public int STAT_FAI;
	public int STAT_INT;
	public int STAT_WIS;
	
	public int STAT_ITEM_VIT;
	public int STAT_ITEM_END;
	public int STAT_ITEM_STR;
	public int STAT_ITEM_AGI;
	public int STAT_ITEM_RES;
	public int STAT_ITEM_FAI;
	public int STAT_ITEM_INT;
	public int STAT_ITEM_WIS;

	transient public final Random random = new Random();
	public double mobhealth;
	public double mana;
	public double stamina;
	public double maxhealth;
	public double maxmana;
	public double maxstamina;
	transient public boolean hurt = false;
	transient public boolean walking = false;

	protected transient int xBound = 0;
	protected transient int yBound = 0;
	public int xOffset = -16, yOffset = -24;
	public transient boolean riding = false;;
	public transient boolean ySort = true;
	public long Exp;
	public int Lvl = 1;
	public transient double speed;
	public transient boolean invulnerable;
	transient public java.awt.Rectangle r;
	public transient String name;
	public int rarity = -1;
	transient public boolean incombat;

	public String UUID = "-1";
	
	public enum HOSTILITY {
		AGR, PASS, NEU, BOSS, PLAYER
	}
	
	public transient HOSTILITY hostility;
	
	public Entity() {
		
	}
	
	public Entity(double x, double y, Sprite sprite, String UUID) {
		this.setX(x);
		this.setY(y);
		this.sprite = sprite;
		this.UUID = UUID;
		r = new java.awt.Rectangle((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
	}
	
	public void setUUID(String val) {
			this.UUID = val;
	}
	
	public String getUUID() {
			return this.UUID;
	}
	
	public void onLoad(Entity e) {
		
	}
//	public Rectangle getBounds(Entity e) {
//	    return rectBounds;
//	}
//	
//	public void setBounds(Sprite sprite) {
//		rectBounds = new Rectangle((int) x - (sprite.getWidth() >> 1), (int) y - (sprite.getHeight() >> 1), sprite.getWidth(), sprite.getHeight());
//	}
	
	public java.awt.Rectangle getBounds() {
		return r = new java.awt.Rectangle((int)x(), (int)y(), sprite.getWidth(), sprite.getHeight());
	}
	
	public void update() {

	}
	public void render (Screen screen) {
		if (sprite != null) screen.renderSprite((int)x(), (int)y(), Sprite.VoidTile, true);
	}
	
	public boolean remove() {
		removed = true;
		return removed;
	}
	
	public double health() {
		return mobhealth;
	}
	
	public long Exp() {
		return Exp;
	}
	
	public int Lvl() {
		return Lvl;
	}
	
	public double x() {
		return pos().x();
	}
	
	public double y() {
		return pos().y();
	}
	
	public void x(double val) {
		setX(val);
	}
	
	public void y(double val) {
		setY(val);
	}
	
	public void setX(double val) {
		pos().setX(val);
	}
	
	public void setY(double val) {
		pos().setY(val);
	}
	
	public PVector pos() {
		return body.pos;
	}
	
	public PVector vel() {
		return body.vel;
	}
	
	public double vx() {
		return vel().x();
	}
	
	public double vy() {
		return vel().y();
	}
	
	public void setPos(PVector pos) {
		this.body.pos = pos;
	}
	
	public void setVel(PVector vel) {
		this.body.vel = vel;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	public void init(Level level) {
		this.level = level;
	}

	public int getColor() {
		return 0xffff0000;
	}
	
	public double getoX(){
	      return x() - xOffset;
	   }
	   
	   public double getoY(){
	      return y() - yOffset;
	   }
	   
	   public double getoXB(){
	      return x() + xBound - xOffset;
	   }
	   
	   public double getoYB(){
	      return y() + yBound - yOffset;
	   }
	
	   public int convert(double value) {
	      return value < 0 ? -1 : 1;
	   }
	   
	   public boolean eCol(double xa, double ya){
	      boolean col = false;
	      for(int e2 = 0; e2 < level.entities.size(); e2++){
	         boolean ePlayer = false;
	         if(level.entities.get(e2) instanceof Player) ePlayer = true;
	         if(level.entities.get(e2).xBound != 0 && level.entities.get(e2).yBound != 0){
	            
	            if(ya == -1){
	               if(getoY() <= level.entities.get(e2).getoYB() + 1 && getoY() >= level.entities.get(e2).getoYB()){
	                  if(((getoX() >= level.entities.get(e2).getoX() 
	                     && getoX() <= level.entities.get(e2).getoXB()) 
	                     || (getoXB() >= level.entities.get(e2).getoX() 
	                     && getoXB() <= level.entities.get(e2).getoXB())) 
	                     || (getoX() <= level.entities.get(e2).getoX()
	                     && getoXB() >= level.entities.get(e2).getoXB())){
	                     col = true;
	                  }
	               }
	            }
	            
	            if(ya == 1){
	               if(getoYB() >= level.entities.get(e2).getoY() - 1 && getoYB() <= level.entities.get(e2).getoY()){
	                  if(((getoX() >= level.entities.get(e2).getoX() 
	                     && getoX() <= level.entities.get(e2).getoXB()) 
	                     || (getoXB() >= level.entities.get(e2).getoX() 
	                     && getoXB() <= level.entities.get(e2).getoXB()))
	                     || (getoX() <= level.entities.get(e2).getoX()
	                     && getoXB() >= level.entities.get(e2).getoXB())){
	                     col = true;
	                  }
	               }
	            }
	            
	            if(xa == -1){
	               if(getoX() <= level.entities.get(e2).getoXB() + 1 && getoX() >= level.entities.get(e2).getoXB()){
	                  if(((getoY() >= level.entities.get(e2).getoY() 
	                     && getoY() <= level.entities.get(e2).getoYB()) 
	                     || (getoYB() >= level.entities.get(e2).getoY() 
	                     && getoYB() <= level.entities.get(e2).getoYB())) 
	                     || (getoY() <= level.entities.get(e2).getoY()
	                     && getoYB() >= level.entities.get(e2).getoYB())){
	                     col = true;
	                  }
	               }
	            }
	            
	            if(xa == 1){
	               if(getoXB() >= level.entities.get(e2).getoX() - 1 && getoXB() <= level.entities.get(e2).getoX()){
	                  if(((getoY() >= level.entities.get(e2).getoY() 
	                     && getoY() <= level.entities.get(e2).getoYB()) 
	                     || (getoYB() >= level.entities.get(e2).getoY() 
	                     && getoYB() <= level.entities.get(e2).getoYB()))
	                     || (getoY() <= level.entities.get(e2).getoY()
	                     && getoYB() >= level.entities.get(e2).getoYB())){
	                     col = true;
	                  }
	               }
	            }
	         }
	         if(this instanceof Projectile && ePlayer) col = false;
	      }
	      //if(this instanceof Projectile && col) this.entCol = true;
	      return col;
	   }
	
	/*protected void updateCollisions() {
	      
		for (int i = 0; i < level.getEntities().size(); i++) {
		if (getBounds().intersects(level.getEntities().get(i).getBounds())) {
				level.getEntities().get(i).speed = 0.5;
			}
		}
	}	*/
	
	public void death() {
		
	}
	
	
	public void renderGUI(Screen screen) {
		
	}
}
