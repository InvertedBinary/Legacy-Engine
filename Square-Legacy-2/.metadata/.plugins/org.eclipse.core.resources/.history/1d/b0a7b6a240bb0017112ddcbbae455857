package com.IB.SL.level.interactables;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ChestInventory;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.inventory.item.consumables.AbstractMatter;
import com.IB.SL.entity.inventory.item.consumables.AbstractMatter.Tier;
import com.IB.SL.entity.inventory.item.consumables.HealthPot;
import com.IB.SL.entity.inventory.item.consumables.InvisPot;
import com.IB.SL.entity.inventory.item.consumables.ManaPot;
import com.IB.SL.entity.inventory.item.consumables.RoomKey;
import com.IB.SL.entity.inventory.item.consumables.StaminaPot;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_BirchShort;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_Cloudburst;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_CobaltLong;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_Crimson;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_FlamingLongbow;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_Frosted;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_OakLong;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_OakShort;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_SilverwoodShort;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_TriString;
import com.IB.SL.entity.inventory.item.equipables.bow.bow_TwoString;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.AbyssalArmor;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.ChaosTunic;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.CopperArmor;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.CottonRobe;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.DragonsArmor;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.IronArmor;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.LeatherTunic;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.LinenRobes;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.MaelstromRobes;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.StuddedLeatherTunic;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.SunAcolyteCloak;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.WyvernSkinArmor;
import com.IB.SL.entity.inventory.item.equipables.head.Bandana;
import com.IB.SL.entity.inventory.item.equipables.head.BerserkersHelm;
import com.IB.SL.entity.inventory.item.equipables.head.BlackBandana;
import com.IB.SL.entity.inventory.item.equipables.head.BronzeHelm;
import com.IB.SL.entity.inventory.item.equipables.head.BronzeSkullcap;
import com.IB.SL.entity.inventory.item.equipables.head.CopperHelm;
import com.IB.SL.entity.inventory.item.equipables.head.DemonicTopHat;
import com.IB.SL.entity.inventory.item.equipables.head.Facewrap;
import com.IB.SL.entity.inventory.item.equipables.head.GreenfingersCap;
import com.IB.SL.entity.inventory.item.equipables.head.IronHelm;
import com.IB.SL.entity.inventory.item.equipables.head.IronSkullcap;
import com.IB.SL.entity.inventory.item.equipables.head.MagusBandana;
import com.IB.SL.entity.inventory.item.equipables.head.MagusCap;
import com.IB.SL.entity.inventory.item.equipables.head.MagusHelm;
import com.IB.SL.entity.inventory.item.equipables.head.MagusTurban;
import com.IB.SL.entity.inventory.item.equipables.head.PyromancerCap;
import com.IB.SL.entity.inventory.item.equipables.head.RampagersHelm;
import com.IB.SL.entity.inventory.item.equipables.head.RangersBandana;
import com.IB.SL.entity.inventory.item.equipables.head.SandblastedTurban;
import com.IB.SL.entity.inventory.item.equipables.head.TopHat;
import com.IB.SL.entity.inventory.item.equipables.head.TravellersCap;
import com.IB.SL.entity.inventory.item.equipables.head.Turban;
import com.IB.SL.entity.inventory.item.equipables.legs.ArcaneFootwraps;
import com.IB.SL.entity.inventory.item.equipables.legs.ArcaneSandals;
import com.IB.SL.entity.inventory.item.equipables.legs.BlackleatherBoots;
import com.IB.SL.entity.inventory.item.equipables.legs.CopperGreaves;
import com.IB.SL.entity.inventory.item.equipables.legs.Footwraps;
import com.IB.SL.entity.inventory.item.equipables.legs.GoldGreaves;
import com.IB.SL.entity.inventory.item.equipables.legs.IronGreaves;
import com.IB.SL.entity.inventory.item.equipables.legs.LeatherBoots;
import com.IB.SL.entity.inventory.item.equipables.legs.LeatherSandals;
import com.IB.SL.entity.inventory.item.equipables.legs.Sandals;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Bronze;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Crimson;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Crude;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Flame;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Frostbitten;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Gazer;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Gold;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Holy;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Iron;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Poison;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Silver;
import com.IB.SL.entity.inventory.item.equipables.rings.AmberBug;
import com.IB.SL.entity.inventory.item.equipables.rings.BrokenHilt;
import com.IB.SL.entity.inventory.item.equipables.rings.CubeOfBefuddlement;
import com.IB.SL.entity.inventory.item.equipables.rings.DiamondShard;
import com.IB.SL.entity.inventory.item.equipables.rings.EmptyFlask;
import com.IB.SL.entity.inventory.item.equipables.rings.FrostflamePentagram;
import com.IB.SL.entity.inventory.item.equipables.rings.ObsidianShard;
import com.IB.SL.entity.inventory.item.equipables.rings.Pentagram;
import com.IB.SL.entity.inventory.item.equipables.rings.RingOfMajorHealth;
import com.IB.SL.entity.inventory.item.equipables.rings.RingOfMajorMana;
import com.IB.SL.entity.inventory.item.equipables.rings.RingOfMajorStamina;
import com.IB.SL.entity.inventory.item.equipables.rings.RingOfMinorHealth;
import com.IB.SL.entity.inventory.item.equipables.rings.RingOfMinorMana;
import com.IB.SL.entity.inventory.item.equipables.rings.RingOfMinorStamina;
import com.IB.SL.entity.inventory.item.equipables.rings.ShardOfTrueIce;
import com.IB.SL.entity.inventory.item.equipables.rings.SilverCross;
import com.IB.SL.entity.inventory.item.equipables.rings.SmallStone;
import com.IB.SL.entity.inventory.item.equipables.rings.TatteredCloth;
import com.IB.SL.entity.inventory.item.equipables.secondary.Buckler;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_BlastingRod;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_ContradictionWand;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_CrystallineScepter;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_FlareScepter;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_MendersStaff;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_OrbweaversRod;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_Possibility;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_Pulsefire;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_RefractorStaff;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_SageRod;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_SilversmithsRod;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_Trappers;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_VoidCrook;
import com.IB.SL.entity.inventory.item.equipables.tome.tome_BookofXithiax;
import com.IB.SL.entity.inventory.item.equipables.tome.tome_CursedBible;
import com.IB.SL.entity.inventory.item.equipables.tome.tome_Glowing;
import com.IB.SL.entity.inventory.item.equipables.tome.tome_HolyTexts;
import com.IB.SL.entity.inventory.item.equipables.tome.tome_Musty;
import com.IB.SL.entity.inventory.item.equipables.tome.tome_Necronomicon;
import com.IB.SL.entity.inventory.item.material.AbyssalBindingOoze;
import com.IB.SL.entity.inventory.item.material.ArcaneEsscence;
import com.IB.SL.entity.inventory.item.material.ArcanemIngot;
import com.IB.SL.entity.inventory.item.material.BindingOoze;
import com.IB.SL.entity.inventory.item.material.CopperIngot;
import com.IB.SL.entity.inventory.item.material.EarthEsscence;
import com.IB.SL.entity.inventory.item.material.EtherialBindingOoze;
import com.IB.SL.entity.inventory.item.material.FlameEsscence;
import com.IB.SL.entity.inventory.item.material.IronIngot;
import com.IB.SL.entity.inventory.item.material.Leather;
import com.IB.SL.entity.inventory.item.material.SteelIngot;
import com.IB.SL.entity.inventory.item.material.Stick;
import com.IB.SL.entity.inventory.item.material.ThreadSpool;
import com.IB.SL.entity.inventory.item.material.ThunderEsscence;
import com.IB.SL.entity.inventory.item.material.VoidEsscence;
import com.IB.SL.entity.inventory.item.material.WaterEsscence;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.input.Mouse;
import com.IB.SL.util.Debug;
import com.IB.SL.util.Sound;

