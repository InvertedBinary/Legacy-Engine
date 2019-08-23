package com.IB.LE2.util.FileIO;

import java.io.IOException;
import java.util.HashMap;

public class Assets {

	private static HashMap<String, AssetPack> packs = new HashMap<>();
						//NAME
	private static HashMap<String, String> assets = new HashMap<>();
						//ID, 	  PATH
	
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
