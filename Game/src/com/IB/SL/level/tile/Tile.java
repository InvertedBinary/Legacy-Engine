package com.IB.SL.level.tile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.level.VoidTile;
import com.IB.SL.level.tile.SL2.Air;
import com.IB.SL.level.tile.SL2.XML_Tile;
import com.IB.SL.level.tile.overlays.Anvil;
import com.IB.SL.level.tile.overlays.BlueBed;
import com.IB.SL.level.tile.overlays.BlueMushroom;
import com.IB.SL.level.tile.overlays.Bone;
import com.IB.SL.level.tile.overlays.BottomChair;
import com.IB.SL.level.tile.overlays.BrokenSword;
import com.IB.SL.level.tile.overlays.Cactus;
import com.IB.SL.level.tile.overlays.Castle;
import com.IB.SL.level.tile.overlays.Cave;
import com.IB.SL.level.tile.overlays.ColoredFlowers;
import com.IB.SL.level.tile.overlays.Counter;
import com.IB.SL.level.tile.overlays.Crossbone;
import com.IB.SL.level.tile.overlays.DarkCastle;
import com.IB.SL.level.tile.overlays.DirtPatch;
import com.IB.SL.level.tile.overlays.FlowerCactus;
import com.IB.SL.level.tile.overlays.GreenBed;
import com.IB.SL.level.tile.overlays.GreenMushroom;
import com.IB.SL.level.tile.overlays.LeftChair;
import com.IB.SL.level.tile.overlays.OrangeBed;
import com.IB.SL.level.tile.overlays.OvVoidTile;
import com.IB.SL.level.tile.overlays.PathCornerBL;
import com.IB.SL.level.tile.overlays.PathCornerBR;
import com.IB.SL.level.tile.overlays.PathCornerTR;
import com.IB.SL.level.tile.overlays.PathCross;
import com.IB.SL.level.tile.overlays.PathEndBottom;
import com.IB.SL.level.tile.overlays.PathEndLeft;
import com.IB.SL.level.tile.overlays.PathEndRight;
import com.IB.SL.level.tile.overlays.PathEndTop;
import com.IB.SL.level.tile.overlays.PathHorizontal;
import com.IB.SL.level.tile.overlays.PathVertical;
import com.IB.SL.level.tile.overlays.Pebble;
import com.IB.SL.level.tile.overlays.Portal;
import com.IB.SL.level.tile.overlays.RedBed;
import com.IB.SL.level.tile.overlays.RedMushroom;
import com.IB.SL.level.tile.overlays.RightChair;
import com.IB.SL.level.tile.overlays.Skull;
import com.IB.SL.level.tile.overlays.Stove;
import com.IB.SL.level.tile.overlays.Table;
import com.IB.SL.level.tile.overlays.TopChair;
import com.IB.SL.level.tile.overlays.Village;
import com.IB.SL.level.tile.overlays.YellowFlowers;
import com.IB.SL.level.tile.tiles.Bluefog;
import com.IB.SL.level.tile.tiles.BookshelfBottom;
import com.IB.SL.level.tile.tiles.BookshelfTop;
import com.IB.SL.level.tile.tiles.BrickCeiling;
import com.IB.SL.level.tile.tiles.BrickWall;
import com.IB.SL.level.tile.tiles.CaveCeiling;
import com.IB.SL.level.tile.tiles.CaveWall;
import com.IB.SL.level.tile.tiles.CobbleStoneWall;
import com.IB.SL.level.tile.tiles.CobblestoneCeiling;
import com.IB.SL.level.tile.tiles.ColorFlowers;
import com.IB.SL.level.tile.tiles.CrackedBrick;
import com.IB.SL.level.tile.tiles.DarkStone;
import com.IB.SL.level.tile.tiles.DeepLava;
import com.IB.SL.level.tile.tiles.DeepWater;
import com.IB.SL.level.tile.tiles.Dirt;
import com.IB.SL.level.tile.tiles.DirtCeiling;
import com.IB.SL.level.tile.tiles.DirtWall;
import com.IB.SL.level.tile.tiles.DoorTile;
import com.IB.SL.level.tile.tiles.DresserBottom;
import com.IB.SL.level.tile.tiles.DresserTop;
import com.IB.SL.level.tile.tiles.HellBrick;
import com.IB.SL.level.tile.tiles.HellBrickCeiling;
import com.IB.SL.level.tile.tiles.HellCaveCeiling;
import com.IB.SL.level.tile.tiles.HellCaveWall;
import com.IB.SL.level.tile.tiles.HellSandCeiling;
import com.IB.SL.level.tile.tiles.HellSandWall;
import com.IB.SL.level.tile.tiles.HellbrickWall;
import com.IB.SL.level.tile.tiles.Hellsand;
import com.IB.SL.level.tile.tiles.Hellstone;
import com.IB.SL.level.tile.tiles.Ice;
import com.IB.SL.level.tile.tiles.IceBrick;
import com.IB.SL.level.tile.tiles.IceBrickCeiling;
import com.IB.SL.level.tile.tiles.IceBrickWall;
import com.IB.SL.level.tile.tiles.IceCaveCeiling;
import com.IB.SL.level.tile.tiles.IceCaveWall;
import com.IB.SL.level.tile.tiles.IceSand;
import com.IB.SL.level.tile.tiles.IceSandCeiling;
import com.IB.SL.level.tile.tiles.IceSandWall;
import com.IB.SL.level.tile.tiles.Lava;
import com.IB.SL.level.tile.tiles.Metal;
import com.IB.SL.level.tile.tiles.MetalCeiling;
import com.IB.SL.level.tile.tiles.MetalWall;
import com.IB.SL.level.tile.tiles.MossCeiling;
import com.IB.SL.level.tile.tiles.MossWall;
import com.IB.SL.level.tile.tiles.ObsidianCeiling;
import com.IB.SL.level.tile.tiles.ObsidianWall;
import com.IB.SL.level.tile.tiles.PathDirt;
import com.IB.SL.level.tile.tiles.Sand;
import com.IB.SL.level.tile.tiles.SandBrick;
import com.IB.SL.level.tile.tiles.SandBrickCeiling;
import com.IB.SL.level.tile.tiles.SandBrickWall;
import com.IB.SL.level.tile.tiles.SandCeiling;
import com.IB.SL.level.tile.tiles.SandWall;
import com.IB.SL.level.tile.tiles.Snow;
import com.IB.SL.level.tile.tiles.StoneBrick;
import com.IB.SL.level.tile.tiles.StoneBrickCeiling;
import com.IB.SL.level.tile.tiles.StoneBrickWall;
import com.IB.SL.level.tile.tiles.Swamp;
import com.IB.SL.level.tile.tiles.Swirly;
import com.IB.SL.level.tile.tiles.Terrain;
import com.IB.SL.level.tile.tiles.TorchTile;
import com.IB.SL.level.tile.tiles.Water;
import com.IB.SL.level.tile.tiles.Wood;
import com.IB.SL.level.tile.tiles.WoodCeiling;
import com.IB.SL.level.tile.tiles.WoodWall;
import com.IB.SL.level.tile.tiles.bitBrick;
import com.IB.SL.level.tile.tiles.bitMetal;

