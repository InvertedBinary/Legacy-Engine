package com.IB.SL.level.interactables;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ChestInventory;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.inventory.item.consumables.CoinBag;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.util.Debug;

public class LootBag extends Entity {
	
	transient protected static java.util.Random Rand = new Random();

	transient public static Sprite sprite;
	transient boolean added = false;
	transient boolean addedNormal = false;
	transient private Sprite Chest = Sprite.GoldenCoin;
	transient private GUI gui;

	public LootBag() {
		
	}
	
	public LootBag(int x, int y) {
		this.x = x << Game.TILE_BIT_SHIFT;
		this.y = y << Game.TILE_BIT_SHIFT;
		this.xBound = 11;
		this.yBound = 5;
		this.xOffset = -7;
		this.yOffset = -13;
		this.mobhealth = 1000;
		this.ChestInventory = new ChestInventory(16);
		ChestInventory.setType(ChestInventory.chestType.Normal);
		this.gui = new GUI();
		this.invulnerable = true;
	}
	
	public void basicInitialization() {
		
	}
	
	boolean addItem(Item item) {
		boolean added = false;
		if (ChestInventory.add(item)) {	
			} else {
				added = false;
		}
		return added;
	}
	
	public void testItem() {
		List<Item> items = level.getItemsFixed((int)this.x + 8, (int) this.y + 8, 20);
		for (int i = 0; i < items.size(); i++) {
			if (items.size() > 0) {
				if (items instanceof CoinBag) {
				} else {
					if (ChestInventory.add(items.get(i))) {
						items.get(i).remove();
					}
				}}}
			}
	
	public void update() {
		if (ChestInventory.isEmpty()) {
			Chest = Sprite.Chest_Dungeon_O;
		}
			}
	
	public void OpenChest(Screen screen) {
		List<PlayerMP> players = level.getPlayersFixed((int)this.x + 8, (int) this.y + 8, 20);
		if (level.getPlayersFixedBool((int)this.x + 8, (int)this.y + 8, 20)) {
			if(!ChestInventory.isEmpty()) {
			Chest = Sprite.Chest_Dungeon_O;
			}
			for (int i = 0; i < players.size(); i++) {
				gui.renderName(screen, "Open - F", (int) x - 16, (int) y - 4, -3, true, false, true);
			//	if (players.get(i).input != null) {
				try {
			if (players.get(i).input.generalActivator && !players.get(i).inventoryEnabled) {
				gui.renderInventory(screen, this, players.get(i));//17
						players.get(i).inChest = true;
			} else {
				players.get(i).inChest = false;
			}
			} catch (Exception e) {
				
			}
			}
				
		} else if (!ChestInventory.isEmpty()) {
			Chest = Sprite.Chest_Dungeon_C;
		}
	} 
	
	public void render(Screen screen) {
		if (Game.getGame().gameState == gameState.INGAME_A) {
			Debug.drawRect(screen, (int)x, (int)y, 16, 16, 0xFF00FF, true);
		}
		sprite = Chest;
		screen.renderMobSpriteUniversal((int)x, (int)y, sprite);
	}
	
	public void renderGUI(Screen screen) {
		OpenChest(screen);
	}
	
}
