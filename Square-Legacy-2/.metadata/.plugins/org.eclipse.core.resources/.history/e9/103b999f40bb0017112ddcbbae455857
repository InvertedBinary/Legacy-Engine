package com.IB.SL.entity.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.font8x8;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.input.Mouse;
import com.IB.SL.level.interactables.ArcaneBench;
import com.IB.SL.level.interactables.EnchArray;
import com.IB.SL.level.interactables.Shop;

public class ChestInventory implements Serializable{
	

	private transient font8x8 font8x8;
	public Item[] items;
	public int firstFree;
	public boolean tabOpen;
	private GUI gui;
	private transient Item crafted = null;
	private ArcaneBench bench = null;
	private Shop shop = null;
	private EnchArray enchArray = null;

	
	public enum Type {
		Shop, Normal, Bench;
	}
	
	public Type chestType;
	
	public ChestInventory(int size) {
	basicInitialization(size);
	}
	
	public ChestInventory(int size, ArcaneBench arcaneBench) {
		this.bench = arcaneBench;
		basicInitialization(size);
	}
	
	public ChestInventory(int size, EnchArray bench) {
		this.enchArray = bench;
		basicInitialization(size);
	}

	
	public ChestInventory(int size, Shop shop) {
		this.shop = shop;
		basicInitialization(size);
	}
	
	
	public void basicInitialization(int size) {
		items = new Item[size];
		firstFree = 0;
		font8x8 = new font8x8();
		tabOpen = false;
		gui = new GUI();
	}
	
	
	public boolean add(Item Item) {
		if (firstFree == items.length) {
			System.out.println("Could Not Add Item: " + Item.getName() + " Your inventory is \'probably\' full");
			return false;
		} 
		
		
		for (int i = firstFree; i < items.length; i++) {
			if (items[i] == null) {	
				firstFree = i;
				System.out.println("Item Added: " + Item.getName() + " At Index: " + i);
				items[firstFree] = Item;
				return true;

			}
			
		}
		firstFree = items.length;
		return true;
	}
	

	
	public Item get(int index) {
		return items[index];
	}
	
	public boolean isEmpty() {
		boolean result = false;
		if (items[0] == null && items[1] == null && items[2] == null
				&& items[3] == null && items[4] == null && items[5] == null
				&& items[6] == null && items[7] == null && items[8] == null
				&& items[9] == null && items[10] == null && items[11] == null
				&& items[12] == null && items[13] == null && items[14] == null
				&& items[15] == null) {

			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public void removeByIndex(int index) {
		if (items[index] != null) {
			System.out.println("Item Removed At Index " + index + "[" + items[index].getName() + "]");
		} else {
			System.out.println("The Slot At " + index + " Is Empty");
		}
		items[index] = null;
		if (index < firstFree) {
			firstFree = index;
		}
	}
	
	public void removeItem(Item Item) {
		for(int i = 0; i < items.length; i++) {
			if(items[i] == Item) {
				System.out.println("Item Removed " + Item.getName() + " At Index " + i);
				items[i] = null;
				if (i < firstFree) {
					firstFree = i;
				}
				return;
			}
		}
	}

	public void removeAll() {
		for(int i = 0; i < items.length; i++) {
			removeByIndex(i);
		}
	}
	
	public void clear() {
		for (int i = 0; i < items.length; i++) {
			removeByIndex(i);
		}
	}
	
	public void renderATab(Screen screen) {
		int xOffset = 0;
		int yOffset = 0;
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				if (items[i] == items[0]) {
					screen.renderSprite(xOffset + 33, yOffset + 4, items[i].getSprite(), false);
				} else if (i <= 9){
					screen.renderSprite(xOffset + 33 + ((i * 23)), yOffset + 4, items[i].getSprite(), false);
				}
			}
		}
	}
	
	public boolean isFull() {
		boolean result = false;
		
			if (items[0] != null &&
				items[1] != null &&
				items[2] != null &&
				items[3] != null &&
				items[4] != null &&
			    items[5] != null &&
				items[6] != null &&
				items[7] != null &&
				items[8] != null &&
				items[9] != null &&
				items[10] != null &&
			    items[11] != null &&
				items[12] != null &&
				items[15] != null) {
				
				result = true;
			} else {
				result = false;
			}
		return result;
	}

