package com.IB.SL.entity.inventory;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_ArcaneTwig;
import com.IB.SL.entity.inventory.item.material.Stick;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.bosses.CopperGuardian;
import com.IB.SL.entity.mob.bosses.FrozenKing;
import com.IB.SL.entity.mob.bosses.Occulus;
import com.IB.SL.entity.mob.bosses.SwarmBoss;
import com.IB.SL.entity.mob.bosses.VoidBoss;
import com.IB.SL.graphics.Screen;
import com.IB.SL.util.SaveGame;

public class ActiveQuests {
	public Quest[] quests;
	public Quest active;
	private Inventory inv;
	private int firstFree;

	public ActiveQuests(int size, Inventory inv) {
		quests = new Quest[size];
		firstFree = 0;
		this.inv = inv;
	}
	
	public boolean add(Quest quest) {
		if (Game.getGame() != null)
		Game.getGame().save(false);
		if (firstFree == quests.length) {
			System.out.println("Could Not Add Quest: " + quest.getName() + " You Have Too Many Active Quests!");
			return false;
		} 
		
		
		for (int i = firstFree; i < quests.length; i++) {
			if (quests[i] == null) {	
				firstFree = i;
				System.out.println("New Quest Active: " + quest.getName() + " At Index: " + i);
				quests[firstFree] = quest;
				quest.remove();
				return true;
			}
			
		}
		firstFree = quests.length;
		return true;
	}
	
	public void completeCraftingObjective(Item i) {
		Player p = Game.getGame().getPlayer();
		if (i instanceof wand_ArcaneTwig) {
			p.quests.completeStage("Fetch", 1);
		}
	}
	
	public void completePurchaseObjective(Item i) {
		Player p = Game.getGame().getPlayer();
		if (i instanceof Stick) {
			p.quests.completeStage("Fetch", 0);
		}
	}
	
	public void completeKillObjective(Mob m) {
		Player p = Game.getGame().getPlayer();
		if (m instanceof Occulus) {
			p.quests.completeStage("Main", 0);
		}
		if (m instanceof CopperGuardian) {
			p.quests.completeStage("Main", 1);
		}
		if (m instanceof SwarmBoss) {
			p.quests.completeStage("Main", 2);
		}
		if (m instanceof VoidBoss) {
			p.quests.completeStage("Main", 3);
		}
		if (m instanceof FrozenKing) {
			p.quests.completeStage("Main", 4);
		}
	}
	
	
	public void saveQuests(Quest[] quests) {
		SaveGame.save(quests, SaveGame.questsFileName);
	}

	public void loadQuests(Player p) {
		Quest[] temp = SaveGame.loadQuests();
		for (int i = 0; i < quests.length; i++) {
			this.removeItem(i);
		}
		
		if (this.lastItemInList(temp) == -1) {
			return;
		}
		
		for (int j = 0; j < temp.length; j++) {
			quests[j] = temp[j];

		}
		for (int t = 0; t < quests.length; t++) {
			if (quests[t] == null) {
				if (t != quests.length) {
					for (int i = t; i < quests.length; i++) {
						if (quests[i] != null) {
							t = i;
						break;
						
						}
					}
				}
			} 
			quests[t].basicInitialization();
			quests[t].setStage(temp[t].stage);
			if (t == this.lastItemInList(quests)) {
				return;
			}
		}
	}
	
	public int lastItemInList(Quest[] quests) {
		int result = -1;
		for (int i = 0; i < quests.length; i++) {
				if (quests[0] != null) {
					result = 0;
				}if (quests[1] != null) {
					result = 1;
				}if (quests[1] != null) {
					result = 1;
				}if (quests[2] != null) {
					result = 2;
				}if (quests[3] != null) {
					result = 3;
				}if (quests[4] != null) {
					result = 4;
				}if (quests[5] != null) {
					result = 5;
				}if (quests[6] != null) {
					result = 6;
				}
		}
		return result;
	}
	
	public Quest constructQuest(String q) {
		try {
			return (Quest) Class.forName("com.IB.SL.entity.inventory.quests." + q).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public boolean completeQuest(String q) {
		if (quests.length >= 0) {
			if (quests != null) {
				for (int i = 0; i < quests.length; i++) {
					if (quests[i] != null) {
						if (quests[i].name != null) {
							if (quests[i].name.equalsIgnoreCase(q)) {
								quests[i].complete();
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean completeQuest(String q, boolean override) {
		if (quests.length >= 0) {
			if (quests != null) {
				for (int i = 0; i < quests.length; i++) {
					if (quests[i] != null) {
						if (quests[i].name != null) {
							if (quests[i].name.equalsIgnoreCase(q)) {
								if ((quests[i].getStage() > quests[i].MAX_STAGE) || override) {
								quests[i].complete();
								return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public Quest get(Quest q) {
		if (quests.length >= 0) {
			if (quests != null) {
				for (int i = 0; i < quests.length; i++) {
					if (quests[i] != null) {
						if (quests[i] == q) {
							return quests[i];
						}
					}
				}
			}
		}
		return null;
	}
	
	public boolean completeStage(String q, int stage) {
		if (quests.length >= 0) {
			if (quests != null) {
				for (int i = 0; i < quests.length; i++) {
					if (quests[i] != null) {
						if (quests[i].name != null) {
							if (quests[i].name.equalsIgnoreCase(q)) {
								if (quests[i].stage == stage) {									
								quests[i].setStage(stage + 1);
								quests[i].completeStage(stage);
								return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean setStage(String q, int stage) {
		if (quests.length >= 0) {
			if (quests != null) {
				for (int i = 0; i < quests.length; i++) {
					if (quests[i] != null) {
						if (quests[i].name != null) {
							if (quests[i].name.equalsIgnoreCase(q)) {
								quests[i].setStage(stage);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public boolean removeItem(Quest q) {
		boolean result = false;
		for(int i = 0; i < quests.length; i++) {
			if(quests[i] == q) {
				System.out.println("Quest Removed " + q.getName() + " At Index " + i);
				quests[i] = null;
				if (this.active == q) {
					this.active = null;
				}
				if (i < firstFree) {
					firstFree = i;
				}
				result = true;
				return result;
			}
		}
		return result;
	}
	
	public boolean removeItem(int index) {
		if (this.active == quests[index]) {
			this.active = null;
		}
		quests[index] = null;
		if (index < firstFree) {
			firstFree = index;
		}
		return true;
	}
	
	public void render(Screen screen) {
		if (inv.tab == inv.tab.QUESTS) {
			inv.renderTabQUESTS(screen, quests);
		}
		}
	}
	
