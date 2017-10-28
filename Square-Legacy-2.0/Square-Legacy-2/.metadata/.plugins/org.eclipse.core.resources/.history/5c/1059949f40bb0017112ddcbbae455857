package com.IB.SL.level.interactables;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ChestInventory;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.inventory.item.consumables.CoinBag;
import com.IB.SL.entity.inventory.item.consumables.HealthPot;
import com.IB.SL.entity.inventory.item.consumables.ManaPot;
import com.IB.SL.entity.inventory.item.consumables.StaminaPot;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_OakLong;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.CopperArmor;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.CottonRobe;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.IronArmor;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.LeatherTunic;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.LinenRobes;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.StuddedLeatherTunic;
import com.IB.SL.entity.inventory.item.equipables.head.GreenfingersCap;
import com.IB.SL.entity.inventory.item.equipables.head.IronHelm;
import com.IB.SL.entity.inventory.item.equipables.head.PyromancerCap;
import com.IB.SL.entity.inventory.item.equipables.head.RangersBandana;
import com.IB.SL.entity.inventory.item.equipables.legs.IronGreaves;
import com.IB.SL.entity.inventory.item.equipables.legs.LeatherBoots;
import com.IB.SL.entity.inventory.item.equipables.legs.LeatherSandals;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Iron;
import com.IB.SL.entity.inventory.item.equipables.rings.BrokenHilt;
import com.IB.SL.entity.inventory.item.equipables.rings.CubeOfBefuddlement;
import com.IB.SL.entity.inventory.item.equipables.rings.Pentagram;
import com.IB.SL.entity.inventory.item.equipables.rings.SilverCross;
import com.IB.SL.entity.inventory.item.equipables.rings.SmallStone;
import com.IB.SL.entity.inventory.item.equipables.secondary.CrystalBall;
import com.IB.SL.entity.inventory.item.equipables.secondary.EnchantedArrow;
import com.IB.SL.entity.inventory.item.equipables.secondary.FireCandle;
import com.IB.SL.entity.inventory.item.equipables.secondary.InspiringBanner;
import com.IB.SL.entity.inventory.item.equipables.secondary.MendersTalisman;
import com.IB.SL.entity.inventory.item.equipables.secondary.Shield;
import com.IB.SL.entity.inventory.item.equipables.secondary.ShortSword;
import com.IB.SL.entity.inventory.item.equipables.secondary.Tack;
import com.IB.SL.entity.inventory.item.equipables.secondary.ThrowingDagger;
import com.IB.SL.entity.inventory.item.equipables.secondary.VoodooCurse;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_ArcaneTwig;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_BlastingRod;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_FlareScepter;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_MendersStaff;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.util.Debug;

public class Chest extends Interactable {
	
	public enum Type {
		EASY, NORMAL, HARD, ADVANCED, RANDOM, STAFF1, EMPTY, CRYSTALBALL, ENCHANTEDARROW, FIRECANDLE, INSPIRINGBANNER,
		MENDERSTALISMAN, SHIELD, SHORTSWORD, THROWINGDAGGER, VOODOODOLL, KNIGHT, MAGE, ARCHER, HEALER, COPPERCHEST,
		COTTONROBE, LEATHERTUNIC, TALISMAN, BROKENSWORD, ROCK, CUBE, PENTAGRAM;
	}
	
	transient public Type type;
	transient protected static java.util.Random Rand = new Random();

	transient public static Sprite sprite;
	transient boolean added = false;
	transient boolean addedNormal = false;
	transient private Sprite Chest = Sprite.Chest_Dungeon_C;
	transient private GUI gui;
	