public class Shop extends Interactable {
	
	transient public static Sprite sprite;
	transient protected boolean added = false;
	transient boolean addedNormal = false;
	transient private GUI gui;
	
	transient private AnimatedSprite shop1 = new AnimatedSprite(SpriteSheet.Shop_1, 32, 32, 30);
	transient private AnimatedSprite shop2 = new AnimatedSprite(SpriteSheet.Shop_2, 32, 32, 30);
	transient private AnimatedSprite shop3 = new AnimatedSprite(SpriteSheet.Shop_3, 32, 32, 30);
	transient private AnimatedSprite inn = new AnimatedSprite(SpriteSheet.Shop_inn, 32, 32, 30);

	transient private AnimatedSprite Blacksmith = new AnimatedSprite(SpriteSheet.Blacksmith_Shop, 32, 32, 30);
	transient private AnimatedSprite Blacksmith_still = new AnimatedSprite(SpriteSheet.Blacksmith_Shop_still, 32, 32, 30);
	
	
	transient private AnimatedSprite Artificer = new AnimatedSprite(SpriteSheet.Artificer_Shop, 32, 32, 30);
	
	transient int randGen = 0;
	transient protected static java.util.Random Rand = new Random();

	transient private AnimatedSprite animSprite = shop1;
	
	public int type = 0;
	transient public int faceframe = 17;
	transient public int addTimer = 0;
	transient public int xROffset, yROffset;
	
