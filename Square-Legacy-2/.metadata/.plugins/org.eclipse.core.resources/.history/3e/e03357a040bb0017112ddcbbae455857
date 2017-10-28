package com.IB.SL.entity.inventory;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.Screen;
import com.IB.SL.util.SaveGame;

public class Equipment {

	public EquipableItem[] items;
	private Inventory inv;

	public Equipment(int i, Inventory inv) {
		items = new EquipableItem[i];
		this.inv = inv;
	}
	
	public boolean Equip(EquipableItem item) {
		Game.getGame().save(false);
		int index = item.getSlot();
		
		if (item.slot == 5) {
		if (items[5] != null) {
			index = 6;
			item.slot = item.slot_UTILITY2;
		}}
		
		if (item.slot == 6) {
		if (items[6] != null) {
			index = 5;
			item.slot = item.slot_UTILITY1;
		}
		}
		if (items[index] != null) {
			
			if (!Dequip(index)) {
				return false;
			}
		}
		items[index] = item;
		System.out.println("Equipped Item: " + item + " At Slot: " + index);
		items[index].equipEvent();
		return true;
	}
	
	public void saveItems(EquipableItem[] items) {
		SaveGame.save(items, SaveGame.equipFileName);
	}
	
	public void loadItems(Player p) {
		  
		  EquipableItem[] temp = SaveGame.loadEquips();
		  
		  
		 for (int i = 0; i < items.length; i++) {
			 if (items[i] != null) {
				 items[i].dequipEvent();
				 items[i] = null; 
			 }
		  }

		  if (this.lastItemInList(temp) == -1) { 
		   return;
		  }
		  
		  for (int j = 0; j < temp.length; j++) {
		   items[j] = temp[j]; 
		  }
		  for (int t = 0; t < items.length; t++) { 
		   //System.out.println("T: " + t + "/" + items.length);
		   //System.out.println("LAST ITEM IN LIST: " + this.lastItemInList(items));
		   if (items[t] == null) {
		    if (t != items.length) {
		     for (int i = t; i < items.length; i++) {
		      if (items[i] != null) {
		      t = i;               
		      break;
		      }
		     }
		    }
		   } 
		   
		   
		   if (items[t] instanceof EquipableItem) {
		    items[t].basicInitialization(((EquipableItem)items[t]).slot);
		    System.out.println("TEMP ATC: " + temp[t].ATC);
		    items[t].ATC = temp[t].ATC;
		    items[t].DEF = temp[t].DEF;
		    items[t].VIT = temp[t].VIT;
		    items[t].WIS = temp[t].WIS;
		    items[t].EDR = temp[t].EDR;
		    items[t].MAT = temp[t].MAT;
		    items[t].MDF = temp[t].MDF;
		    items[t].AGI = temp[t].AGI;
		    
		    items[t].equipEvent();
		   }
		   
		   if (t == this.lastItemInList(items)) { 
		    System.out.println(":::FULLY LOADED EQUIPS >> RETURNING:::");
		    return;
		   }
		  }
		 }
	
	public int lastItemInList(Item[] items) {
		int result = -1;
		for (int i = 0; i < items.length; i++) {
			
				if (items[0] != null) {
					result = 0;
				}if (items[1] != null) {
					result = 1;
				}if (items[1] != null) {
					result = 1;
				}if (items[2] != null) {
					result = 2;
				}if (items[3] != null) {
					result = 3;
				}if (items[4] != null) {
					result = 4;
				}if (items[5] != null) {
					result = 5;
				}if (items[6] != null) {
					result = 6;
				}
		}
		return result;
	}
	
	
	public boolean Dequip(int slot) {
		if (!inv.isFull()) {
		if (inv.add(items[slot])) {
			items[slot].dequipEvent();
			items[slot] = null;
			return true;
		}
		}
		return false;
	}
	
	public EquipableItem getItem(int slot) {
		return items[slot];
	}
	
	
	public void render(Screen screen) {
	if (Game.getGame().getPlayer().inventoryEnabled) {
		if (inv.tab == inv.tab.EQUIPMENT) {
			inv.renderTabEQUIPMENT(screen, items);
			}
		}
	}

	public void addByID(Equipment equips, int id) {
			System.out.println("BEING CALLED!!");
			Game.getGame().getLevel().addInvById(inv, id);
	}
	
}
