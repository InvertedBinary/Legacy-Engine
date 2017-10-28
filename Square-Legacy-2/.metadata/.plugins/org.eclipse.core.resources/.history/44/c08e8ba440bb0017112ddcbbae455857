package com.IB.SL.entity.inventory.quests;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.Quest;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Bronze;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.bosses.CopperGuardian;
import com.IB.SL.entity.mob.bosses.FrozenKing;
import com.IB.SL.entity.mob.bosses.Occulus;
import com.IB.SL.entity.mob.bosses.SwarmBoss;
import com.IB.SL.entity.mob.bosses.VoidBoss;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.worlds.Dungeon01;
import com.IB.SL.level.worlds.Dungeon02;
import com.IB.SL.level.worlds.Dungeon03;
import com.IB.SL.level.worlds.Dungeon04;
import com.IB.SL.level.worlds.MainLevel;
import com.IB.SL.level.worlds.VoidBossRoom;

public class qMain extends Quest {
	
	public qMain() {
		basicInitialization();
	}
	
	public void basicInitialization() {
		this.name = "Main";
		this.displayname = name;
		this.desc = "Find And\nDefeat The\nTerrible\nOcculos!";
		this.destination = new TileCoord(-1000, -1000);
		this.MAX_STAGE = 4;
		this.buildCoords();
	}
	
	public void complete() {
		Player p = Game.getGame().getLevel().getClientPlayer();
		p.ExpC += 250;
		p.getInventory().add(new mace_Bronze(EquipableItem.slot_WEAPON));
		p.quests.removeItem(this);
	//	p.quests.add(new testQuest());
	}
	
	int total = 0;
	public void update() {
		if (Game.getGame()!= null && Game.getGame().getLevel() != null) {
			Entity e = null;
			this.destination = new TileCoord(-1000, -1000);				
			if (stage == 0) {
				this.displayname = "Occulos";
				this.desc = "Find And\nDefeat The\nTerrible\nOcculos!";
			}
			if (stage == 1) {
				this.displayname = "Copper Guardian";
				this.desc = "Kill the\nCopper\nGuardian";
			}
			if (stage == 2) {
				this.displayname = "Swarm";
				this.desc = "Kill the\nSwarm of\nRyzan-Koh";
			}
			if (stage == 3) {
				this.displayname = "The Null";
				this.desc = "Put an end to\nThe Null";
			}
			if (stage == 4) {
				this.displayname = "Frozen King";
				this.desc = "Complete your journey!\nDestroy the\nFrozen King!";
			}

			if (stage > MAX_STAGE) {
				this.complete();
			}
			
			for (int i = 0; i < Game.getGame().getLevel().entities.size(); i++) {	
				e = Game.getGame().getLevel().entities.get(i);
			if (Game.getGame().getLevel() instanceof MainLevel) {
				if (stage == 0 || stage == 1)  {
				this.destination = this.FirstDungeon;
				}
				if (stage == 2) {
					this.destination = this.SandDungeon;
				}
				if (stage == 3) {
				this.destination = this.VoidDungeon;
				}
				if (stage == 4) {
					this.destination = this.IceDungeon;
				}
			} else if (Game.getGame().getLevel() instanceof Dungeon01) {
				if (e != null) {
					if (stage == 0) {
						if (e instanceof Occulus) {
							this.destination = new TileCoord((int) e.getX() >> 4, (int) e.getY() >> 4);
						}
					} else if (stage == 1) {
						if (e instanceof CopperGuardian) {
							this.destination = new TileCoord((int) e.getX() >> 4, (int) e.getY() >> 4);
						}
					}
				}

			} else if (Game.getGame().getLevel() instanceof Dungeon02) {
				if (stage == 3) {
					this.destination = new TileCoord(133, 46);
				}
			} else if (Game.getGame().getLevel() instanceof VoidBossRoom) {
				if (stage == 3) {
					if (e instanceof VoidBoss) {
						this.destination = new TileCoord((int) e.getX() >> 4, (int) e.getY() >> 4);
					}
				}
			} else if (Game.getGame().getLevel() instanceof Dungeon03) {
				if (stage == 4) {
					if (e instanceof FrozenKing) {
						this.destination = new TileCoord((int) e.getX() >> 4, (int) e.getY() >> 4);
					}
					/*if (Game.getGame().getLevel().entities.size() > 0) {
						int tallyMob = 0;
						for (int j = 0; j < Game.getGame().getLevel().entities.size(); j++) {
							if (Game.getGame().getLevel().entities.get(j) instanceof Mob) {
								tallyMob++;
								if (total < tallyMob) 
									total = tallyMob;
								if (j == Game.getGame().getLevel().entities.size() -1 && tallyMob <= total * 0.90) {
									Game.getGame().getPlayer().quests.completeStage("Main", 3);
								} else {
									this.displayname = "Clear the Area: " + (1 + Math.round((tallyMob - total * 0.90)));
								}
							}
						}
					}*/
				} else {
				//	this.displayname = "Stage: " + this.stage;
				}
			} else if (Game.getGame().getLevel() instanceof Dungeon04) {
				if (stage == 2) {
					if (e instanceof SwarmBoss) {
						this.destination = new TileCoord((int) e.getX() >> 4, (int) e.getY() >> 4);
					}
				}
			}
		}
		}
	}

}