	transient int Page = 0;
	
	transient public ChestInventory P0 = new ChestInventory(16, this);
	transient public ChestInventory P1 = new ChestInventory(16, this); 
	transient public ChestInventory P2 = new ChestInventory(16, this);
	transient public ChestInventory P3 = new ChestInventory(16, this);
	transient public ChestInventory P4 = new ChestInventory(16, this);
	transient public ChestInventory P5 = new ChestInventory(16, this);
	
	transient public ChestInventory S0 = new ChestInventory(16, this);
	transient public ChestInventory S1 = new ChestInventory(16, this);
	transient public ChestInventory S2 = new ChestInventory(16, this);
	transient public ChestInventory S3 = new ChestInventory(16, this);
	transient public ChestInventory S4 = new ChestInventory(16, this);
	transient public ChestInventory S5 = new ChestInventory(16, this);
	
	transient public ChestInventory C0 = new ChestInventory(16, this);
	transient public ChestInventory C1 = new ChestInventory(16, this);
	transient public ChestInventory C2 = new ChestInventory(16, this);
	transient public ChestInventory C3 = new ChestInventory(16, this);
	transient public ChestInventory C4 = new ChestInventory(16, this);
	transient public ChestInventory C5 = new ChestInventory(16, this);
	//TODO> Change Y-Offset for entity collision
	
	public void onLoad(Entity e) {
			this.type = ((Shop)e).type;
	}
	
	public Shop(int x, int y) {
		basicInitialization(x, y);
	}
	
	public Shop(int x, int y, int type) {
		this.type = type;
		basicInitialization(x, y);
	}
	
	public Shop(int x, int y, int type, ChestInventory CI) {
		this.type = type;
		basicInitialization(x,y);
		if (CI != null) {
			CI.setType(ChestInventory.chestType.Shop);
			this.ChestInventory = CI;
		}
	}
	
	
	private void basicInitialization(int x, int y) {
		this.x = x << Game.TILE_BIT_SHIFT;
		this.y = y << Game.TILE_BIT_SHIFT;
		this.xBound = 33;
		this.yBound = 18;
		this.xOffset = 12;
		this.yOffset = -6;
		this.mobhealth = 1000;
		this.ChestInventory = P0;
		ChestInventory.setType(ChestInventory.chestType.Shop);
		this.gui = new GUI();
		this.invulnerable = true;
		this.name = "Shop" + type;
		this.xROffset = -16;
		this.yROffset = -8;
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
		if (type != 3) { 
		ChestInventory.removeAll();
		this.added = false;
		}
	}
	
