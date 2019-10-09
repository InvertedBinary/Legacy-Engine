package com.IB.LE2.util.FileIO;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

public class Assets {

	private static HashMap<String, AssetPack> packs = new HashMap<>();
						//NAME
	private static HashMap<String, String> assets = new HashMap<>();
						//ID, 	  PATH
	
	public static void ExecuteLoadOrder() {
		try {
			Ini ini = new Ini(new File(Disk.AppDataDirectory + "/mods/mods.ini"));
			Preferences order = new IniPreferences(ini).node("LoadOrder");
			String mods = order.get("mods", "");
			if (mods.isEmpty()) {
				JOptionPane.showMessageDialog(null,
					    "Game files are missing or corrupt.\nReinstall and try again.",
					    "Dirty Disk Error",
					    JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			
			List<String> items = Arrays.asList(mods.split("\\s*,\\s*"));
			for (String mod : items) {
				LoadPack(mod);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void LoadPack(String name) {
		AssetPack ap = new AssetPack(name);
		try {
			RegisterResources(ap.CombDirectory());
		} catch (IOException e) {
			e.printStackTrace();
		}
		packs.put(name, ap);
	}
	
	public static void RegisterResources(HashMap<String, String> resources) {
		for (String id : resources.keySet()) {
			RegisterResource(id, resources.get(id));
		}
	}
	
	public static void RegisterResource(String name, String path) {
		System.out.println("REGISTERING [" + name + "] :: TO PATH: " + path);
		assets.put(name, path);
	}
	
	public static AssetPack getPack(String name) {
		return packs.get(name);
	}
	
	public static String get(String name) {
		return assets.get(name);
	}
}
