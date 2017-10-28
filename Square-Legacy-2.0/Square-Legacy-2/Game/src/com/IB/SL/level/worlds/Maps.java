package com.IB.SL.level.worlds;

import java.io.Serializable;

public class Maps implements Serializable{
	
	public static int spawnHavenId = 0;
	public static int mainId = 1;
	public static int tutWorldId = 2;
	public static int dungeon1Id = 3;
	public static int dungeon2Id = 4;
	public static int voidBossRoomId = 5;
	public static int dungeon3Id = 6;
	public static int dungeon4Id = 7;
	
	public static int CaveHavenId = 8;
	public static int IceHavenId = 9;
	public static int RedTownHavenId = 10;
	public static int SandHavenId = 11;
	public static int SouthTownHavenId = 12;

	public static int swampId = -1;  // Unused

	public static String main = ("/levels/WorldMap.png");
	public static String Swamp = ("/levels/Swamp.png");	
	
	public static String SpawnHaven = ("/levels/SpawnHaven.png");
	public static String CaveHaven = ("/levels/CaveHaven.png");
	public static String IceHaven = ("/levels/IceHaven.png");
	public static String RedTownHaven = ("/levels/RedTownHaven.png");
	public static String SandHaven = ("/levels/SandHaven.png");
	public static String SouthTownHaven = ("/levels/SouthTownHaven.png");

	public static String Tutorial_World = ("/levels/Tutorial.png");

	public static String dungeon01 = ("/levels/Dungeons/Labarynth.png");
	public static String dungeon02 = ("/levels/Dungeons/VoidDungeon.png");
	public static String dungeon03 = ("/levels/Dungeons/IceTemple.png");
	public static String dungeon04 = ("/levels/Dungeons/SandDung.png");
	public static String VoidBossRoom = ("/levels/Dungeons/VoidBossRoom.png");
}