	public void decideGeneration() {
		if (Game.getGame().getLevel() != null) {
			if (added == false) {
				
				switch(type) {
				case 0: 
//					ChestInventory.add(new wand_ArcaneTwig(EquipableItem.slot_WEAPON));
					P0.add(new wand_FlareScepter(EquipableItem.slot_WEAPON));
					P0.add(new wand_Pulsefire(EquipableItem.slot_WEAPON));
					P0.add(new wand_VoidCrook(EquipableItem.slot_WEAPON));
					P0.add(new wand_ContradictionWand(EquipableItem.slot_WEAPON));
					P0.add(new wand_CrystallineScepter(EquipableItem.slot_WEAPON));
					P0.add(new wand_BlastingRod(EquipableItem.slot_WEAPON));
					P0.add(new wand_MendersStaff(EquipableItem.slot_WEAPON));
					P0.add(new wand_OrbweaversRod(EquipableItem.slot_WEAPON));
					P0.add(new wand_Possibility(EquipableItem.slot_WEAPON));
					P0.add(new wand_RefractorStaff(EquipableItem.slot_WEAPON));
					P0.add(new wand_SageRod(EquipableItem.slot_WEAPON));
					P0.add(new wand_SilversmithsRod(EquipableItem.slot_WEAPON));
					P0.add(new wand_Trappers(EquipableItem.slot_WEAPON));
					
					P1.add(new bow_OakShort(EquipableItem.slot_WEAPON));
					P1.add(new bow_OakLong(EquipableItem.slot_WEAPON));
					P1.add(new bow_CobaltLong(EquipableItem.slot_WEAPON));
					P1.add(new bow_BirchShort(EquipableItem.slot_WEAPON));
					P1.add(new bow_SilverwoodShort(EquipableItem.slot_WEAPON));
					P1.add(new bow_TwoString(EquipableItem.slot_WEAPON));
					P1.add(new bow_Cloudburst(EquipableItem.slot_WEAPON));
					P1.add(new bow_Crimson(EquipableItem.slot_WEAPON));
					P1.add(new bow_FlamingLongbow(EquipableItem.slot_WEAPON));
					P1.add(new bow_Frosted(EquipableItem.slot_WEAPON));
					P1.add(new bow_TriString(EquipableItem.slot_WEAPON));

					P2.add(new ShardOfTrueIce(EquipableItem.slot_UTILITY1));
					P2.add(new ObsidianShard(EquipableItem.slot_UTILITY1));
					P2.add(new DiamondShard(EquipableItem.slot_UTILITY1));
					P2.add(new SmallStone(EquipableItem.slot_UTILITY1));
					P2.add(new SilverCross(EquipableItem.slot_UTILITY1));
					P2.add(new TatteredCloth(EquipableItem.slot_UTILITY1));
					P2.add(new EmptyFlask(EquipableItem.slot_UTILITY1));
					P2.add(new BrokenHilt(EquipableItem.slot_UTILITY1));
					P2.add(new CubeOfBefuddlement(EquipableItem.slot_UTILITY1));
					P2.add(new Pentagram(EquipableItem.slot_UTILITY1));
					P2.add(new FrostflamePentagram(EquipableItem.slot_UTILITY1));
					P2.add(new AmberBug(EquipableItem.slot_UTILITY1));
					
					P4.add(new HealthPot());
					P4.add(new ManaPot());
					P4.add(new StaminaPot());
					P4.add(new InvisPot()); 
					
					P3.add(new Stick());
					P3.add(new Leather());
					P3.add(new ThreadSpool());
					P3.add(new BindingOoze());
					P3.add(new AbyssalBindingOoze());
					P3.add(new EtherialBindingOoze());
					P3.add(new ArcaneEsscence());
					P3.add(new EarthEsscence());
					P3.add(new FlameEsscence());
					P3.add(new ThunderEsscence());
					P3.add(new VoidEsscence());
					P3.add(new WaterEsscence());
					P3.add(new IronIngot());
					P3.add(new CopperIngot());
					P3.add(new ArcanemIngot());
					P3.add(new SteelIngot());
					
				
                    
					animSprite = shop1;
					faceframe = 17;
					break;
				case 1: 
					S0.add(new CopperArmor(EquipableItem.slot_CHEST));
					S0.add(new IronArmor(EquipableItem.slot_CHEST));
					S0.add(new DragonsArmor(EquipableItem.slot_CHEST));
					S0.add(new AbyssalArmor(EquipableItem.slot_CHEST));
					
					S2.add(new RingOfMinorHealth(EquipableItem.slot_UTILITY1));
					S2.add(new RingOfMinorMana(EquipableItem.slot_UTILITY1));
					S2.add(new RingOfMinorStamina(EquipableItem.slot_UTILITY1));
					
					S2.add(new RingOfMajorHealth(EquipableItem.slot_UTILITY1));
					S2.add(new RingOfMajorStamina(EquipableItem.slot_UTILITY1));
					S2.add(new RingOfMajorMana(EquipableItem.slot_UTILITY1));
					
					S0.add(new BronzeSkullcap(EquipableItem.slot_HEAD));
					S0.add(new IronSkullcap(EquipableItem.slot_HEAD));
					S0.add(new IronHelm(EquipableItem.slot_HEAD));
					S0.add(new BronzeHelm(EquipableItem.slot_HEAD));
					S0.add(new Buckler(EquipableItem.slot_SHEILD));
                    S0.add(new MagusHelm(EquipableItem.slot_HEAD));
					
                    S0.add(new CopperGreaves(EquipableItem.slot_BOOTS));
                    S0.add(new GoldGreaves(EquipableItem.slot_BOOTS));
                    S0.add(new IronGreaves(EquipableItem.slot_BOOTS));
                    
                    S0.add(new RampagersHelm(EquipableItem.slot_HEAD));
                    S0.add(new CopperHelm(EquipableItem.slot_HEAD));
                    S0.add(new BerserkersHelm(EquipableItem.slot_HEAD));
                    S1.add(new mace_Bronze(EquipableItem.slot_WEAPON));
                    S1.add(new mace_Crimson(EquipableItem.slot_WEAPON));
                    S1.add(new mace_Crude(EquipableItem.slot_WEAPON));
                    S1.add(new mace_Flame(EquipableItem.slot_WEAPON));
                    S1.add(new mace_Frostbitten(EquipableItem.slot_WEAPON));
                    S1.add(new mace_Gazer(EquipableItem.slot_WEAPON));
                    S1.add(new mace_Gold(EquipableItem.slot_WEAPON));
                    S1.add(new mace_Holy(EquipableItem.slot_WEAPON));
                    S1.add(new mace_Iron(EquipableItem.slot_WEAPON));
                    S1.add(new mace_Poison(EquipableItem.slot_WEAPON));
                    S1.add(new mace_Silver(EquipableItem.slot_WEAPON));
                    
					animSprite = Blacksmith;
				//	faceframe = 27;
					break;
				case 2: 
					C0.add(new CottonRobe(EquipableItem.slot_CHEST));
					C0.add(new LinenRobes(EquipableItem.slot_CHEST));
					C0.add(new SunAcolyteCloak(EquipableItem.slot_CHEST));
					C0.add(new MaelstromRobes(EquipableItem.slot_CHEST));
					
					C0.add(new LeatherTunic(EquipableItem.slot_CHEST));
					C0.add(new StuddedLeatherTunic(EquipableItem.slot_CHEST));
					C0.add(new WyvernSkinArmor(EquipableItem.slot_CHEST));
					C0.add(new ChaosTunic(EquipableItem.slot_CHEST));

					C1.add(new Bandana(EquipableItem.slot_HEAD));
					C1.add(new Turban(EquipableItem.slot_HEAD));
					C1.add(new TravellersCap(EquipableItem.slot_HEAD));
					
					C2.add(new tome_Musty(EquipableItem.slot_WEAPON));
					C2.add(new tome_Glowing(EquipableItem.slot_WEAPON));
					C2.add(new tome_HolyTexts(EquipableItem.slot_WEAPON));
					C2.add(new tome_CursedBible(EquipableItem.slot_WEAPON));
					C2.add(new tome_Necronomicon(EquipableItem.slot_WEAPON));
					C2.add(new tome_BookofXithiax(EquipableItem.slot_WEAPON));
                    
					C3.add(new LeatherBoots(EquipableItem.slot_BOOTS));
					C3.add(new LeatherSandals(EquipableItem.slot_BOOTS));
					C3.add(new Sandals(EquipableItem.slot_BOOTS));
					
					C1.add(new BlackBandana(EquipableItem.slot_HEAD));
				
					C1.add(new DemonicTopHat(EquipableItem.slot_HEAD));
					C1.add(new Facewrap(EquipableItem.slot_HEAD));
                    C1.add(new GreenfingersCap(EquipableItem.slot_HEAD));
                    C1.add(new MagusBandana(EquipableItem.slot_HEAD));
                    C1.add(new MagusCap(EquipableItem.slot_HEAD));

                    C1.add(new MagusTurban(EquipableItem.slot_HEAD));
                    C1.add(new PyromancerCap(EquipableItem.slot_HEAD));
                    C1.add(new SandblastedTurban(EquipableItem.slot_HEAD));
                    C1.add(new TopHat(EquipableItem.slot_HEAD));

                    C1.add(new RangersBandana(EquipableItem.slot_HEAD));
                    C3.add(new ArcaneFootwraps(EquipableItem.slot_BOOTS));
                    C3.add(new ArcaneSandals(EquipableItem.slot_BOOTS));
                    C3.add(new BlackleatherBoots(EquipableItem.slot_BOOTS));
                  
                    C3.add(new Footwraps(EquipableItem.slot_BOOTS));
					/*
					ChestInventory.add(new Charm_ATC(EquipableItem.slot_UTILITY1));
					ChestInventory.add(new Charm_DEF(EquipableItem.slot_UTILITY1));
					ChestInventory.add(new Charm_AGI(EquipableItem.slot_UTILITY1));
					ChestInventory.add(new Charm_VIT(EquipableItem.slot_UTILITY1));
					ChestInventory.add(new Charm_WIS(EquipableItem.slot_UTILITY1));
					ChestInventory.add(new Charm_EDR(EquipableItem.slot_UTILITY1));
					ChestInventory.add(new Charm_MAT(EquipableItem.slot_UTILITY1));
					ChestInventory.add(new Charm_MDF(EquipableItem.slot_UTILITY1));
					*/
					
					animSprite = shop3;
					faceframe = 17;
					break;
				case 3: 
					animSprite = Artificer;
					this.ChestInventory.chestType = this.ChestInventory.chestType.Normal;
//					faceframe = 17;
					break;
					
				case 4:
					animSprite = inn;
					this.xROffset = -16;
					this.yROffset = 0;
					faceframe = 17;
					ChestInventory.add(new RoomKey(0));
					break;
				}
				//ChestInventory.add(new Coin(Coin.Type.ADVANCED));
				added =! added;
				this.setPage(0);
			}	
		}
	}
	
