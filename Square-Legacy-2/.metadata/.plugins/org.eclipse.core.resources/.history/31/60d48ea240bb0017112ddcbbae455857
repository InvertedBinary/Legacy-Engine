package com.IB.SL.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.Quest;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_ArcaneTwig;
import com.IB.SL.entity.mob.PlayerMP;

public class SaveGame {
	public static final String fileName = "/SaveData.sl";
	public static final String itemFileName = "/ItemData.sl";
	public static final String equipFileName = "/EquipData.sl";
	public static final String questsFileName = "/QuestData.sl";
	public static final String prefsFileName = "/PrefsData.sl";
	public static final String mobsFileName = "/MobData.sl";
	public static final String root = "/SquareLegacy";
	

	
	public static void save(Serializable objectToSerialze) {
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(createSaveFolder() + fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objectToSerialze);
			oos.flush();
			oos.close();
			System.out.println("Saving: " + objectToSerialze);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed To Save: " + objectToSerialze);
		}
		
	}
	
	public static void save(Serializable objectToSerialze, String fileName) {
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(createSaveFolder() + fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objectToSerialze);
			oos.flush();
			oos.close();
			System.out.println("Saving: " + objectToSerialze);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed To Save: " + objectToSerialze);

		}
		
	}
	
	public static PlayerMP load() {
		
		if(checkSaveExists(fileName)) {
			FileInputStream fis = null;
				PlayerMP loadedObject = null;
			try {
				
				fis = new FileInputStream(createSaveFolder() + fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);
				loadedObject = (PlayerMP) ois.readObject();
				ois.close();
				fis.close();
				System.out.println("Loaded: " + loadedObject);
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Failed To Load Object");
				e.printStackTrace();
			}
			return loadedObject;
			
		}
		return null;
	}
	
public static PlayerMP load(String name) {
		
		if(checkSaveExists(fileName)) {
			FileInputStream fis = null;
				PlayerMP loadedObject = null;
			try {
				
				fis = new FileInputStream(createSaveFolder(name) + fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);
				loadedObject = (PlayerMP) ois.readObject();
				ois.close();
				fis.close();
				System.out.println("Loaded: " + loadedObject);
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Failed To Load Object");
				e.printStackTrace();
			}
			return loadedObject;
			
		}
		return null;
	}
	
	
	public static Item[] loadItems() {
		
		if(checkSaveExists(itemFileName)) {
			FileInputStream fis = null;
				Item[] loadedObject = null;
			try {
				
				fis = new FileInputStream(createSaveFolder() + itemFileName);
				ObjectInputStream ois = new ObjectInputStream(fis);
				loadedObject = (Item[]) ois.readObject();
				ois.close();
				fis.close();
				System.out.println("Loaded: " + loadedObject);
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Failed To Load Object");
				e.printStackTrace();
			}
			return loadedObject;
			
		}
		return null;
	}
	
public static Entity[] loadEntities() {
		
		if(checkSaveExists(mobsFileName)) {
			FileInputStream fis = null;
				Entity[] loadedObject = null;
			try {
				
				fis = new FileInputStream(createSaveFolder() + SaveGame.mobsFileName + Game.getGame().getPlayer().currentLevelId);
				ObjectInputStream ois = new ObjectInputStream(fis);
				loadedObject = (Entity[]) ois.readObject();
				ois.close();
				fis.close();
				System.out.println("Loaded: " + loadedObject);
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Failed To Load Object");
				e.printStackTrace();
			}
			return loadedObject;
			
		}
		return null;
	}
	
public static Entity[] loadEntities(int levelID) {
	System.out.println(">>>>>>>>>>>>>>>>>> || Loading.......: " + SaveGame.mobsFileName + levelID);
	if(checkSaveExists(SaveGame.mobsFileName + levelID)) {
		FileInputStream fis = null;
			Entity[] loadedObject = null;
		try {
			
			fis = new FileInputStream(createSaveFolder() + SaveGame.mobsFileName + levelID);
			ObjectInputStream ois = new ObjectInputStream(fis);
			loadedObject = (Entity[]) ois.readObject();
			ois.close();
			fis.close();
			System.out.println("Loaded: " + loadedObject);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Failed To Load Object");
			e.printStackTrace();
		}
		return loadedObject;
		
	}
	return null;
}

	
	public static Quest[] loadQuests() {
		
		if(checkSaveExists(questsFileName)) {
			FileInputStream fis = null;
				Quest[] loadedObject = null;
			try {
				
				fis = new FileInputStream(createSaveFolder() + questsFileName);
				ObjectInputStream ois = new ObjectInputStream(fis);
				loadedObject = (Quest[]) ois.readObject();
				ois.close();
				fis.close();
				System.out.println("Loaded: " + loadedObject);
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Failed To Load Object");
				e.printStackTrace();
			}
			return loadedObject;
			
		}
		return null;
	}
	
	public static EquipableItem[] loadEquips() {
		
		if(checkSaveExists(equipFileName)) {
			FileInputStream fis = null;
				EquipableItem[] loadedObject = null;
			try {
				try {
					fis = new FileInputStream(createSaveFolder() + equipFileName);					
				} catch (FileNotFoundException e) {
					Game.getGame().getPlayer().equipment.Equip(new wand_ArcaneTwig(EquipableItem.slot_WEAPON));
				//	Game.getGame().getPlayer().equipment.Equip(new CottonRobe(EquipableItem.slot_CHEST, Game.getGame().getPlayer()));
				}
				ObjectInputStream ois = new ObjectInputStream(fis);
				loadedObject = (EquipableItem[]) ois.readObject();
				ois.close();
				fis.close();
				System.out.println("Loaded: " + loadedObject);
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Failed To Load Object");
				e.printStackTrace();
			}
			return loadedObject;
			
		}
		return null;
	}
	
