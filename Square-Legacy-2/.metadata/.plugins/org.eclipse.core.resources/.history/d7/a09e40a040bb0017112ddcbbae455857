package com.IB.SL.level.interactables;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.inventory.ChestInventory;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.inventory.item.consumables.AbstractMatter;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.CopperArmor;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.IronArmor;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.LeatherTunic;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.StuddedLeatherTunic;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_ArcaneTwig;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.util.Debug;

public class EnchArray extends Interactable {
	
	transient public static Sprite sprite;
	transient boolean added = false;
	transient boolean addedNormal = false;
	transient private AnimatedSprite ArcaneBench = new AnimatedSprite(SpriteSheet.ArcaneBench_1, 32, 32, 10);
	transient private AnimatedSprite animSprite = ArcaneBench;
	transient private GUI gui;
	
	public EnchArray(int x, int y) {
		this.x = x << Game.TILE_BIT_SHIFT;
		this.y = y << Game.TILE_BIT_SHIFT;
		this.xBound = 33;
		this.yBound = 10;
		this.xOffset = -2;
		this.yOffset = -20;
		this.mobhealth = 1000;
		this.ChestInventory = new ChestInventory(16, this);
		ChestInventory.setType(ChestInventory.chestType.Bench);
		this.gui = new GUI();
		this.invulnerable = true;
	}
	
	boolean addItem(Item item) {
		boolean added = false;
		if (ChestInventory.add(item)) {	
			} else {
				added = false;
		}
		return added;
	}
	
	public void resetShop() {
		ChestInventory.removeAll();
		added = false;
	}
	
	public void decideGeneration() {
		if (!added) {
//			ChestInventory.add(new wand_VoidCrook(EquipableItem.slot_WEAPON));
			ChestInventory.add(new LeatherTunic(EquipableItem.slot_CHEST));
			ChestInventory.add(new StuddedLeatherTunic(EquipableItem.slot_CHEST));
			ChestInventory.add(new IronArmor(EquipableItem.slot_CHEST));
			ChestInventory.add(new CopperArmor(EquipableItem.slot_CHEST));
			ChestInventory.add(new wand_ArcaneTwig(EquipableItem.slot_WEAPON));
			ChestInventory.add(new AbstractMatter(AbstractMatter.Tier.RANDOM));

		/*	ChestInventory.add(new wand_VoidCrook(EquipableItem.slot_WEAPON));
			ChestInventory.add(new wand_VoidCrook(EquipableItem.slot_WEAPON));*/

			added = true;
		}
	}
	

		
		
		
		//TODO: Create a crafting recipe in item and override it

	public void update() {
		decideGeneration();

		
		animSprite.update();

			}
	
	
	/*public boolean OpenChest(Screen screen) {
		List<Player> players = level.getPlayers(this, 20);
		boolean Transpot = false;
			if (level.getPlayersBool(this, 20)) {
				if (Game.getGame().getPlayer().input.generalActivator) {
					System.out.println(ChestInventory.listItems(0));
					System.out.println(ChestInventory.listItems(1));
					System.out.println(ChestInventory.listItems(2));
					System.out.println(ChestInventory.listItems(3));
					gui.renderName(screen, "Chest", (int)x, (int)y, -4, true, false, true);
					for(int i = 0; i < players.size(); i++) {
						players.get(i).inChest = true;
*/
	
	public void OpenChest(Screen screen) {
		List<PlayerMP> players = level.getPlayersFixed((int)this.x + 15, (int) this.y + 30, 14);
		if (players.size() > 0) {
			for (int i = 0; i < players.size(); i++) {
				//gui.renderName(screen, "Craft - F", (int) x - 14, (int) y - 4, -3, true, false, true);
			//	if (players.get(i).input != null) {
				try {
			if (players.get(i).input.generalActivator && !players.get(i).inventoryEnabled) {
				screen.drawFillRect(200, 1, 98, 165, 0xff383838, false);
				screen.drawRect(200, 1, 98, 165, 0xff000000, false);
				gui.renderBench(screen, this, players.get(i));//17
						players.get(i).inChest = true;
			} else {
				players.get(i).inChest = false;
				gui.font8x8.render(110, 15, -2, 0xff000000, "Craft  - 'F'", screen, false, true);
				gui.font8x8.render(109, 15, -2, 0xffFFFFFF, "Craft  - 'F'", screen, false, false);
			}
			} catch (Exception e) {
				
			}
			}
		}
	} 
	
	public void render(Screen screen) {
		if (Game.getGame().gameState == gameState.INGAME_A) {
			Debug.drawRect(screen, (int)x, (int)y, 16, 16, 0xFF00FF, true);
		}
		sprite = animSprite.getSprite();
		screen.renderMobSpriteUniversal((int)x, (int)y, sprite);
		
	}
	
	public void renderGUI(Screen screen) {
		OpenChest(screen);
	}
	
}
