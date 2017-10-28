package com.IB.SL.entity.inventory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.inventory.item.consumables.AbstractMatter;
import com.IB.SL.entity.inventory.item.consumables.CoinBag;
import com.IB.SL.entity.inventory.item.consumables.RoomKey;
import com.IB.SL.entity.inventory.item.consumables.Ticket;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.font8x8;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.input.Mouse;
import com.IB.SL.util.SaveGame;
import com.IB.SL.util.Sound;

public class Inventory {
	

	public static int pickUpTime = 0;
	private font8x8 font8x8;
	public Item[] items;
	public Item moving = null;
	public int[] amount;
	private int firstFree;
	public transient TAB tab;
	public int x = 82, y = 32; 
	public int nativeX = 82, nativeY = 32;
	private GUI gui;
	private String currentItemName = "";
	private String currentDesc = "";
	
	
	public enum TAB {
		ITEMS, EQUIPMENT, ABILITIES, QUESTS, CLOSED
	}
	
	//public HashMap<Item, Integer> stackable;
	public Inventory(int size) {
		items = new Item[size];
	//	stackable = new HashMap<Item, Integer>();
		firstFree = 0; 
		font8x8 = new font8x8();
		tab = tab.ITEMS;
		gui = new GUI();
	}
	
	
	
	
	public void addByID(Inventory inv, int id) {
		
		
		
		System.out.println("BEING CALLED WITH AN ID OF " + id);
		Game.getGame().getLevel().addInvById(inv, id);
	}
	
	
	public boolean add(Item item) {
		Game.getGame().save(false);
		Game.log("ITEM INDEXED " + getItemSlot(item), "InvClass", true);

		if (false) {
		//if (stackItems(item)) {
		//return true;
		} else {
		if (firstFree == items.length) {
				
			System.out.println("Could Not Add Item: " + item.getName() + " Full Inventory");
			return false;
		} 
		
		
		for (int i = firstFree; i < items.length; i++) { 
			if (items[i] == null) {	

				
				firstFree = i;
				System.out.println("Item Added: " + item.getName() + " At Index: " + i);
				items[firstFree] = item;
				item.remove();
				return true;
				

			}
		}
		firstFree = items.length;
		}
		return true;
	/*	for(int i = 0; i < items.length; i++) {
			if (items[i] == null) {
					items[i] = item;
					System.out.println("Item Added: " + item.getName() + " At Index: " + i);
					return true;
			}
		}
		return false;
		*/
		
	}
	
	
	
	
	public void saveItems(Item[] items) {
		SaveGame.save(items, SaveGame.itemFileName);
	}
	
	public void loadItems(Player p) {
	
		Item[] temp = SaveGame.loadItems();
		for (int i = 0; i < items.length; i++) {
			this.removeByIndex(i);	
		}
		
		if (this.lastItemInList(temp) == -1) {
			return;
		}
		for (int j = 0; j < temp.length; j++) {
			items[j] = temp[j];
		}
		for (int t = 0; t < items.length; t++) {
		//	System.out.println("T: " + t + "/" + items.length + " FF: " + firstFree);
		//	System.out.println("LAST ITEM IN LIST: " + this.lastItemInList());
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
			
			items[t].basicInitialization();
			
			if (items[t] instanceof EquipableItem) {
				items[t].basicInitialization(((EquipableItem)items[t]).slot);
			    items[t].ATC = temp[t].ATC;
			    items[t].DEF = temp[t].DEF;
			    items[t].VIT = temp[t].VIT;
			    items[t].WIS = temp[t].WIS;
			    items[t].EDR = temp[t].EDR;
			    items[t].MAT = temp[t].MAT;
			    items[t].MDF = temp[t].MDF;
			    items[t].AGI = temp[t].AGI;
			}
			
			if (items[t] instanceof Ticket) {
				items[t].basicInitialization(((Ticket)items[t]).type);
			}			
			
			if (items[t] instanceof RoomKey) {
				items[t].basicInitialization(((RoomKey)items[t]).rm);
			}
			
			if (items[t] instanceof CoinBag) {
				items[t].basicInitialization(((CoinBag)items[t]).type);
			}
			
			if (items[t] instanceof AbstractMatter) {
				items[t].basicInitialization(((AbstractMatter)items[t]).tier);
			}
			
			if (t == this.lastItemInList(items)) {
				System.out.println(":::FULLY LOADED ITEMS >> RETURNING:::");
				return;
			}
		}
	}
	
	
	public boolean stackItems(Item item) {
		boolean stacked = false;
		if ((getItemSlot(item) != -1)) {				
			if (items[getItemSlot(item)].stackSize > amount[getItemSlot(item)] - 1) {
			System.out.println(getItemSlot(item));
			amount[getItemSlot(item)] += 1;
			item.remove();
			stacked = true;
			} else {
				stacked = false;
			}
		}
		return stacked;
	}
	
