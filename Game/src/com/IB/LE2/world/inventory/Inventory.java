package com.IB.LE2.world.inventory;

import com.IB.LE2.Boot;
import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.input.UI.UI_Manager;
import com.IB.LE2.input.UI.menu.TagMenu;
import com.IB.LE2.world.entity.Entity;

public class Inventory {

	private Entity owner;
	
	private int max_items;
	private Item[] items;
	
	private TagMenu menu;
	
	public Inventory(Entity e, String tagmenu, int max_items) {
		this.owner = e;
		this.max_items = max_items;
		
		menu = new TagMenu(tagmenu);
		items = new Item[max_items];
	}
	
	public void addItem(Item item) {
		Boot.getLevel().remove(item);
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				items[i] = item;
				break;
			}
		}
	}
	
	public void show() {
		UI_Manager.Load(menu);
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null)
			menu.SetElementImage("item0", items[i].display);
		}
	}
	
	public void update() {
	}
	
	public void render(Screen screen) {
	}
	
	
	
}