public class Tile {
	
	public enum stepSound {
		Organic, Hard, Squishy, Water;
	}
	
	public static HashMap<Integer, Tile> TileIndex = new HashMap<Integer, Tile>();

	public static Tile returnTile(int hex) {
		return TileIndex.get(hex);
	}
	
	public String XML_String = "";

	public Tile() {
		
	}
	
	public void readXML(String path) {
		this.XML_String = path;
		try {
		InputStream fXmlFile = Tile.class.getResourceAsStream(path);
		DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		
		//System.out.println("ROOT: " + doc.getDocumentElement().getNodeName());
		TileIndex.put(0, Air);
		buildTiles(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buildTiles(Document doc) {
		NodeList nList = doc.getElementsByTagName("Tiles");
		//System.out.println("--------------TILES--------------");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				for (int i = 0; i < (((Element) nNode).getElementsByTagName("tile").getLength()); i++) {
					try {
						Element e = (Element) ((Element) nNode).getElementsByTagName("tile").item(i);
						//int hex = Long.decode(e.getAttribute("hex")).intValue();
						int id = Integer.parseInt(e.getAttribute("id"));
						boolean solid = (Boolean.parseBoolean(e.getAttribute("solid")));
						boolean solidTwo = (Boolean.parseBoolean(e.getAttribute("projSolid")));
						boolean jumpThrough = (Boolean.parseBoolean(e.getAttribute("jumpThrough")));
						boolean isExit = (Boolean.parseBoolean(e.getAttribute("isExitTile")));
						Field field = Sprite.class.getField(e.getAttribute("sprite"));
						Sprite sp = (Sprite) field.get(field.getType());
						String name = (e.getTextContent());

						/*System.out.println("HEX: " + (hex)
										+ ", AA: " + (hex >> 24)
										+ ", RR: " + (hex >> 0)
										+ ", GG: " + (hex >> 8)
										+ ", BB: " + (hex >> 16));*/

						XML_Tile t = new XML_Tile(name, sp, stepSound.Hard, id, solid, solidTwo, jumpThrough, isExit);
						TileIndex.put(id, t);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public Sprite sprite;
	
	public static Tile bitBrick = new bitBrick(Sprite.bitBrick);
	public static Tile bitMetal = new bitMetal(Sprite.bitMetal);
	public static Tile Bluefog = new Bluefog(Sprite.Bluefog);
	
	public static Tile BookshelfBottom = new BookshelfBottom(Sprite.BookshelfBottom);
	public static Tile BookshelfTop = new BookshelfTop(Sprite.BookshelfTop);
	public static Tile BrickCeiling = new BrickCeiling(Sprite.BrickCeiling);
	public static Tile BrickWall = new BrickWall(Sprite.BrickWall);
	public static Tile CaveCeiling = new CaveCeiling(Sprite.CaveCeiling);
	public static Tile CaveWall = new CaveWall(Sprite.CaveWall);
	
	public static Tile Grass = new Terrain(Sprite.Grass, stepSound.Organic, Level.GrassHex);
	
	public static Tile CobbleStone = new Terrain(Sprite.CobbleStone, stepSound.Hard, Level.CobbleStoneHex);
	public static Tile CobblestoneCeiling = new CobblestoneCeiling(Sprite.CobblestoneCeiling);
	
	public static Tile CobbleStoneWall = new CobbleStoneWall(Sprite.CobbleStoneWall);
	public static Tile ColorFlowers = new ColorFlowers(Sprite.ColorFlowers);
	public static Tile CrackedBrick = new CrackedBrick(Sprite.CrackedBrick);
	public static Tile DarkStone = new DarkStone(Sprite.DarkStone);
	
	public static Tile DeepLava = new DeepLava(Sprite.DeepLava);
	public static Tile DeepWater = new DeepWater(Sprite.DeepWater);
	public static Tile Dirt = new Dirt(Sprite.Dirt);
	public static Tile DirtCeiling = new DirtCeiling(Sprite.DirtCeiling);
	
	public static Tile DirtWall = new DirtWall(Sprite.DirtWall);
	public static Tile DresserBottom = new DresserBottom(Sprite.DresserBottom);
	public static Tile DresserTop = new DresserTop(Sprite.DresserTop);
	
	
	
	public static Tile HellBrick = new HellBrick(Sprite.HellBrick);
	public static Tile HellBrickCeiling = new HellBrickCeiling(Sprite.HellBrickCeiling);
	public static Tile HellbrickWall = new HellbrickWall(Sprite.HellbrickWall);
	public static Tile HellCaveCeiling = new HellCaveCeiling(Sprite.HellCaveCeiling);
	public static Tile HellCaveWall = new HellCaveWall(Sprite.HellCaveWall);
	public static Tile Hellsand = new Hellsand(Sprite.Hellsand);
	public static Tile HellSandCeiling = new HellSandCeiling(Sprite.HellSandCeiling);
	public static Tile HellSandWall = new HellSandWall(Sprite.HellSandWall);
	public static Tile Hellstone = new Hellstone(Sprite.Hellstone);
	public static Tile Ice = new Ice(Sprite.Ice);
	public static Tile IceBrick = new IceBrick(Sprite.IceBrick);
	public static Tile IceBrickCeiling = new IceBrickCeiling(Sprite.IceBrickCeiling);
	public static Tile IceBrickWall = new IceBrickWall(Sprite.IceBrickWall);
	public static Tile IceCaveCeiling = new IceCaveCeiling(Sprite.IceCaveCeiling);
	public static Tile IceCaveWall = new IceCaveWall(Sprite.IceCaveWall);
	public static Tile IceSand = new IceSand(Sprite.IceSand);
	public static Tile IceSandCeiling = new IceSandCeiling(Sprite.IceSandCeiling);
	public static Tile IceSandWall = new IceSandWall(Sprite.IceSandWall);
	public static Tile Lava = new Lava(Sprite.Lava);
	public static Tile Metal = new Metal(Sprite.Metal);
	public static Tile MetalCeiling = new MetalCeiling(Sprite.MetalCeiling);
	public static Tile MetalWall = new MetalWall(Sprite.MetalWall);
	public static Tile MossCeiling = new MossCeiling(Sprite.MossCeiling);
	public static Tile MossWall = new MossWall(Sprite.MossWall);
	public static Tile ObsidianCeiling = new ObsidianCeiling(Sprite.ObsidianCeiling);
	public static Tile ObsidianWall = new ObsidianWall(Sprite.ObsidianWall);
	
	public static Tile PathDirt = new PathDirt(Sprite.PathDirt);
	
	public static Tile Sand = new Sand(Sprite.Sand);
	public static Tile SandBrick = new SandBrick(Sprite.SandBrick);
	public static Tile SandBrickCeiling = new SandBrickCeiling(Sprite.SandBrickCeiling);
	public static Tile SandBrickWall = new SandBrickWall(Sprite.SandBrickWall);
	public static Tile SandCeiling = new SandCeiling(Sprite.SandCeiling);
	public static Tile SandWall = new SandWall(Sprite.SandWall);
	public static Tile Snow = new Snow(Sprite.Snow);
	public static Tile StoneBrick = new StoneBrick(Sprite.StoneBrick);
	public static Tile StoneBrickCeiling = new StoneBrickCeiling(Sprite.StoneBrickCeiling);
	public static Tile StoneBrickWall = new StoneBrickWall(Sprite.StoneBrickWall);
	public static Tile Swamp = new Swamp(Sprite.Swamp);
	
	public static Tile VoidTile = new VoidTile(Sprite.VoidTile);
	public static Tile Air = new Air(Sprite.VoidTile);

	//public static Tile Water = new Water();
	public static Tile Wood = new Wood(Sprite.Wood);
	public static Tile WoodCeiling = new WoodCeiling(Sprite.WoodCeiling);
	public static Tile WoodWall = new WoodWall(Sprite.WoodWall);
	public static Tile swirly = new Swirly(Sprite.Swirly);
	public static Tile DoorTile = new DoorTile(Sprite.Door);
	
	
	/**
	 * Overlay
	 */
	public static Tile TorchTile = new TorchTile(Sprite.Torch);
	public static Tile Cactus = new Cactus(Sprite.Cactus);
	public static Tile FlowerCactus = new FlowerCactus(Sprite.FlowerCactus);
	public static Tile ColoredFlowers = new ColoredFlowers(Sprite.ColoredFlowers);
	public static Tile YellowFlowers = new YellowFlowers(Sprite.YellowFlowers);
	public static Tile Pebble = new Pebble(Sprite.Pebble);
	public static Tile BlueMushroom = new BlueMushroom(Sprite.BlueMushroom);
	public static Tile BlueMushroomDirt = new com.IB.SL.level.tile.overlays.BlueMushroomDirt(Sprite.BlueMushroomDirt);
	public static Tile RedMushroom = new RedMushroom(Sprite.RedMushroom);
	public static Tile RedMushroomDirt = new com.IB.SL.level.tile.overlays.RedMushroom(Sprite.RedMushroomDirt);
	public static Tile GreenMushroom = new GreenMushroom(Sprite.GreenMushroom);
	public static Tile GreenMushroomDirt = new com.IB.SL.level.tile.overlays.GreenMushroom(Sprite.GreenMushroomDirt);
	public static Tile DirtPatch = new DirtPatch(Sprite.DirtPatch);
	public static Tile DarkCastle = new DarkCastle(Sprite.DarkCastle);
	public static Tile Cave = new Cave(Sprite.Cave);
	public static Tile Portal = new Portal(Sprite.Portal);
	public static Tile PathVertical = new PathVertical(Sprite.PathVertical);
	public static Tile PathHorizontal = new PathHorizontal(Sprite.PathHorizontal);
	public static Tile PathCross = new PathCross(Sprite.PathCross);
	public static Tile PathCornerTL = new com.IB.SL.level.tile.overlays.PathCornerTL(Sprite.PathCornerTL);
	public static Tile PathCornerTR = new PathCornerTR(Sprite.PathCornerTR);
	public static Tile PathCornerBL = new PathCornerBL(Sprite.PathCornerBL);
	public static Tile PathCornerBR = new PathCornerBR(Sprite.PathCornerBR);
	public static Tile PathEndLeft = new PathEndLeft(Sprite.PathEndLeft);
	public static Tile PathEndRight = new PathEndRight(Sprite.PathEndRight);
	public static Tile PathEndTop = new PathEndTop(Sprite.PathEndTop);
	public static Tile PathEndBottom = new PathEndBottom(Sprite.PathEndBottom);
	public static Tile BrokenSword = new BrokenSword(Sprite.BrokenSword);
	public static Tile RedBed = new RedBed(Sprite.RedBed);
	public static Tile BlueBed = new BlueBed(Sprite.BlueBed);
	public static Tile GreenBed = new GreenBed(Sprite.GreenBed);
	public static Tile OrangeBed = new OrangeBed(Sprite.OrangeBed);
	public static Tile TopChair = new TopChair(Sprite.TopChair);
	public static Tile BottomChair = new BottomChair(Sprite.BottomChair);
	public static Tile LeftChair = new LeftChair(Sprite.LeftChair);
	public static Tile RightChair = new RightChair(Sprite.RightChair);
	public static Tile Castle = new Castle(Sprite.Castle);
	public static Tile Village = new Village(Sprite.Village);
	public static Tile Table = new Table(Sprite.Table);
	public static Tile bone = new Bone(Sprite.bone);
	public static Tile crossbone = new Crossbone(Sprite.crossBone);
	public static Tile skull = new Skull(Sprite.skull);
	public static Tile Anvil = new Anvil(Sprite.Anvil);
	public static Tile Stove = new Stove(Sprite.Stove);
	public static Tile OvVoidTile = new OvVoidTile(Sprite.VoidTile);
	public static Tile Counter = new Counter(Sprite.CounterA);
	public static Tile CounterB = new Counter(Sprite.CounterB);

	//public static final int col_grass = 0xff00ff00;
	//public static final int col_gmcsDOWN = 0xff00ff00;
	//public static final int col_gmcsLEFT = 0xff00ff00;
	//public static final int col_gmcsUP = 0xff00ff00;
	//public static final int col_cobblestone = 0xff00ff00;
	//public static final int col_flower = 0xff00ff00;
	//public static final int col_water = 0xff00ff00;
	public static Tile Water = new Water(Sprite.water);
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}


	public void render(int x, int y, Screen screen) {
	}
	
	public boolean solid() {
		return false;
	}
	
	public stepSound StepSound() {
		return null;
	}

	public boolean exit() {
		return false;
	}

	public boolean solidtwo() {
		return false;
	}
	
	public boolean jumpThrough() {
		return false;
	}

	public boolean liquid() {
		return false;
	}
	
	public boolean illuminator() {
		return false;
	}
	
	public boolean antiSpawn() {
		return false;
	}
	
	public int getHex() {
		return 0xffFF00FF;
	}

}