public static LoadProperties loadPrefs() {
		
		if(checkSaveExists(prefsFileName)) {
			FileInputStream fis = null;
			LoadProperties loadedObject = null;
			try {
				try {
					fis = new FileInputStream(createSaveFolder() + prefsFileName);					
				} catch (FileNotFoundException e) {
				}
				ObjectInputStream ois = new ObjectInputStream(fis);
				loadedObject = (LoadProperties) ois.readObject();
				ois.close();
				fis.close();
				System.out.println("Loaded: " + loadedObject);
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Failed To Load Object");
				e.printStackTrace();
			}
			return loadedObject;
			
		}
		return null;
	}
	
	
	public static boolean checkFileExists() {
		return new File(createDataFolder() + fileName).isFile();
	}
	
	public static boolean checkFileExists(String fileName) {
		return new File(createDataFolder() + fileName).isFile();
	}
	
	public static boolean checkSaveExists(String fileName) {
		return new File(createSaveFolder() + fileName).isFile();
	}
	
	public static String createDataFolder() {
		String home = System.getProperty("user.home");
		String OS = System.getProperty("os.name").toLowerCase();
		
		if (OS.contains("win")) {
			home = System.getenv("appdata");
		} else if (OS.contains("mac")) {
			home += "~/Library/Application Support";
		} else if (OS.contains("nix") || OS.contains("aix")) {
			home += "~/.";
		}
		
		File dir = new File(home);
		dir = new File(dir, root);
		
		
		
		if (!dir.exists()) {
			dir.mkdir();
			System.out.println("Creating directory");
			
		}
		
		
		
		
		return dir.getAbsolutePath();
	}
	
	
	public static String createSaveFolder() {
		String home = System.getProperty("user.home");
		String OS = System.getProperty("os.name").toLowerCase();
		
		if (OS.contains("win")) {
			home = System.getenv("appdata");
		} else if (OS.contains("mac")) {
			home += "~/Library/Application Support";
		} else if (OS.contains("nix") || OS.contains("aix")) {
			home += "~/.";
		}
		
		
		File saveDir = new File(home);
		saveDir = new File(saveDir, root + "/Saves/" + Game.PersonNameGetter);
		
		if (!saveDir.exists()) {
			saveDir.mkdir();
			System.out.println("Creating Save Directory");
			
		}
		
		return saveDir.getAbsolutePath();
	}
	
	public static String createSaveFolder(String userName) {
		String home = System.getProperty("user.home");
		String OS = System.getProperty("os.name").toLowerCase();
		
		if (OS.contains("win")) {
			home = System.getenv("appdata");
		} else if (OS.contains("mac")) {
			home += "~/Library/Application Support";
		} else if (OS.contains("nix") || OS.contains("aix")) {
			home += "~/.";
		}
		
		
		File saveDir = new File(home);
		saveDir = new File(saveDir, root + "/Saves/" + userName);
		
		if (!saveDir.exists()) {
			saveDir.mkdir();
			System.out.println("Creating Save Directory");
			
		}
		
		return saveDir.getAbsolutePath();
	}
	
	private static boolean deleteSaveFile() {
		if(!checkFileExists()) {
			System.err.println("File: " + createDataFolder() + fileName + " does not exist.");
			
			return new File(createDataFolder()).delete();
		}
		
		File toDelete = new File(createDataFolder() + fileName);
		
		if(toDelete.canWrite()) {
			return toDelete.delete();
		}
		
	System.err.println("File: " + createDataFolder() + fileName + " is write protected!");
	return false;
	
	
	
	}
	
	static void deleteDir(File index){
		String[]entries = index.list();
		for(String s: entries){
		    File currentFile = new File(index.getPath(),s);
		    currentFile.delete();
		}
		index.delete();
	}
	
	public static boolean deleteCharacter(String userName) {
		
			if (!Game.getGame().gui.save1.equals("(Open)")) {
				Game.getGame().gui.saveSelected = Game.getGame().gui.save1;
			} else if (!Game.getGame().gui.save2.equals("(Open)")) {
				Game.getGame().gui.saveSelected = Game.getGame().gui.save2;
			} else if (!Game.getGame().gui.save3.equals("(Open)")) {
				Game.getGame().gui.saveSelected = Game.getGame().gui.save3;
			} else if (!Game.getGame().gui.save4.equals("(Open)")) {
				Game.getGame().gui.saveSelected = Game.getGame().gui.save4;
			
		}
		

		String characterFile = createSaveFolder(userName) + "\\";
		File toDelete = new File(characterFile);
		if (!toDelete.isDirectory()) {
			
			System.err.println("File: " + characterFile + " does not exist.");
			
			return new File(characterFile).delete();
		}
		
		
		
		deleteDir(toDelete);
		
		
		return true;
	}
	
	public static boolean deleteSaveFile(String fileName) {
		if(!checkFileExists(fileName)) {
			System.err.println("File: " + createSaveFolder() + fileName + " does not exist.");
			
			return new File(createSaveFolder()).delete();
		}
		
		File toDelete = new File(createSaveFolder() + fileName);
		
		if(toDelete.canWrite()) {
			System.err.println("WARNING: " + createSaveFolder() + fileName + " Was Deleted!");
			return toDelete.delete();
		}
		
	System.err.println("File: " + createSaveFolder() + fileName + " is write protected!");
	return false;
	
	
	
	}
	
	
	
}

