package com.IB.SL.inventory;

import com.IB.SL.entity.Entity;

public class Inventory {

	private Entity owner;
	private int max_items;
	private Item[] items;
	
	public Inventory(Entity e, int max_items) {
		this.owner = e;
		this.max_items = max_items;
		
		items = new Item[max_items];
	}
	
	public void addItem() {
		
	}
	
}