	public boolean isEmpty() {
		boolean result = false;
		
		     if (items[0] == null &&
				 items[1] == null &&
				 items[2] == null &&
				 items[3] == null &&
				 items[4] == null &&
			     items[5] == null &&
				 items[6] == null &&
				 items[7] == null &&
				 items[8] == null &&
				 items[9] == null &&
				items[10] == null &&
			    items[11] == null &&
				items[12] == null &&
				items[13] == null &&
				items[15] == null) {
				
				result = true;
			} else {
				result = false;
			}
		return result;
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
				}if (items[7] != null) {
					result = 7;
				}if (items[8] != null) {
					result = 8;
				}if (items[9] != null) {
					result = 9;
				}if (items[10] != null) {
					result = 10;
				}if (items[11] != null) {
					result = 11;
				}if (items[12] != null) {
					result = 12;
				}if (items[13] != null) {
					result = 13;
				}if (items[14] != null) {
					result = 14;
				}if (items[15] != null) {
					result = 15;
				}
		}
		return result;
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
	
	
	public int itemCount() {
		int result = 0;
		
			if (items[0] != null) result++;
			if (items[1] != null) result++;
				if (items[2] != null) result++;
				if (items[3] != null) result++;
				if (items[4] != null) result++;
			    if (items[5] != null) result++;
				if (items[6] != null) result++;
				if (items[7] != null) result++;
				if (items[8] != null) result++;
				if (items[9] != null) result++;
				if (items[10] != null) result++;
			    if (items[11] != null) result++;
				if (items[12] != null) result++;
				if (items[15] != null) result++;
				
		return result;
	}
	
	
	
	public int getAmount(String name) {
		int result = 0;
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
			if (items[i].name.equalsIgnoreCase(name)) {
				result++;
			}
		}
		}
		
		
		return result;
	}
	
	
	public Item get(int index) {
		return items[index];
	}

	public int getItemSlot(Item item) {
		Integer result = -1;
		for(int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				if(items[i].id.equals( item.id)) {
					result = i;
					//System.out.println("i: " + i);
					return result;
				}}
		}
		return result;
	}
	
	public boolean removeByIndex(int index) {
		boolean complete = false;
		if (items[index] != null) {
			System.out.println("Item Removed At Index " + index + "[" + items[index].getName() + "]");
			complete = true;
		} else {
			System.out.println("The Slot At " + index + " Is Empty");
			complete = false;
		}
		/*if (amount[index] > 0) {
			amount[index] -= 1;
			Mouse.setMouseB(-1);
		} else {*/
		items[index] = null;
		
		if (index < firstFree) {
			firstFree = index;
		//}
			/*for (int i = firstFree; i < items.length; i++) {
			if (items[firstFree + i] != null) {
				items[firstFree] = items[firstFree + i];
				removeByIndex(firstFree + 1);
			}
			}*/
		}
		return complete;
	}
	
	
	public void removeItem(Item item) {
		for(int i = 0; i < items.length; i++) {
			if(items[i] == item) {
				System.out.println("Item Removed " + item.getName() + " At Index " + i);
				if (amount[getItemSlot(item)] >= 0) {
					amount[getItemSlot(item)] -= 1;
					Mouse.setMouseB(-1);
				} else {
				items[i] = null;
				if (i < firstFree) {
					firstFree = i;
				}
				return;
			}
		}
		}
	}

	public void clear() {
		for (int i = 0; i < items.length; i++) {
			removeByIndex(i);
		}
	}
	
	public void dropAll() {
		for (int i = 0; i < items.length; i++) {
			dropItem(i);
		}
	}
	
	private boolean sellItem(Player p, int index) {
		if (items[index] == null) {
			return false;
		}
		if (p.nearShop == null) {
			return false;
		}

		if (p.nearShop.buyItem(p, items[index], index)) {
			 return true;
		 }
		 return false;
	}
	
	
	
	private void dropItem(Item item) {
		int xp = 0, yp = 0;
		List<PlayerMP> players = Game.getGame().getLevel().players;
		for (int i = 0; i < players.size(); i++) {
			xp = (int) players.get(i).getX();
			yp = (int) players.get(i).getY();
		}
		
		if (item instanceof Ticket) {
		Ticket t = (Ticket)item;
		Game.getGame().getLevel().add(new Ticket((xp), (yp + 6), 7400, 1, t.type));
		}
		
		if (item instanceof RoomKey) {
		RoomKey t = (RoomKey)item;
		Game.getGame().getLevel().add(new RoomKey((xp), (yp + 6), 7400, 1, t.rm));
		}

		if (sendItemToLevel(item, xp, yp) != null) {				
			sendItemToLevel(item, xp, yp);
			}
			
		Game.getGame().save(false);
	}
	
	private void dropItem(int index) {
		CoinBag c = null;
		int xp = 0, yp = 0;
			xp = (int) Game.getGame().getLevel().getClientPlayer().getX();
			yp = (int) Game.getGame().getLevel().getClientPlayer().getY();
		Item item = get(index);
		
		
		if (item instanceof Ticket) {
			Ticket t = (Ticket)item;
			Game.getGame().getLevel().add(new Ticket((xp), (yp + 6), 7400, 1, t.type));
			removeByIndex(index);
		}
		
		if (item instanceof RoomKey) {
			RoomKey t = (RoomKey)item;
			Game.getGame().getLevel().add(new RoomKey((xp), (yp + 6), 7400, 1, t.rm));
			removeByIndex(index);
		}
		
		if (item instanceof AbstractMatter) {
			AbstractMatter um = (AbstractMatter)item;
			Game.getGame().getLevel().add(new AbstractMatter((xp), (yp + 6), 7400, 1, um.tier));
			removeByIndex(index);
		}
		
		if (sendItemToLevel(item, xp, yp) != null) {				
		if (removeByIndex(index)) {			
			//TODO: Implement check for null return -- keep item | DONE
			Game.getGame().getLevel().add(sendItemToLevel(item, xp, yp));
			}
			
		}
		
			Game.getGame().save(false);		
			currentItemName = "";
			currentDesc = "";
	}
	
