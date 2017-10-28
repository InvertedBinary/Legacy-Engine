package com.IB.SL.entity.inventory.quests;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.Quest;
import com.IB.SL.entity.inventory.item.equipables.head.TravellersCap;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.worlds.MainLevel;

public class qExplore extends Quest {
	
	public qExplore() {
		basicInitialization();
	}
	
	public void basicInitialization() {
		this.name = "Explore";
		this.displayname = name;
		this.desc = "Explore/nthen/World!";
		this.destination = new TileCoord(-1000, -1000);
		this.MAX_STAGE = 2;
		this.buildCoords();
	}
	
	public void complete() {
		Player p = Game.getGame().getLevel().getClientPlayer();
		p.ExpC += 8;
		p.getInventory().add(new TravellersCap(EquipableItem.slot_HEAD));
		p.quests.removeItem(this);
		p.quests.add(new qMain());
	}
	
	public void update() {
		if (Game.getGame()!= null && Game.getGame().getLevel() != null) {
			Entity e = null;
			this.destination = new TileCoord(-1000, -1000);				
			for (int i = 0; i < Game.getGame().getLevel().entities.size(); i++) {	
				e = Game.getGame().getLevel().entities.get(i);
			}
			if (stage == 0) {
				this.displayname = "Ghelln";
				this.desc = "Journey to\nGhellen";
			}
			
			if (stage == 1) {
				this.displayname = "Stage 1";
				this.desc = "Journey to\nX";
			}
		
			if (stage > MAX_STAGE) {
				Game.getGame().getPlayer().quests.get(this).complete();
			}
			
			if (Game.getGame().getLevel() instanceof MainLevel) {
				if (stage == 0)  {
							this.destination = new TileCoord(838, 158);{
								Player p = Game.getGame().getPlayer();
								if ((int)(p.getX() / 16) == destination.x() >> 4 && (int)(p.getY() / 16) == destination.y() >> 4){
									System.out.println("TRUE");
									p.quests.completeStage(this.name, 0);
								}
							}
					}
					
					}
				}	
			} 
		}

	

