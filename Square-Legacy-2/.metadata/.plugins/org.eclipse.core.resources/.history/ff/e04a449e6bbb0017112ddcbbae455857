package com.IB.SL.entity;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.inventory.ChestInventory;
import com.IB.SL.entity.inventory.Quest;
import com.IB.SL.entity.mob.MobFactory;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;

public class Entity implements Serializable {

	public double x;
	public double y;
	protected transient Sprite sprite;
	public transient boolean removed = false;
	public transient Level level;
	public transient ActiveEffects effects;

	transient public final Random random = new Random();
	public double mobhealth;
	public double mana;
	public double stamina;
	public double maxhealth;
	public double maxmana;
	public double maxstamina;
	transient public boolean hurt = false;
	
	public transient Quest toGive;
	private boolean givenQuest = false;

	public transient MobFactory factory = new MobFactory();
	transient public ChestInventory ChestInventory;

	protected transient int xBound = 0;
	protected transient int yBound = 0;
	public int xOffset = -16, yOffset = -24;
	
	public transient boolean riding = false;;
	public transient boolean ySort = true;
	public long Exp;
	public int Lvl = 1;
	public transient double speed;
	public transient boolean invulnerable;
	public Integer id = -1;
	public transient String UID = "";
	transient public Rectangle r;
	public transient String name;
	public int rarity = -1;
	transient public boolean incombat;

	
	public enum HOSTILITY {
		AGR, PASS, NEU, BOSS, PLAYER
	}
	
	public transient HOSTILITY hostility;
	
	public Entity() {
		
	}
	
	public Entity(double x, double y, Sprite sprite, int id) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.id = id;
		this.UID = genUUID();
		r = new Rectangle((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
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
	
	public String getUUID() {
		return UID;
	}
	
	public void setUUID(String uid) {
		this.UID = uid;
	}
	
	public Rectangle getBounds() {
		return r = new Rectangle((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
	}
	
	public void update() {

	}
	public void render (Screen screen) {
		if (sprite != null) screen.renderSprite((int)x, (int)y, Sprite.VoidTile, true);
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
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double value) {
		x = value;
	}
	
	public void setY(double value) {
		y = value;
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
	      return x - xOffset;
	   }
	   
	   public double getoY(){
	      return y - yOffset;
	   }
	   
	   public double getoXB(){
	      return x + xBound - xOffset;
	   }
	   
	   public double getoYB(){
	      return y + yBound - yOffset;
	   }
	
	   
	   public static String genUUID() {
		    UUID uId = UUID.randomUUID();
		    
		    return String.valueOf(uId);
	   }


	   public int convert(double value) {
	      return value < 0 ? -1 : 1;
	   }
	   
	   public void displayQuest(Screen screen) {
		   Player p = Game.get().getPlayer();
		   if (this.toGive != null && givenQuest == false) {
			   p.gui.font8x8.render(110, 15, -2, 0xff000000, "Quest - 'F'", screen, false, true);
			   p.gui.font8x8.render(109, 15, -2, 0xffFFFFFF, "Quest - 'F'", screen, false, false);
			   if (p.input.generalActivator) {
				   if (p.addQuest(toGive)) {
				   this.givenQuest = true;
				   }
			   }
		} else if (givenQuest == true && toGive != null) {
			if (p.quests.get(toGive) != null) {
				
			if (p.quests.get(toGive).getStage() > p.quests.get(toGive).MAX_STAGE) {
			   p.gui.font8x8.render(110, 15, -2, 0xff000000, "Complete - 'F'", screen, false, true);
			   p.gui.font8x8.render(109, 15, -2, 0xffFFFFFF, "Complete - 'F'", screen, false, false);
			   if (p.input.generalActivator) {				   
			   p.quests.completeQuest(toGive.name, false);
			   toGive = null;
			   }
			} else {
				   p.gui.font8x8.render(110, 15, -2, 0xff000000, "Quest Given!", screen, false, true);
				   p.gui.font8x8.render(109, 15, -2, 0xffFFFFFF, "Quest Given!", screen, false, false);
			}
			   }
		   }
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
	
	public com.IB.SL.entity.inventory.ChestInventory getChestInventory() {
		return this.ChestInventory;
	}

	
	public void death() {
		
	}
	
	
	public int getID() {
		if (id == null) {
			id = -1;
			return -1;
		} else {
			return id;
		}
		
		}

	public void renderGUI(Screen screen) {
		
	}
}