//		public Item sendItemToLevel(Item item, int xp, int yp) {
//			Item result = item.factory.buildItem(xp, yp, item);
//			return result;
//		}
	
	public EquipableItem sendEquipmentToLevel(EquipableItem item, int xp, int yp) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		//TODO: STILL REMOVED WITHOUT SECURE DROP WHEN CLICKED OUTSIDE OF INVENTORY UI
		EquipableItem result = null;
		Random rand = new Random();
		
		//int x, int y, int life, int amount, int slot -------------------------------/
		EquipableItem it = item.getClass().getConstructor(new Class[] {Integer.TYPE}).newInstance(item.slot);
		it.AGI		= item.AGI;
		it.ATC	= item.ATC;
		it.DEF 	= item.DEF;
		it.EDR 	= item.EDR;
		it.MAT 	= item.MAT;
		it.MDF	= item.MDF;
		it.VIT		= item.VIT;
		it.WIS	= item.WIS;
		it.setX(xp + rand.nextInt(24) - 24);
		it.setY(yp + 6 + rand.nextInt(6) - 3);
		it.life 	= 7400;
		//it.basicInitialization();
		it.regenDesc();
		result = it;
	
	return result;
	}
	
	public Item sendItemToLevel(Item item, int xp, int yp) {
		Item result = null;
		Random rand = new Random();
		try {

			if (!(item instanceof EquipableItem)) {
				Item it = item.getClass().newInstance();
				it.setX(xp + rand.nextInt(24) - 24);
				it.setY(yp + 6 + rand.nextInt(6) - 3);
				it.life = 7400;
				result = it;
			} else  {
				result = sendEquipmentToLevel((EquipableItem) item, xp, yp);
			}

		//result = (Item) object;
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Oh noes!");
			return null;
		}
		/*if (item instanceof HealthPot) {
			result = new HealthPot((xp), (yp + 6), 7400, 1);
		}else if (item instanceof ManaPot) {
			result = new ManaPot((xp), (yp + 6), 7400, 1);
		}else if (item instanceof StaminaPot) {
			result = new StaminaPot((xp), (yp + 6), 7400, 1);
		}else if (item instanceof OpticBond) {
			result = new OpticBond((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof RingOfMinorHealth) {
			result = new RingOfMinorHealth((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof RingOfMinorMana) {
			result = new RingOfMinorMana((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof RingOfMinorStamina) {
			result = new RingOfMinorStamina((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof wand_ArcaneTwig) {
			result = new wand_ArcaneTwig((xp), (yp + 6), 7400, 1, EquipableItem.slot_WEAPON);
		}else if (item instanceof wand_FlareScepter) {
			result = new wand_FlareScepter((xp), (yp + 6), 7400, 1, EquipableItem.slot_WEAPON);
		}else if (item instanceof wand_VoidCrook) {
			result = new wand_VoidCrook((xp), (yp + 6), 7400, 1, EquipableItem.slot_WEAPON);
		}else if (item instanceof wand_Pulsefire) {
			result = new wand_Pulsefire((xp), (yp + 6), 7400, 1, EquipableItem.slot_WEAPON);
		}else if (item instanceof wand_StygianScepter) {
			result = new wand_StygianScepter((xp), (yp + 6), 7400, 1, EquipableItem.slot_WEAPON);
		}else if (item instanceof wand_ContradictionWand) {
			result = new wand_ContradictionWand((xp), (yp + 6), 7400, 1, EquipableItem.slot_WEAPON);
		}else if (item instanceof CoinBag) {
				  item.clickEvent();
		}else if (item instanceof CottonRobe) {
			result = new CottonRobe((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof AbyssalArmor) {
			result = new AbyssalArmor((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof DragonsArmor) {
			result = new DragonsArmor((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof SunAcolyteCloak) {
			result = new SunAcolyteCloak((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof LinenRobes) {
			result = new LinenRobes((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof MaelstromRobes) {
			result = new MaelstromRobes((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof CopperArmor) {
			result = new CopperArmor((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof IronArmor) {
			result = new IronArmor((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof InvisPot) {
			result = new InvisPot((xp), (yp + 6), 7400, 1);
		}else if (item instanceof Stick) {
			result = new Stick((xp), (yp + 6), 7400, 1);
		}else if (item instanceof CopperIngot) {
			result = new CopperIngot((xp), (yp + 6), 7400, 1);
		}else if (item instanceof IronIngot) {
			result = new IronIngot((xp), (yp + 6), 7400, 1);
		}else if (item instanceof Leather) {
			result = new Leather((xp), (yp + 6), 7400, 1);
		}else if (item instanceof WyvernSkinArmor) {
			result = new WyvernSkinArmor((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof LeatherTunic) {
			result = new LeatherTunic((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof StuddedLeatherTunic) {
			result = new StuddedLeatherTunic((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof ChaosTunic) {
			result = new ChaosTunic((xp), (yp + 6), 7400, 1, EquipableItem.slot_CHEST);
		}else if (item instanceof Charm_ATC) {
			result = new Charm_ATC((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof Charm_AGI) {
			result = new Charm_AGI((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof Charm_DEF) {
			result = new Charm_DEF((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof Charm_VIT) {
			result = new Charm_VIT((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof Charm_WIS) {
			result = new Charm_WIS((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof Charm_EDR) {
			result = new Charm_EDR((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof Charm_MDF) {
			result = new Charm_MDF((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof Charm_MAT) {
			result = new Charm_MAT((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof BrokenHilt) {
			result = new BrokenHilt((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof CubeOfBefuddlement) {
			result = new CubeOfBefuddlement((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof DiamondShard) {
			result = new DiamondShard((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof ObsidianShard) {
			result = new ObsidianShard((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof Pentagram) {
			result = new Pentagram((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof FrostflamePentagram) {
			result = new FrostflamePentagram((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof EmptyFlask) {
			result = new EmptyFlask((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof SilverCross) {
			result = new SilverCross((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof SmallStone) {
			result = new SmallStone((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof TatteredCloth) {
			result = new TatteredCloth((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof RingOfMajorMana) {
			result = new RingOfMajorMana((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof RingOfMajorStamina) {
			result = new RingOfMajorStamina((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof RingOfMajorHealth) {
			result = new RingOfMajorHealth((xp), (yp + 6), 7400, 1, EquipableItem.slot_UTILITY1);
		}else if (item instanceof AbyssalBindingOoze) {
			result = new AbyssalBindingOoze((xp), (yp + 6), 7400, 1);
		}else if (item instanceof ArcaneEsscence) {
			result = new ArcaneEsscence((xp), (yp + 6), 7400, 1);
		}else if (item instanceof ArcanemIngot) {
			result = new ArcanemIngot((xp), (yp + 6), 7400, 1);
		}else if (item instanceof BindingOoze) {
			result = new BindingOoze((xp), (yp + 6), 7400, 1);
		}else if (item instanceof EarthEsscence) {
			result = new EarthEsscence((xp), (yp + 6), 7400, 1);
		}else if (item instanceof EtherialBindingOoze) {
			result = new EtherialBindingOoze((xp), (yp + 6), 7400, 1);
		}else if (item instanceof FlameEsscence) {
			result = new FlameEsscence((xp), (yp + 6), 7400, 1);
		}else if (item instanceof SteelIngot) {
			result = new SteelIngot((xp), (yp + 6), 7400, 1);
		}else if (item instanceof ThunderEsscence) {
			result = new ThunderEsscence((xp), (yp + 6), 7400, 1);
		}else if (item instanceof VoidEsscence) {
			result = new VoidEsscence((xp), (yp + 6), 7400, 1);
		}else if (item instanceof WaterEsscence) {
			result = new WaterEsscence((xp), (yp + 6), 7400, 1);
		}*/
		return result;
	}
	
	public void renderTabEQUIPMENT(Screen screen, EquipableItem[] equips) {
		if (Game.getGame().getPlayer().inventoryEnabled) {
		EquipableItem slotCheck = null;
		screen.renderSheet(x, y, SpriteSheet.inventoryEquip, false);
		font8x8.render(x + 44, y + 24, -2, 0xFFFFFF, " Level: " + Game.getGame().getPlayer().Lvl + "\n Exp: " + Game.getGame().getLevel().getClientPlayer().ExpC + "\n Kills: " + Game.getGame().getLevel().getClientPlayer().kills + "\n Money: " + Game.getGame().getPlayer().money, screen, false, false);
		//for (int i = 0; i < equipment.length; i++) {
			
			if (equips[EquipableItem.slot_HEAD] != null) {
				//equips[EquipableItem.slot_HEAD].equipEvent();
				screen.renderSprite(x + 15, y + 20, Sprite.abilitybox, false);
				screen.renderSprite(x + 16, y + 21, equips[EquipableItem.slot_HEAD].getSprite(), false);
			}
			if (equips[EquipableItem.slot_CHEST] != null) {
				//equips[EquipableItem.slot_CHEST].equipEvent();
				screen.renderSprite(x + 4, y + 40, Sprite.abilitybox, false);
				screen.renderSprite(x + 5, y + 41, equips[EquipableItem.slot_CHEST].getSprite(), false);
			
			}
			if (equips[EquipableItem.slot_BOOTS] != null) {
				//equips[EquipableItem.slot_BOOTS].equipEvent();
				screen.renderSprite(x + 4, y + 60, Sprite.abilitybox, false);
				screen.renderSprite(x + 5, y + 61, equips[EquipableItem.slot_BOOTS].getSprite(), false);
			
			}
			if (equips[EquipableItem.slot_WEAPON] != null) {
				//equips[EquipableItem.slot_WEAPON].equipEvent();
				screen.renderSprite(x + 26, y + 40, Sprite.abilitybox, false);
				screen.renderSprite(x + 27, y + 41, equips[EquipableItem.slot_WEAPON].getSprite(), false);
			
			}
			if (equips[EquipableItem.slot_SHEILD] != null) {
				//equips[EquipableItem.slot_SHEILD].equipEvent();
				screen.renderSprite(x + 26, y + 60, Sprite.abilitybox, false);
				screen.renderSprite(x + 27, y + 61, equips[EquipableItem.slot_SHEILD].getSprite(), false);
		
			}
			if (equips[EquipableItem.slot_UTILITY1] != null) {
			//	equips[EquipableItem.slot_UTILITY1].equipEvent();
				screen.renderSprite(x + 4, y + 80, Sprite.abilitybox, false);
				screen.renderSprite(x + 5, y + 81, equips[EquipableItem.slot_UTILITY1].getSprite(), false);
			}
			if (equips[EquipableItem.slot_UTILITY2] != null) {
			//equips[EquipableItem.slot_UTILITY2].equipEvent();
				screen.renderSprite(x + 26, y + 80, Sprite.abilitybox, false);
				screen.renderSprite(x + 27, y + 81, equips[EquipableItem.slot_UTILITY2].getSprite(), false);
			}
			
			try {
				       if (gui.checkBounds(x + 16, y + 21, 16, 16, true, true)) {
					slotCheck = equips[EquipableItem.slot_HEAD];
				} else if (gui.checkBounds(x + 5, y + 41, 16, 16, true, true)) {
					slotCheck = equips[EquipableItem.slot_CHEST];
				} else if (gui.checkBounds(x + 5, y + 61, 16, 16, true, true)) {
					slotCheck = equips[EquipableItem.slot_BOOTS];
				} else if (gui.checkBounds(x + 27, y + 41, 16, 16, true, true)) {
					slotCheck = equips[EquipableItem.slot_WEAPON];
				} else if (gui.checkBounds(x + 27, y + 61, 16, 16, true, true)) {
					slotCheck = equips[EquipableItem.slot_SHEILD];
				} else if (gui.checkBounds(x + 5, y + 81, 16, 16, true, true)) {
					slotCheck = equips[EquipableItem.slot_UTILITY1];
				} else if (gui.checkBounds(x + 27, y + 81, 16, 16, true, true)) {
					slotCheck = equips[EquipableItem.slot_UTILITY2];
				} else {
					slotCheck = null;
				}
			} catch (NullPointerException e) {
				/*System.err.println("IGNORE: ");
				System.out.append("Equippable Item In A Slot is Null -- Continue");
				System.out.println();*/
			}
					if (slotCheck != null) {
						font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT, Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -3, 0xFFFFFF, slotCheck.getName().toUpperCase(), screen, false, true);
						if (Mouse.getButton() == 1) {
							Game.getGame().getPlayer().equipment.Dequip(slotCheck.slot);
						}
					}
			}
	}
		//font8x8.render(126, 95, -2, 0xFFFFFF, " Money: " + Game.getGame().getPlayer().money, screen, false, false);
		