	public void setPage(int page) {
		switch (type) {
		
		case 0:
		switch (page) {
		case 0:
			ChestInventory = P0;
			break;
		case 1:
			ChestInventory = P1;
			break;
		case 2:
			ChestInventory = P2;
			break;
		case 3:
			ChestInventory = P3;
			break;
		case 4:
			ChestInventory = P4;
			break;
		case 5:
			ChestInventory = P5;
			break;

		}
			break;
		case 1:
			switch (page) {
			case 0:
				ChestInventory = S0;
				break;
			case 1:
				ChestInventory = S1;
				break;
			case 2:
				ChestInventory = S2;
				break;
			case 3:
				ChestInventory = S3;
				break;
			case 4:
				ChestInventory = S4;
				break;
			case 5:
				ChestInventory = S5;
				break;

			}
			break;
			
		case 2:
			switch (page) {
			case 0:
				ChestInventory = C0;
				break;
			case 1:
				ChestInventory = C1;
				break;
			case 2:
				ChestInventory = C2;
				break;
			case 3:
				ChestInventory = C3;
				break;
			case 4:
				ChestInventory = C4;
				break;
			case 5:
				ChestInventory = C5;
				break;

			}
			break;
		
		}
		ChestInventory.setType(ChestInventory.chestType.Shop);
	}
	
