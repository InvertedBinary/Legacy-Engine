package com.IB.SL.entity.inventory.quests;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.Quest;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Bronze;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.interactables.Shop;
import com.IB.SL.level.worlds.SpawnHaven;

public class q_Fetch extends Quest {
	
	public q_Fetch() {
		basicInitialization();
	}
	
	public void basicInitialization() {
		this.name = "Fetch";
		this.displayname = name;
		this.desc = "Fetch Description";
		this.destination = new TileCoord(-1000, -1000);
		this.MAX_STAGE = 1;
		this.buildCoords();
	}
	
	public void complete() {
		Player p = Game.getGame().getLevel().getClientPlayer();
		p.ExpC += 250;
		p.getInventory().add(new mace_Bronze(EquipableItem.slot_WEAPON));
		p.quests.removeItem(this);
		p.quests.add(new testQuest());
	}
	
	public void completeStage(int stage) {
		
	}
	
	public void update() {
		if (Game.getGame()!= null && Game.getGame().getLevel() != null) {
			Entity e = null;
			this.destination = new TileCoord(-1000, -1000);				
			for (int i = 0; i < Game.getGame().getLevel().entities.size(); i++) {	
				e = Game.getGame().getLevel().entities.get(i);
			}
			if (stage == 0) {
				this.displayname = "Occulos";
				this.desc = "Find And\nDefeat The\nTerrible\nOcculos!";
			}
			if (stage == 1) {
				this.displayname = "Copper Guardian";
				this.desc = "Kill the\n Copper\nGuardian";
			}
			if (stage == 2) {
				this.displayname = "The Null";
				this.desc = "Put an end to\nThe Null";
			}
			if (stage == 3) {
				this.displayname = "Ice Dungeon";
				this.desc = "Clear The\nIce Dungeon\nTo The\nNorth!";
			}
			if (stage > MAX_STAGE) {
				this.complete();
			}
			
			if (Game.getGame().getLevel() instanceof SpawnHaven) {
				if (stage == 0) {
					if (e instanceof Shop) {
						if (((Shop)e).type == 0) {
							this.destination = new TileCoord((int)e.getX() >> 4, (int)e.getY() >> 4);
						}
					}
				}
				
				if (stage == 1) {
					Player p = Game.getGame().getPlayer();
					if (p.getX() == destination.x() << 4 && p.getY() == destination.y() << 4) {
						this.completeStage(1);
					}
/*					if (e instanceof ArcaneBench) {
					}*/
					this.destination = new TileCoord((int)e.getX() >> 4, (int)e.getY() >> 4);
				}
				
			} else {
				
			}
			}
		}
}