//	}
	
	
	
	
	public void renderTabITEMS(Screen screen) {
		screen.renderSheet(x, y, SpriteSheet.inventoryItems, false);
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
			if (items[i].getSprite()  != null) {
				if (items[i] == items[0]) {
					screen.renderSprite(x + 48, y + 20, items[i].getSprite(), false);
				} else if (i < 4) {
				screen.renderSprite(x + 48 + ((i * 22)), y + 20, items[i].getSprite(), false);
				} else if (i < 8) {
				screen.renderSprite(x + 48 + ((i - 4) * 22), y + 41, items[i].getSprite(), false);
				} else if (i < 12) {
				screen.renderSprite(x + 48 + ((i - 8) * 22), y + 62, items[i].getSprite(), false);
				} else if (i < 16) {
					screen.renderSprite(x + 48 + ((i - 12) * 22), y + 83, items[i].getSprite(), false);
				}
			}
			}
		}
		if (moving != null) {
			screen.renderSprite(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT, Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, moving.getSprite(), false);
		}
		
		font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT, Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -3, 0xFFFFFF, currentItemName, screen, false, true);
		font8x8.render(x + 2, y + 23, -3, currentDesc, screen, false, false);

	}
	
	public void renderEffects(Screen screen, Effect[] effects) {
		//font8x8.render(10, 32, -2, 0xff000000, "Effects:", screen, false, true, 1);			
		for (int i = 0; i < effects.length; i++) {
			if (effects[i] != null) {
			screen.renderSprite(12, 39 + (i * 8), effects[i].getSprite(), false);
			font8x8.render(20, 46 + (i * 8), -2, 0xff000000, effects[i].getName() + ":" + (effects[i].life - effects[i].time), screen, false, true, 1);			
			}
		}
	}

	public void renderTabABILITIES(Screen screen) {
			screen.renderSheet(x, y, SpriteSheet.inventoryAbilities, false);
	}
	
	
	
	
	public void renderTabSKILLS(Screen screen) {
		screen.renderSheet(x, y, SpriteSheet.inventorySkills, false);
	}
	
	public void renderTabQUESTS(Screen screen, Quest[] quests) {
		String questDesc = "";
		screen.renderSheet(x, y, SpriteSheet.inventoryQuest, false);

		
		if (quests[0] != null) {
			//font8x8.render(80, 52, -3,quests[0].getName(), screen, false, false);
		}
		
		for (int i = 0; i < quests.length; i++) {
			if (quests[i] != null) {
				if (quests[i].equals(Game.getGame().getPlayer().quests.active)) {
					//screen.drawFillRect(87, 51 + i * 12, 38, 8, 0, false);
					font8x8.render(81, 51 + i * 12, -3, 0xFFFFFF, quests[i].getName(), screen, false, false);
				} else {
					font8x8.render(81, 51 + i * 12, -3, 0, quests[i].getName(), screen, false, false);
				}
			}
		}
		
		
		
		
		if (detectQuestButton(quests) == null) {
		questDesc = "No\nDescription!";
		} else {
			try {
				Quest q = detectQuestButton(quests);
				if (Mouse.getButton() == 1) {
					Game.getGame().getPlayer().quests.active = q;
					Sound.Play(Sound.Click, false);
					Mouse.setMouseB(-1);
				}
				questDesc = q.getDesc();
				font8x8.render(130, 55, -3, questDesc, screen, false, false);
			} catch (NullPointerException e) {
				System.err.println(e.getMessage());
			}
			
		}
		/*font8x8.render(86, 51, -2, 0xFFFFFF, "Test", screen, false, false);
		if (Game.getGame().PersonNameGetter != null) {
		font8x8.render(125, 55, -3, " Thanks for\n helping us\n test the game,\n " + Game.getGame().PersonNameGetter + "\n\n\n       -SL Team", screen, false, false);
		}*/
		
		
	}
	
	private Quest detectQuestButton(Quest[] quests) {
		for (int i = 0; i < quests.length; i++) {

			
			if (Game.getGame().getPlayer().gui.checkBounds(87, 51 + i * 12, 38, 8, true, true)) {
				if (quests[i] != null) {
					return quests[i];
				} else {
					return null;
				}
	}
		}
		return null;
	}
	
	public void setMoving(int slot) {
		Item temp = items[slot];
		items[slot] = moving;
		moving = temp;
		
	}
	
	public void SlotChecking(int slot) {
		try {
			if (moving != null) {
			if (Mouse.getButton() == 3 || Mouse.getButton() == 1) {
				setMoving(slot);
				Mouse.setMouseB(-1);
				}
			}
	
			if (items[slot] != null) {
			//screen.drawFillRect(Mouse.getX() * 4 >> Game.TILE_BIT_SHIFT, Mouse.getY() * 4 >> Game.TILE_BIT_SHIFT, 60, 15, 0x00FF00, false);
			
			this.currentItemName = items[slot].getName().toUpperCase();
			this.currentDesc = items[slot].getDesc().toUpperCase();
			
		if (Mouse.getButton() == 3 && Game.getGame().key.Sprint) {
				if (!sellItem(Game.getGame().getPlayer(), slot)) {
					dropItem(slot);
			}
			Mouse.setMouseB(-1);
		}
		
	if (Mouse.getButton() == 3 && !Game.getGame().key.Sprint) {
		if (moving == null) {
			moving = items[slot];
			removeByIndex(slot);
			Mouse.setMouseB(-1);
		}
	}

	if (Mouse.getButton() == 1) {
		if (Game.getGame().getPlayer().nearShop != null && Game.getGame().getPlayer().input.generalActivator) {
			sellItem(Game.getGame().getPlayer(), slot);
		} else {
		if (moving != null) {
		} else if (items[slot].clickEvent()) {
			removeByIndex(slot);
		} else {
			setMoving(slot);
		}
		}
		Mouse.setMouseB(-1);
	}
	
		} else {
			currentItemName = "";
			currentDesc = "";
		}
} catch (Exception e) {
}
	}
	
	
	public void render(Screen screen) {
		if (tab == null) {
			tab = TAB.ITEMS;
		}
		
		if (overMoveLock || moveLock) {
		Game.getGame().getScreen().drawFillRect(x - 5, y - 5, 5, 5, 0xff000000, false);
		}
		
		if (tab == TAB.ITEMS) {
			renderTabITEMS(screen);
		} else if (tab == TAB.ABILITIES) {
			renderTabABILITIES(screen);
		}
		
		
		
	}
	
	public void SwitchTab(TAB tab, boolean isButton) {
		this.tab = tab;
		if (isButton) {
			Sound.Play(Sound.Click, false);
			Mouse.setMouseB(-1);
		}
		
		abViewExpanded = false;
		if (moving != null) {
			if (add(moving)) {
			moving = null;
		}
		}
	}

	public void move(int x, int y) {
		if (x <= Game.getGame().getScreen().width - 4 && y <= Game.getGame().getScreen().height - 4) {
			if (x >= 4 && y >= 4) {
				
		this.x = x;
		this.y = y;
			} else {
				this.x = x + 1;
				this.y = y + 1; //Can still go out of window
								//TODO: Make items drop into bags
				Mouse.setMouseB(-1);
			}
		} else {
			this.x = x - 1;
			this.y = y - 1;
			Mouse.setMouseB(-1);
		}
	}
	int Sxx = 0;
	int Syy = 0;
	
	
	public boolean moveLock = false;
	public boolean abViewExpanded = false;
	public boolean abDragLock = false;
	public boolean overMoveLock = false;
	private void manageClickEvents() {
		if (Game.getGame().getPlayer().inventoryEnabled || Game.getGame().getPlayer().nearShop != null && Game.getGame().getPlayer().input.generalActivator) {
		//TODO: Double Click To Activate Click Events
			
	if (!moveLock) {
		if (Game.getGame().getPlayer().nearShop == null) {
		if (gui.checkBounds(x - 5, y - 5, 5, 5, true, true)) {
			overMoveLock = true;
			if (Mouse.getButton() == 1) {
				moveLock = true;
			}
		} else {
			overMoveLock = false;
		}
	}
	}
		
		if (moveLock) {
			if (Mouse.getButton() != 1) {
				moveLock = false;
			}
			move((Mouse.getX() + 10) * Game.scale >> Game.TILE_BIT_SHIFT, (Mouse.getY() + 10) * Game.scale >> Game.TILE_BIT_SHIFT);
		}
		
		if (tab != TAB.EQUIPMENT) {
			if (gui.checkBounds(x, y, 34, 16, true, true)) {
				if (Mouse.getButton() == 1) {
					SwitchTab(TAB.EQUIPMENT, true);
				}
			}
		}

		if (tab != TAB.ITEMS) {
			if (gui.checkBounds(x + 34, y, 34, 16, true, true)) {
				if (Mouse.getButton() == 1) {
					SwitchTab(TAB.ITEMS, true);

				}
			}
		}

		if (tab != TAB.ABILITIES) {
			if (gui.checkBounds(x + 68, y, 34, 16, true, true)) {
				if (Mouse.getButton() == 1) {
					SwitchTab(TAB.ABILITIES, true);
				}
			}
		} else {
			if (gui.checkBounds(x + 5, y + 25, 86, 68, true, true)) {
				if (Mouse.getButton() == 1) {
					abViewExpanded = true;
					Mouse.setMouseB(-1);
				}
			}
		}

		if (tab != TAB.QUESTS) {
			if (gui.checkBounds(x + 102, y, 34, 16, true, true)) {
				if (Mouse.getButton() == 1) {
					SwitchTab(TAB.QUESTS, true);
				}
			}
		}
		
		if (tab == TAB.ITEMS) {
			if (!clickEventsRow1() &&
				!clickEventsRow2() &&
				!clickEventsRow3() &&
				!clickEventsRow4()) {
			currentItemName = "";
//			currentDesc = "";
			}

			if (moving != null) {
				if (Mouse.getButton() == 3) {
					if (!Game.getGame().gui.checkBounds(85, 49, 132, 86, true, true)) {
						//TODO: FIX
						//System.out.println("broken --");
						//dropItem(moving );
						//moving = null;
					}
				}
			}

			}
		}
	}
	private boolean clickEventsRow1() {
		boolean result = false;
		if (gui.checkBounds(x + 48, y + 20, 16, 16, true, true)) {
			SlotChecking(0);
			result = true;
		} else if (gui.checkBounds(x + 70, y + 20, 16, 16, true, true)) {
			SlotChecking(1);
			result = true;
		} else if (gui.checkBounds(x + 92, y + 20, 16, 16, true, true)) {
			SlotChecking(2);
			result = true;
		} else if (gui.checkBounds(x + 114, y + 20, 16, 16, true, true)) {
			SlotChecking(3);
			result = true;
		} else {
			result = false;
		}
		
		return result;
	}
	private boolean clickEventsRow2() {
		boolean result = false;
		if (gui.checkBounds(x + 48, y + 41, 16, 16, true, true)) {
			SlotChecking(4);
			result = true;
		} else if (gui.checkBounds(x + 70, y + 41, 16, 16, true, true)) {
			SlotChecking(5);
			result = true;
		} else if (gui.checkBounds(x + 92, y + 41, 16, 16, true, true)) {
			SlotChecking(6);
			result = true;
		} else if (gui.checkBounds(x + 114, y + 41, 16, 16, true, true)) {
			SlotChecking(7);
			result = true;
		} else {
			result = false;
		}
		
		return result;
	}
	
	private boolean clickEventsRow3() {
		boolean result;
		if (gui.checkBounds(x + 48, y + 62, 16, 16, true, true)) {
			SlotChecking(8);
			result = true;
		} else if (gui.checkBounds(x + 70, y + 62, 16, 16, true, true)) {
			SlotChecking(9);
			result = true;
		} else if (gui.checkBounds(x + 92, y + 62, 16, 16, true, true)) {
			SlotChecking(10);
			result = true;
		} else if (gui.checkBounds(x + 114, y + 62, 16, 16, true, true)) {
			SlotChecking(11);
			result = true;
		} else {
			result = false;
		}
		
		return result;
	}
	
	private boolean clickEventsRow4() {
		boolean result = false;
		if (gui.checkBounds(x + 48, y + 83, 16, 16, true, true)) {
			SlotChecking(12);
			result = true;
		} else if (gui.checkBounds(x + 70, y + 83, 16, 16, true, true)) {
			SlotChecking(13);
			result = true;
		} else if (gui.checkBounds(x + 92, y + 83, 16, 16, true, true)) {
			SlotChecking(14);
			result = true;
		} else if (gui.checkBounds(x + 114, y + 83, 16, 16, true, true)) {
			SlotChecking(15);
			result = true;
		} else {
			result = false;
		}
		return result;
	}
	
	
	public void Update() {
		pickUpTime++;
		if (pickUpTime > 21) {
			pickUpTime = 0;
		}
		
		manageClickEvents();
		
		if (moving != null) {
		if (!Game.getGame().getPlayer().inventoryEnabled) {
			if (this.add(moving)) {
				moving = null;
			}
		}
		}
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

	
	
	public int getRecipeAmount(ArrayList<String> recipies, String recipe) {
		int result = 0;
		for(int i = 0; i < recipies.size(); i++) {
			if (recipies.get(i).equalsIgnoreCase(recipe)) {
				result++;
			}
		}
		
		return result;
	}

	public boolean contains(ArrayList<String> recipe) {
		for(int i = 0; i < recipe.size(); i++) {
			if (this.getAmount(recipe.get(i)) >= getRecipeAmount(recipe, recipe.get(i))) {
				if (i == recipe.size() - 1) {
					return true;
				}
			} else {
				return false;
			}
		}
		
		return false;
	}

	public boolean removeItemByName(String name) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
			if (items[i].name.equalsIgnoreCase(name)) {
				if (removeByIndex(i))
					return true;
				break;
				}
			}
		}
		return false;
	}
	
	public void removeByRecipe(ArrayList<String> recipe) {
		for(int t = 0; t < recipe.size(); t++) {
			System.out.println("ITEMS IN RECIPE: " + recipe.get(t) + " / RECIPE SIZE: " + recipe.size());
			removeItemByName(recipe.get(t));
		}
	}


}