	public void onLoad(Entity e) {
			this.type = Type.RANDOM;
	}
	public Chest(int x, int y) {
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
		this.type = type.RANDOM;
		if (this.type == this.type.RANDOM) {
			int random = (Rand.nextInt(28));
		if (random == 27) {
			this.type = this.type.STAFF1;
		} else if (random == 26) {
			this.type = this.type.ADVANCED;
		} else if (random == 25) {
			this.type = this.type.HARD;
		} else if (random == 24) {
			this.type = this.type.NORMAL;
		} else if (random == 23) {
			this.type = this.type.EMPTY;
		} else if (random == 22) {
			this.type = this.type.CRYSTALBALL;
		} else if (random == 21) {
			this.type = this.type.ARCHER;
		} else if (random == 20) {
			this.type = this.type.BROKENSWORD;
		} else if (random == 19) {
			this.type = this.type.COPPERCHEST;
		}else if (random == 18) {
			this.type = this.type.COTTONROBE;
		} else if (random == 17) {
			this.type = this.type.CUBE;
		} else if (random == 16) {
			this.type = this.type.ENCHANTEDARROW;
		} else if (random == 15) {
			this.type = this.type.FIRECANDLE;
		}else if (random == 14) {
			this.type = this.type.HEALER;
		} else if (random == 13) {
			this.type = this.type.INSPIRINGBANNER;
		} else if (random == 12) {
			this.type = this.type.KNIGHT;
		} else if (random == 11) {
			this.type = this.type.KNIGHT;
		}else if (random == 10) {
			this.type = this.type.LEATHERTUNIC;
		} else if (random == 9) {
			this.type = this.type.MAGE;
		} else if (random == 8) {
			this.type = this.type.MENDERSTALISMAN;
		} else if (random == 7) {
			this.type = this.type.PENTAGRAM;
		}else if (random == 6) {
			this.type = this.type.ROCK;
		} else if (random == 5) {
			this.type = this.type.SHIELD;
		} else if (random == 4) {
			this.type = this.type.SHORTSWORD;
		} else if (random == 3) {
			this.type = this.type.TALISMAN;
		}else if (random == 2) {
			this.type = this.type.THROWINGDAGGER;
		} else if (random == 1) {
			this.type = this.type.VOODOODOLL;
		} else if (random == 0) {
			this.type = this.type.EMPTY;
		} 
			}
			
		}
	public Chest(int x, int y, Type type) {
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
		this.type = type;
		if (this.type == this.type.RANDOM) {
			int random = (Rand.nextInt(28));
		if (random == 27) {
			this.type = this.type.STAFF1;
		} else if (random == 26) {
			this.type = this.type.ADVANCED;
		} else if (random == 25) {
			this.type = this.type.HARD;
		} else if (random == 24) {
			this.type = this.type.NORMAL;
		} else if (random == 23) {
			this.type = this.type.EMPTY;
		} else if (random == 22) {
			this.type = this.type.CRYSTALBALL;
		} else if (random == 21) {
			this.type = this.type.ARCHER;
		} else if (random == 20) {
			this.type = this.type.BROKENSWORD;
		} else if (random == 19) {
			this.type = this.type.COPPERCHEST;
		}else if (random == 18) {
			this.type = this.type.COTTONROBE;
		} else if (random == 17) {
			this.type = this.type.CUBE;
		} else if (random == 16) {
			this.type = this.type.ENCHANTEDARROW;
		} else if (random == 15) {
			this.type = this.type.FIRECANDLE;
		}else if (random == 14) {
			this.type = this.type.HEALER;
		} else if (random == 13) {
			this.type = this.type.INSPIRINGBANNER;
		} else if (random == 12) {
			this.type = this.type.KNIGHT;
		} else if (random == 11) {
			this.type = this.type.KNIGHT;
		}else if (random == 10) {
			this.type = this.type.LEATHERTUNIC;
		} else if (random == 9) {
			this.type = this.type.MAGE;
		} else if (random == 8) {
			this.type = this.type.MENDERSTALISMAN;
		} else if (random == 7) {
			this.type = this.type.PENTAGRAM;
		}else if (random == 6) {
			this.type = this.type.ROCK;
		} else if (random == 5) {
			this.type = this.type.SHIELD;
		} else if (random == 4) {
			this.type = this.type.SHORTSWORD;
		} else if (random == 3) {
			this.type = this.type.TALISMAN;
		}else if (random == 2) {
			this.type = this.type.THROWINGDAGGER;
		} else if (random == 1) {
			this.type = this.type.VOODOODOLL;
		} else if (random == 0) {
			this.type = this.type.EMPTY;
		} 
		}
		
	}
	
