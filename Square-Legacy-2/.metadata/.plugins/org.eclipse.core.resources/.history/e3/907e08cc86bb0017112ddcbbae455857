package com.IB.SL.entity.inventory;

import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.level.TileCoord;

public abstract class Quest extends Item{
	
	
	public transient int MAX_STAGE;
	protected int stage = 0;
	public transient TileCoord destination;
	
	public transient TileCoord FirstDungeon; 
	public transient TileCoord SandDungeon; 
	public transient TileCoord VoidDungeon; 
	public transient TileCoord IceDungeon;

	public abstract void complete();
	
	public void buildCoords() {
		this.FirstDungeon = new TileCoord(730, 363);
		this.VoidDungeon = new TileCoord(401, 735);
		this.IceDungeon = new TileCoord(655, 78);
		this.SandDungeon = new TileCoord(518, 400);

	}
	
	public void completeStage(int stage) {
		
	}
	
	public void setStage(int stage) {
		this.stage = stage;
	}
	
	public int getStage() {
		return stage;
	}
}