	public void detectPage() {
		
		if (gui.checkBounds(112, 70, 16, 16, true, false)) {
			if(Mouse.getButton() == 1) {
				if (Page < 6) {
					Page++;
					setPage(Page);
				}
				Mouse.setMouseB(-1);
			}
		}
		
		if (gui.checkBounds(10, 70, 16, 16, true, false)) {
			if(Mouse.getButton() == 1) {
				if (Page > 0) {
					Page--;
					setPage(Page);
				}
				Mouse.setMouseB(-1);
			}
		}
	}
	
	public void update() {
		if (Game.getGame().key.generalActivator && Game.getGame().getPlayer().nearShop == this) {
			detectPage();
		}

		addTimer++;
		if (addTimer > 1000) {
			addTimer = 0;
		}
		decideGeneration();
		//if (time % 1 == 0) {
		if (type == 3) {
			animSprite.update();
		}
		if (type == 1 && animSprite == Blacksmith_still) {
			animSprite.update();
		}
		
		
		if (!level.getPlayersFixedBool((int)this.x, (int)this.y + 16, 20)) {
			animSprite.update();
			if (level.getClientPlayer().nearShop == this) {
				level.getClientPlayer().nearShop = null;
			}
		} else {
			level.getClientPlayer().nearShop = this;
			if (addTimer > 25) {
				//testItem();			
				addTimer = 0;
			}
		}
		//}
	
			}
	
	
	public void addRNG(Player p, AbstractMatter item) {
		if (!ChestInventory.isFull()) {
			
			if (item.tier == Tier.LARGE) {
				randGen = (Rand.nextInt((13 - 7) + 7) + 7);
			}else if (item.tier == Tier.MEDIUM) {
				randGen = (Rand.nextInt((13 - 4) + 4) + 4);
			}else if (item.tier == Tier.SMALL) {
				randGen = (Rand.nextInt((8 - 1) + 1) + 1);
			}else if (item.tier == Tier.SHARD) {
				randGen = (Rand.nextInt((6 - 1) + 1) + 1);
			}
		
		switch(randGen) {
		case 13: 
			ChestInventory.add(new IronIngot());
			ChestInventory.add(new CopperIngot());
		case 11: 
			ChestInventory.add(new IronIngot());
			break;
		case 9: 
			ChestInventory.add(new IronIngot());
			ChestInventory.add(new IronIngot());
			break;
		case 8: 
			ChestInventory.add(new CopperIngot());
			ChestInventory.add(new CopperIngot());
			ChestInventory.add(new IronIngot());
			break;
		case 7: 
			ChestInventory.add(new Stick());
			ChestInventory.add(new Leather());
			ChestInventory.add(new Leather());
			break;
		case 5: 
			ChestInventory.add(new Stick());
			ChestInventory.add(new Leather());
			break;
		case 4: 
			ChestInventory.add(new Stick());
			ChestInventory.add(new Stick());
			break;
		case 3: 
			ChestInventory.add(new Stick());
			break;
		case 1: 
			ChestInventory.add(new IronIngot());
			ChestInventory.add(new Stick());
			break;
		}
		
		}
	}
	