	boolean addItem(Item item) {
		boolean added = false;
		if (ChestInventory.add(item)) {	
			} else {
				added = false;
		}
		return added;
	}
	
	public void generateShield() {
		if (added == false) {
			ChestInventory.add(new Shield(EquipableItem.slot_SHEILD));
			added =! added;
		}
	}
	
	public void generateCrystalBall() {
		if (added == false) {
			ChestInventory.add(new CrystalBall(EquipableItem.slot_SHEILD));
			added =! added;
		}
	}
	
	public void generateMendersTalisman() {
		if (added == false) {
			ChestInventory.add(new MendersTalisman(EquipableItem.slot_SHEILD));
			added =! added;
		}
	}
	
	public void generateTack() {
		if (added == false) {
			ChestInventory.add(new Tack(EquipableItem.slot_SHEILD));
			added =! added;
		}
	}
	
	public void generateEnchantedArrow() {
		if (added == false) {
			ChestInventory.add(new EnchantedArrow(EquipableItem.slot_SHEILD));
			added =! added;
		}
	}
	
	public void generateVoodoo() {
		if (added == false) {
			ChestInventory.add(new VoodooCurse(EquipableItem.slot_SHEILD));
			added =! added;
		}
	}
	
	public void generateDagger() {
		if (added == false) {
			ChestInventory.add(new ThrowingDagger(EquipableItem.slot_SHEILD));
			added =! added;
		}
	}
	
	public void generateSword() {
		if (added == false) {
			ChestInventory.add(new ShortSword(EquipableItem.slot_SHEILD));
			added =! added;
		}
	}
	
	public void generateCandle() {
		if (added == false) {
			ChestInventory.add(new FireCandle(EquipableItem.slot_SHEILD));
			added =! added;
		}
	}
	
	public void generateBanner() {
		if (added == false) {
			ChestInventory.add(new InspiringBanner(EquipableItem.slot_SHEILD));
			added =! added;
		}
	}
	
	public void generateKnight() {
		if (added == false) {
			ChestInventory.add(new mace_Iron(EquipableItem.slot_WEAPON));
			ChestInventory.add(new IronArmor(EquipableItem.slot_CHEST));
			ChestInventory.add(new IronGreaves(EquipableItem.slot_BOOTS));
			ChestInventory.add(new IronHelm(EquipableItem.slot_HEAD));
			added =! added;
		}
	}
	
	public void generateArcher() {
		if (added == false) {
			ChestInventory.add(new bow_OakLong(EquipableItem.slot_WEAPON));
			ChestInventory.add(new StuddedLeatherTunic(EquipableItem.slot_CHEST));
			ChestInventory.add(new LeatherBoots(EquipableItem.slot_BOOTS));
			ChestInventory.add(new RangersBandana(EquipableItem.slot_HEAD));
			added =! added;
		}
	}
	
	public void generateHealer() {
		if (added == false) {
			ChestInventory.add(new wand_MendersStaff(EquipableItem.slot_WEAPON));
			ChestInventory.add(new LinenRobes(EquipableItem.slot_CHEST));
			ChestInventory.add(new LeatherSandals(EquipableItem.slot_BOOTS));
			ChestInventory.add(new GreenfingersCap(EquipableItem.slot_HEAD));
			added =! added;
		}
	}
	
	public void generateMage() {
		if (added == false) {
			ChestInventory.add(new wand_BlastingRod(EquipableItem.slot_WEAPON));
			ChestInventory.add(new LinenRobes(EquipableItem.slot_CHEST));
			ChestInventory.add(new LeatherSandals(EquipableItem.slot_BOOTS));
			ChestInventory.add(new PyromancerCap(EquipableItem.slot_HEAD));
			added =! added;
		}
	}
	
