package com.IB.SL.entity.inventory.quests;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.Quest;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Bronze;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.level.TileCoord;

public class testQuest extends Quest	{
	
	public testQuest() {
		basicInitialization();
	}
	
	public void basicInitialization() {
		this.name = "Test";
		this.displayname = "Complete";
		this.desc = "Thanks for\nhelping us\ntest the game\n\n\n\n      -SL Team";
		this.MAX_STAGE = 0;
		this.destination = new TileCoord(-1000, -1000);
		this.buildCoords();
	}
	
	public void complete() {
		Player p = Game.getGame().getLevel().getClientPlayer();
		p.ExpC += 250;
		p.getInventory().add(new mace_Bronze(EquipableItem.slot_WEAPON));
		p.quests.removeItem(this);
	}
	
	public void update() {
		if (Game.getGame()!= null && Game.getGame().getLevel() != null) {
			
			if (stage == 0) {
				this.displayname = "Stage One";
			}

			if (stage > MAX_STAGE) {
				this.complete();
			}
			
				this.destination = new TileCoord(-1000, -1000);				
		}
	}
	

}