	public boolean buyItem(Player p, Item item, int i) {
		boolean result = false;
		
	if (type != 3) {
		if (p.nearShop != null) {
		if (item.item_TYPE == this.type || item.item_TYPE == 4) {
		if (Double.parseDouble(item.price) < 50 || ChestInventory.add(item)) {
			if ( i == -1 || p.inventory.removeByIndex(i)) {
			item.nearShop = false;
			p.money += (Integer.parseInt(item.price) * 0.75);
			result = true;
			Sound.Play(Sound.coin, false);
				} else {
				}
			}
			}
		}
		} else {
			if (p.nearShop != null) {
				if (item instanceof AbstractMatter) {
				if (i == -1 || p.inventory.removeByIndex(i)) {
					//	addRNG(p, (AbstractMatter)item);
						result = true;
						}
				} else {
				result = false;
				}
			}
		}
		return result;
	}

	public void testItem() {
		List<PlayerMP> players = level.getPlayersFixed((int)this.x, (int) this.y + 16, 30);
		List<Item> items = level.getItemsFixed((int)this.x + 8, (int) this.y + 16, 30);
		for (int i = 0; i < players.size(); i++) {
			if (players.contains(players.get(0))) {
			players.get(0).nearShop = this;
			} else {
				players.get(0).nearShop = null;
			}
		}
		if (!this.ChestInventory.isFull()) {
		if (items.size() > 0) {
		for (int i = 0; i < items.size(); i++) {
				//System.out.println(items.get(i).type_ARMOR + " : SHOP: " + this.type);
				if (items.get(i).item_TYPE == this.type) {
					items.get(i).nearShop = true;
					
					if (ChestInventory.add(items.get(i))) {
						items.get(i).nearShop = false;
						players.get(0).money += (Integer.parseInt(items.get(i).price) * 0.75);
						items.get(i).remove();
						Sound.Play(Sound.coin, false);
							}
				}
						}
				}}}
	