	public void generateCopper() {
		if (added == false) {
			ChestInventory.add(new CopperArmor(EquipableItem.slot_CHEST));
			added =! added;
		}
	}
	
	public void generateCotton() {
		if (added == false) {
			ChestInventory.add(new CottonRobe(EquipableItem.slot_CHEST));
			added =! added;
		}
	}
	
	public void generateLeather() {
		if (added == false) {
			ChestInventory.add(new LeatherTunic(EquipableItem.slot_CHEST));
			added =! added;
		}
	}
	
	public void generateTalisman() {
		if (added == false) {
			ChestInventory.add(new SilverCross(EquipableItem.slot_UTILITY1));
			added =! added;
		}
	}
	
	public void generateBrokenSword() {
		if (added == false) {
			ChestInventory.add(new BrokenHilt(EquipableItem.slot_UTILITY1));
			added =! added;
		}
	}
	
	public void generateRock() {
		if (added == false) {
			ChestInventory.add(new SmallStone(EquipableItem.slot_UTILITY1));
			added =! added;
		}
	}
	
	public void generateCube() {
		if (added == false) {
			ChestInventory.add(new CubeOfBefuddlement(EquipableItem.slot_UTILITY1));
			added =! added;
		}
	}
	
	public void generatePentagram() {
		if (added == false) {
			ChestInventory.add(new Pentagram(EquipableItem.slot_UTILITY1));
			added =! added;
		}
	}
	
	public void generateEasyItems() {
		if (added == false) {
			ChestInventory.add(new ManaPot());
			ChestInventory.add(new StaminaPot());
			added =! added;
		}
	}
	
	public void generateNormalItems() {
		if (added == false) {
		ChestInventory.add(new HealthPot());
		ChestInventory.add(new StaminaPot());
		ChestInventory.add(new CoinBag(CoinBag.Type.RANDOM));

		added =! added;
		}
	}
	
	public void testItem() {
		List<Item> items = level.getItemsFixed((int)this.x + 8, (int) this.y + 8, 20);
		for (int i = 0; i < items.size(); i++) {
			if (items.size() > 0) {
				if (items instanceof CoinBag) {
				} else {
					if (ChestInventory.add(items.get(i))) {
						items.get(i).remove();
						
						
//							System.out.println("TRUE: " + items.get(i));
					}
				}}}
			}
	
	public void genStaff1() {
		if (added == false) {
		ChestInventory.add(new wand_FlareScepter(EquipableItem.slot_WEAPON));
		added =! added;
		}
		}
	
	
	public void decideGeneration() {
		if (type == type.RANDOM) {
			type = type.EASY;
		}
		if (Game.getGame().getLevel() != null) {
			if (type == type.EASY) {
				generateEasyItems();
			} else if (type == type.NORMAL) {
				generateNormalItems();
			} else if (type == type.HARD) {
			generateHardItems();
			} else if (type == type.ADVANCED) {
				generateAdvancedItems();
			} else if (type == type.STAFF1) {
				genStaff1();
			} else if (type == type.ARCHER) {
				generateArcher();
			} else if (type == type.BROKENSWORD) {
				generateBrokenSword();
			} else if (type == type.COPPERCHEST) {
				generateCopper();
			} else if (type == type.COTTONROBE) {
				generateCotton();
			} else if (type == type.CRYSTALBALL) {
				generateCrystalBall();
			} else if (type == type.CUBE) {
				generateCube();
			} else if (type == type.ENCHANTEDARROW) {
				generateEnchantedArrow();
			} else if (type == type.FIRECANDLE) {
				generateCandle();
			} else if (type == type.HEALER) {
				generateHealer();
			} else if (type == type.INSPIRINGBANNER) {
				generateBanner();
			} else if (type == type.KNIGHT) {
				generateKnight();
			} else if (type == type.LEATHERTUNIC) {
				generateLeather();
			} else if (type == type.MAGE) {
				generateMage();
			} else if (type == type.MENDERSTALISMAN) {
				generateMendersTalisman();
			} else if (type == type.PENTAGRAM) {
				generatePentagram();
			} else if (type == type.ROCK) {
				generateRock();
			} else if (type == type.SHIELD) {
				generateShield();
			} else if (type == type.SHORTSWORD) {
				generateSword();
			} else if (type == type.TALISMAN) {
				generateAdvancedItems();
			} else if (type == type.THROWINGDAGGER) {
				generateDagger();
			} else if (type == type.VOODOODOLL) {
				generateVoodoo();
			}
			
			
//			ChestInventory.add(new UncompiledMatter(UncompiledMatter.Tier.RANDOM));

			
			
			
			if (Game.getGame().gameState == Game.getGame().gameState.INGAME_A) {
				testItem();
			}
		}
	}
	