	HashMap<String, Integer> recipeCount = new HashMap<String, Integer>();
	String it = "-1ERR";
	public void SlotChecking(int slot, Screen screen, Player player) {
		String priceString = "Value";
		int color = 0xff00FF00;
		if (chestType == chestType.Normal) {
			priceString = "Value:$";
		} else if (chestType == chestType.Shop){
			priceString = "Buy:$";
		}
		if (items[slot] != null) {
			if (Integer.parseInt(items[slot].price) > player.money && chestType == chestType.Shop) {
				color = 0xff7f0000;
			} else if (chestType == chestType.Normal) {
				color = 0xff000000;
			} else {
				color = 0xff00FF00;
			}
			//screen.drawFillRect(Mouse.getX() * 4 >> Game.TILE_BIT_SHIFT, Mouse.getY() * 4 >> Game.TILE_BIT_SHIFT, 60, 15, 0x00FF00, false);
			font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT, Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -3, items[slot].getName().toUpperCase(), screen, false, true);
			
			if (chestType != chestType.Bench) {
			font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT, (Mouse.getY() + 32)* Game.scale >> Game.TILE_BIT_SHIFT, -3, color, priceString + items[slot].price, screen, false, true);
			} else {
				String rec = "";
				for(int i = 0; i < items[slot].recipe.size(); i++) {
					 it = items[slot].recipe.get(i);
					if (recipeCount.containsKey(it)) {
						recipeCount.put(it, recipeCount.get(it) + 1);						
					} else {
						recipeCount.put(it, 1);
					}
					//recipeCount.put("stick",20);
					
				//	font8x8.render(200, 16 + (i * 12), -3, 0xff000000, items[slot].recipe.get(i).toString(), screen, false, false);
					//	rec += items[slot].recipe.get(i).toString() + "\n";
				//	recipeCount.clear();
					
				//	font8x8.render(208, 16 + (i * 12), -3, 0xff000000, it, screen, false, false);					
				}
				int ind = 0;
				for(Map.Entry<String, Integer> entry : recipeCount.entrySet()) {
					ind++;
					String key = entry.getKey();
					Integer val = entry.getValue();
					font8x8.render(200, 8 + (ind * 12), -3, 0xff000000, "("+val+") " + key, screen, false, false);					
				}
				ind = 0;
				
				font8x8.render(200, 5, -3, 0xff000000, "REQUIRES:\n"/*+ rec*/, screen, false, false);
				screen.drawRect(205, 14, 88, 0, 0xff000000, false);
				recipeCount.clear();
			}
		/*if (Mouse.getButton() == 3) {
			removeByIndex(slot);
		}*/
			
