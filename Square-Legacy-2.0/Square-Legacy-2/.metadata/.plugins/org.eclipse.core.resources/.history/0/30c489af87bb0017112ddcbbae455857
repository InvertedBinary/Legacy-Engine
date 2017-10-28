package com.IB.SL.entity.mob.peaceful;

import java.util.ArrayList;
import java.util.List;

import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ChestInventory;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.inventory.item.consumables.CoinBag;
import com.IB.SL.entity.inventory.item.consumables.Ticket;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Sound;

public class Carraige extends Mob {

	//private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Carraige_left, 32, 32, 3);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Carraige_right, 128, 32, 0);

	transient private AnimatedSprite animSprite = right;
	transient double xa = 0;
	transient double ya = 0;
	transient double time = 0;
	transient public int fireRate = 0;
	public String city;
	transient public static boolean justspawned = false;
	transient Player p = null;
	transient private GUI gui;
	transient private int breatheTime = 519;
	transient TileCoord dest = new TileCoord((int)x, (int)y);
	transient ArrayList<String> cities = new ArrayList<String>();
	
	public void onLoad(Entity e) {
		basicInitialization((int)this.getX() >> 4, (int)this.getY() >> 4, ((Carraige)e).city);
	}
	
	public Carraige(int x, int y) {
		basicInitialization(x, y, "");
	}
	
	public Carraige(int x, int y, String city) {
		basicInitialization(x, y, city);
	}
	
	public void basicInitialization(int x, int y, String city) {
		this.mobhealth = 150;
		this.x = x << 4;
		this.y = y << 4;
		this.name = "Carraige";
		this.city = city;
		this.ChestInventory = new ChestInventory(16);
		ChestInventory.setType(ChestInventory.chestType.Shop);
		this.invulnerable = true;
		this.gui = new GUI();
		this.dir = DIRECTION.RIGHT;
		
		cities.add("Ghelln");
		cities.add("Fenir");
		cities.add("Astellon");
		
		for (int i = 0; i < cities.size(); i++) {
			if (!cities.get(i) .equals(city)) {
			ChestInventory.add(new Ticket(cities.get(i)));
			}
		}
	}

	private void move() {
	      List<Player> players = level.getPlayers(this, 25);
	      if (players.size() > 0) {
	    	  Player p = players.get(0);
	    	  if (p.ridingOn == null) {
	  			testItem();			
	    	  }
	    	  breatheTime++;
	    	  if (breatheTime % 520 == 0) {
	    		  Sound.Play(Sound.horse_Whinny, false);
	    	  }
	      } 
	   }
	
	
	public void testItem() {
		List<PlayerMP> players = level.getPlayersFixed((int)this.x + 12, (int) this.y + 16, 100);
		List<Item> items = level.getItemsFixed((int)this.x + 12, (int) this.y + 16, 80);
		if (items.size() > 0) {
		for (int i = 0; i < items.size(); i++) {
				if (items instanceof CoinBag) {
				} else {
					try {
					Ticket t = (Ticket)items.get(i);
					items.get(i).nearShop = true;
					items.get(i).remove();
					if (ChestInventory.add(items.get(i))) {
						if (t.type.equalsIgnoreCase("Ghelln")) {
							dest = new TileCoord(840, 170);
						} else if (t.type.equalsIgnoreCase("Fenir")) {
							dest = new TileCoord(242, 868);
						} else if (t.type.equalsIgnoreCase("Astellon")) {
							dest = new TileCoord(647, 269);
						}
						
							Sound.Play(Sound.horse_Breath, false);
							players.get(0).x = dest.x();
							players.get(0).y = dest.y();
							Sound.Play(Sound.coin, false);
					
							
						}
					} catch (Exception e) {
						}
				}
				}}}
	
	public void OpenChest(Screen screen) {
		List<PlayerMP> players = level.getPlayersFixed((int)this.x + 32, (int) this.y + 10, 24);
		if (level.getPlayersFixedBool((int)this.x + 32, (int)this.y + 10, 20)) {
			for (int i = 0; i < players.size(); i++) {
				gui.renderName(screen, " (Ticket)\n Open - F", (int) x, (int) y - 24, -3, true, false, true);
			if (players.get(i).input.generalActivator && !players.get(i).inventoryEnabled) {
				gui.renderInventory(screen, this, players.get(i));
				//gui.renderName(screen, " (Tickets)\n  Open - F", (int) x + 16, (int) y - 16, -3, true, false, true);
						players.get(i).inChest = true;
						/*if (players.get(i).name.equalsIgnoreCase("Nate")) {
						ChestInventory.add(new OpticBond(EquipableItem.slot_UTILITY1));
						}*/
						//gui.renderString(screen, "These Are\nVery In Beta", (int) 120, 90, -3, 0xffcc0000, false, false, false);
					//	gui.renderString(screen, "Everything(ie Sprite, etc)\nIs Subject To Change", (int) 120, 130, -3, 0xffcc0000, false, false, true);
			} else {
				players.get(i).inChest = false;
			}
			}
		}
	} 
	
	public void update() {
	/*	if (this.dir == DIRECTION.LEFT) {
			animSprite = left;
		}*/
		if (this.dir == DIRECTION.RIGHT) {
			animSprite = right;					
		}
		
		time++;
		move();
		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);

	}
	public void render(Screen screen) {
		sprite = animSprite.getSprite();


		screen.renderMobSpriteUniversal((int) (x - 14), (int) (y - 15), Sprite.Carraige);
		if (this.dir == DIRECTION.RIGHT) {
		}
	
		OpenChest(screen);
	}
	

}