	public void update() {
		decideGeneration();
		if (ChestInventory.isEmpty()) {
			Chest = Sprite.Chest_Dungeon_O;
		}
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
	
	private void generateAdvancedItems() {
		if (added == false) {
		ChestInventory.add(new HealthPot());
		ChestInventory.add(new ManaPot());
		ChestInventory.add(new StaminaPot());
		ChestInventory.add(new ManaPot());
		ChestInventory.add(new StaminaPot());
		ChestInventory.add(new CoinBag(CoinBag.Type.RANDOM));
		ChestInventory.add(new CoinBag(CoinBag.Type.RANDOM));
		ChestInventory.add(new wand_ArcaneTwig(EquipableItem.slot_WEAPON));

			added =! added;
		}
	}

	private void generateHardItems() {
		if (added == false) {
		ChestInventory.add(new HealthPot());
		ChestInventory.add(new ManaPot());
		ChestInventory.add(new CoinBag(CoinBag.Type.RANDOM));
		ChestInventory.add(new StaminaPot());
			added =! added;
		}
	}

	public void OpenChest(Screen screen) {
		List<PlayerMP> players = level.getPlayersFixed((int)this.x + 8, (int) this.y + 8, 20);
		if (level.getPlayersFixedBool((int)this.x + 8, (int)this.y + 8, 20)) {
			if(!ChestInventory.isEmpty()) {
			Chest = Sprite.Chest_Dungeon_O;
			}
			for (int i = 0; i < players.size(); i++) {
				   gui.font8x8.render(110, 15, -2, 0xff000000, "Open - 'F'", screen, false, true);
				   gui.font8x8.render(109, 15, -2, 0xffFFFFFF, "Open - 'F'", screen, false, false);			//	if (players.get(i).input != null) {
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
	
/*	public boolean EntityTele(double xi, double yi, Level level, List<Entity> entities) {
		boolean TransportEntity = false;
		int xp = 0, yp = 0;
		for (int i = 0; i < entities.size(); i++) {
			xp = (int) entities.get(i).getX();
			yp = (int) entities.get(i).getY();
				if (xp < (int) x + sprite.getWidth() + 1
	            && xp > (int) x - 1
	            && yp < (int) y  + sprite.getHeight() + 1
	            && yp > (int)y - 1
	           
	            ) {
					TransportEntity = true;
				}
		}
		return TransportEntity;
	}*/
	
	public void render(Screen screen) {
		if (Game.getGame().gameState == gameState.INGAME_A) {
			Debug.drawRect(screen, (int)x, (int)y, 16, 16, 0xFF00FF, true);
		}
		//int radius = level.radius / 2 + 5;
		//screen.renderLight((int)x - 34 + radius, (int)y - 30 + radius, 50 - radius, 20, 20, 40);
		sprite = Chest;
		screen.renderMobSpriteUniversal((int)x, (int)y, sprite);
		
	}
	
	public void renderGUI(Screen screen) {
		OpenChest(screen);
	}
	
}