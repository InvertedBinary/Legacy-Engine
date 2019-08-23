package com.IB.LE2.world.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.AABB;
import com.IB.LE2.util.math.PVector;
import com.IB.LE2.util.shape.LineSegment;
import com.IB.LE2.util.shape.PhysicsBody;
import com.IB.LE2.util.shape.Rectangle;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.entity.projectile.Projectile;
import com.IB.LE2.world.level.Level;
import com.IB.LE2.world.level.TileCoord;

public abstract class Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public transient Rectangle bounds = new Rectangle(0, 0, 32, 32);
	public transient PhysicsBody body = new PhysicsBody(this, bounds);

	public transient Sprite sprite;
	public transient Sprite display;
	public transient Sprite master;
	
	public transient boolean removed = false;
	public transient Level level;
	
	public transient AABB aabb;
	protected transient LineSegment BottomBound;

	transient public final Random random = new Random();
	transient public short hurt = 0;
	transient public boolean walking = false;

	protected transient int xBound = 0;
	protected transient int yBound = 0;
	public transient int xOffset = -16, yOffset = -24, EntWidth = 32, EntHeight = 64;
	public transient int DrawXOffset = -16, DrawYOffset = -24;
	public transient double detection_radius = 0;
	public transient int despawn_index = -1; //-1 => never despawn
	public transient boolean essential = false;
	
	public transient EntityContainer parent_container;
	
	public transient boolean ySort = true;
	transient public boolean incombat;

	public String UUID = "-1";
	
	public transient String name;
	public transient double health;
	public transient double speed;
	public transient double mass;

	protected HashMap<String, String> vars = new HashMap<>();
	
	public Entity() {
		
	}
	
	public Entity(double x, double y, Sprite sprite, String UUID) {
		this.setX(x);
		this.setY(y);
		this.sprite = sprite;
		this.UUID = UUID;
		//r = new Rectangle((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
	}
	
	public Set<String> getVars() {
		return vars.keySet();
	}
	
	public void set(String key, double num) {
		set(key, "" + num);
	}
	
	public void set(String key, boolean val) {
		set(key, "" + val);
	}
	
	public void set(String key, String val) {
		if (key.equals("name"))
			name = key;
		else if (key.equals("health"))
			health = Double.parseDouble(val);
		else if (key.equals("speed"))
			speed = Double.parseDouble(val);
		else if (key.equals("mass"))
			mass = Double.parseDouble(val);
		
		vars.put(key, val);
	}
	
	public String svar(String key) {
		return vars.get(key);
	}
	
	public double nvar(String key) {
		try {
			return Double.parseDouble(svar(key));
		} catch (Exception e) {
			return -1;
		}
	}
	
	public boolean bvar(String key) {
		try {
			return Boolean.parseBoolean(svar(key));
		} catch (Exception e) {
			return false;
		}
	}
	
	public double modvar(String key, double num) {
		set(key, nvar(key) + num);
		return nvar(key);
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
	
	public AABB getBounds() {
		if (this.aabb == null) {
			this.aabb = new AABB(this.x(), this.y(), x() + xOffset, y() + yOffset);
		}
		return this.aabb;
	}
	
	public void added() {
			
	}
	
	public void update() {

	}
	public void render (Screen screen) {
		if (sprite != null) screen.renderSprite((int)x(), (int)y(), Sprite.get("VoidTile"), true);
	}
	
	public boolean remove() {
		removed = true;
		return removed;
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
	
	public int TileX() {
		return (int)x() / TileCoord.TILE_SIZE;
	}
	
	public int TileY() {
		return (int)y() / TileCoord.TILE_SIZE;
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