			if (chestType == chestType.Normal) {
				if (Mouse.getButton() == 1) {
					if (player.addItem(items[slot])) {
					removeByIndex(slot);
					//Sound.Play(Sound.Click, false);
					Mouse.setMouseB(-1);
					}
				}
			} else if (chestType == chestType.Shop) {
				if (Mouse.getButton() == 1 && player.money >= Integer.parseInt(items[slot].price) && !player.getInventory().isFull()) {
					Mouse.setMouseB(-1);
						if (player.addItem(items[slot])) {
							player.quests.completePurchaseObjective(items[slot]);
							if (shop != null) {
								if (shop.type != 3) {
									player.money -= Integer.parseInt(items[slot].price);
								}
							}
							removeByIndex(slot);
							//Sound.Play(Sound.Click, false);
							if (shop != null) {
								shop.resetShop();
						}

					}
				}
			} else if (chestType == chestType.Bench) {
				if (Mouse.getButton() == 1) {
					Mouse.setMouseB(-1);
					if (this.checkRecipe(items[slot].recipe)) {
						if (this.crafted == null) {
							this.crafted = items[slot];
							player.inventory.removeByRecipe(items[slot].recipe);
							removeByIndex(slot);
							items[slot] = crafted;
							
							if (bench != null) {
								bench.resetShop();
							}
						}
						
					}
					
				}
			}
	}
	
	}
	
	public boolean checkRecipe(ArrayList<String> recipe) {
		Player p = Game.getGame().getPlayer();
		if (p.inventory.contains(recipe)) {
		return true;
		} else {
			return false;
		}
	}
	
	public void setType(com.IB.SL.entity.inventory.ChestInventory.Type type) {
		chestType = type;
	}
	
	public void manageClickEvents(Screen screen, Player player) {
		if (chestType == chestType.Shop) {
			font8x8.render(25, 28, -3, 0xffFFFFFF, "Balance: " + player.money, screen, false, true);
			} else if (chestType == chestType.Bench) {
				font8x8.render(25, 28, -3, 0xffFFFFFF, "Craft An Item", screen, false, true);
			}
		clickEventRow1(screen, player);
		clickEventRow2(screen, player);
		clickEventRow3(screen, player);
		clickEventRow4(screen, player);

	}
	
	
	public void clickEventRow1(Screen screen, Player player) {
		if (gui.checkBounds(31, 43, 16, 16, true, true)) {
		SlotChecking(0, screen, player);
		}
		
		if (gui.checkBounds(52, 43, 16, 16, true, true)) {
			SlotChecking(1, screen, player);
			}
		
		if (gui.checkBounds(73, 43, 16, 16, true, true)) {
			SlotChecking(2, screen, player);
			}
		
		if (gui.checkBounds(94, 43, 16, 16, true, true)) {
			SlotChecking(3, screen, player);
			}
	}
	
	public void clickEventRow2(Screen screen, Player player) {
		if (gui.checkBounds(31, 64, 16, 16, true, true)) {
		SlotChecking(4, screen, player);
		}
		
		if (gui.checkBounds(52, 64, 16, 16, true, true)) {
			SlotChecking(5, screen, player);
			}
		
		if (gui.checkBounds(73, 64, 16, 16, true, true)) {
			SlotChecking(6, screen, player);
			}
		
		if (gui.checkBounds(94, 64, 16, 16, true, true)) {
			SlotChecking(7, screen, player);
			}
	}
	
	public void clickEventRow3(Screen screen, Player player) {
		if (gui.checkBounds(31, 85, 16, 16, true, true)) {
		SlotChecking(8, screen, player);
		}
		
		if (gui.checkBounds(52, 85, 16, 16, true, true)) {
			SlotChecking(9, screen, player);
			}
		
		if (gui.checkBounds(73, 85, 16, 16, true, true)) {
			SlotChecking(10, screen, player);
			}
		
		if (gui.checkBounds(94, 85, 16, 16, true, true)) {
			SlotChecking(11, screen, player);
			}
	}
	
	public void clickEventRow4(Screen screen, Player player) {
		if (gui.checkBounds(31, 106, 16, 16, true, true)) {
		SlotChecking(12, screen, player);
		}
		
		if (gui.checkBounds(52, 106, 16, 16, true, true)) {
			SlotChecking(13, screen, player);
			}
		
		if (gui.checkBounds(73, 106, 16, 16, true, true)) {
			SlotChecking(14, screen, player);
			}
		
		if (gui.checkBounds(94, 106, 16, 16, true, true)) {
			SlotChecking(15, screen, player);
			}
	}
	
	public void render(Screen screen, Player player) {
			screen.renderSheet(25, 37, SpriteSheet.ChestInventory, false);
			renderItems(screen);
			manageClickEvents(screen, player);
		}
	
	public void renderBench(Screen screen, Player player) {
		
		screen.renderSheet(25, 37, SpriteSheet.ChestInventory, false);
		screen.renderSprite(135, 55, Sprite.resize(Sprite.abilitybox, 3), false);
		if (crafted != null) {
			screen.renderSprite(138, 58, Sprite.resize(crafted.getSprite(), 3), false);
			
			if (gui.checkBounds(138, 58, 48, 48, true, true)) {
				if (Mouse.getButton() == 1) {
					Mouse.setMouseB(-1);
					if (Game.getGame().getPlayer().addItem(crafted)) {
						player.quests.completeCraftingObjective(crafted);
						crafted = null;
					}
				}
			}
		}
		renderItems(screen);
		manageClickEvents(screen, player);
		
		
		}
	
	private void renderItems(Screen screen) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
		if (items[i] == items[0]) {
			screen.renderSprite(31, 43, items[i].getSprite(), false);
		} else if (i < 4) {
		screen.renderSprite(31 + ((i * 21)), 43, items[i].getSprite(), false);
		} else if (i < 8) {
		screen.renderSprite(31 + ((i - 4) * 21), 64, items[i].getSprite(), false);
		} else if (i < 12) {
		screen.renderSprite(31 + ((i - 8) * 21), 85, items[i].getSprite(), false);
		} else if (i < 16) {
			screen.renderSprite(31 + ((i - 12) * 21), 106, items[i].getSprite(), false);
					}
			}
		}
	}
	public void checkItem(Screen screen, int slot) {
		System.out.println("Item " + items[slot].getName());
		
	}
	
	public void Update(Player player) {
		
	}
	
	public String listItems(int slot) {
		String item;
		if (items[slot] != null) {
		item = items[slot].getName();
		} else {
			item = "No Item Found In That Slot";
		}
		return item;
	}

}


	