	boolean guiOpen = false;
	protected int xoff = 0;
	protected int yoff = 16;
	public void OpenChest(Screen screen) {
		List<PlayerMP> players = level.getPlayersFixed((int)this.x + xoff, (int) this.y + yoff, 20);
		if (level.getPlayersFixedBool((int)this.x + xoff, (int)this.y + yoff, 20)) {
			
			if (this.type == 1) {
				animSprite = Blacksmith_still;
			} else if (type == 3) {
				animSprite.update();
			}else if (type != 1 && type != 3){
				animSprite.setFrame(faceframe);
			}
			
			guiOpen = true;
			
			for (int i = 0; i < players.size(); i++) {
				if (type == 3) {
					//gui.renderName(screen, "The Void Grows Hungry", (int) x - 65, (int) y - 16, -3, true, true, true);
				}
			if (players.get(i).input.generalActivator && !players.get(i).inventoryEnabled) {  
				screen.renderSprite(116, 70, Sprite.ArrowRight, false);
				screen.renderSprite(7, 70, Sprite.ArrowLeft, false);
				gui.renderInventory(screen, Game.getGame().getPlayer(), 140, 30);
				Game.getGame().getPlayer().inventory.abViewExpanded = false;
				gui.renderInventory(screen, this, players.get(i));
				//Game.getGame().getPlayer().inventory.tab = TAB.ITEMS;
				players.get(i).inChest = true;
			} else {
				players.get(i).inChest = false;
				gui.font8x8.render(110, 15, -2, 0xff000000, "Trade - 'F'", screen, false, true);
				gui.font8x8.render(109, 15, -2, 0xffFFFFFF, "Trade - 'F'", screen, false, false);
			}
			}
		} else {
			if (this.type == 1) {
				animSprite = Blacksmith;
			}
			guiOpen = true;
		}
	} 
	
	public void render(Screen screen) {
		if (Game.getGame().gameState == gameState.INGAME_A) {
			Debug.drawRect(screen, (int)x, (int)y, 16, 16, 0xFF00FF, true);
		}
		//int radius = level.radius / 2 + 5;
		//screen.renderLight((int)x - 34 + radius, (int)y - 30 + radius, 50 - radius, 20, 20, 40);
			sprite = animSprite.getSprite();

			
		screen.renderMobSpriteUniversal((int)x + xROffset, (int)y + yROffset, sprite);
	}
	
	public void renderGUI(Screen screen) {
		OpenChest(screen);
	}
}