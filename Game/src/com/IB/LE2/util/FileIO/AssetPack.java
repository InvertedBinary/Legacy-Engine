package com.IB.LE2.util.FileIO;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import com.IB.LE2.input.UI.components.UI_Font;
import com.IB.LE2.media.audio.Audio;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;

public class AssetPack {

	String path;
	
	String name;
	String description;
	String version;
	
	public AssetPack(String name) {
		Disk.InitializeDirectoryStructure();
		
		this.path = (Disk.AppDataDirectory + "/mods/" + name);

		try {
			Ini ini = new Ini(new File(path + "/tagsinfo.ini"));
			Preferences info = new IniPreferences(ini).node("ModInfo");
			this.name = info.get("Name", "An LE2 Mod");
			this.description = info.get("Description", "Undefined");
			this.version = info.get("Version", "-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		InitializePack();
	}
	
	public void InitializePack() {
		Audio.LoadSounds(path + "/Audio/");
		SpriteSheet.LoadTags(path + "/Textures/SheetBatch.xml");
		Sprite.LoadTags(path + "/Textures/SpriteBatch.xml");
	}
	
	public HashMap<String, String> CombDirectory() throws IOException {
		HashMap<String, String> results = new HashMap<>();
		
	    final Path rootDir = Paths.get(path);

	    Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path path, BasicFileAttributes mainAtts) throws IOException {
	        	if (mainAtts.isRegularFile()) {
	        		if (path.toString().endsWith(".xml") || path.toString().endsWith(".tmx")) {
	        			String fileName = path.getFileName().toString();
	        			String filePath = path.toString();
	        			fileName = fileName.substring(0, fileName.lastIndexOf('.'));
	        			Path parent = path.getParent();
	        			if (parent.getParent().endsWith("Levels")) {
	        				filePath = parent.toString();
	        			} else if (parent.endsWith("Menu")) {
	        				filePath = filePath.substring(0, filePath.lastIndexOf('.'));
	        			} else if (parent.endsWith("Font")) {
	        				UI_Font.LoadFont(filePath);
	        			}
	        			
	        			results.put(fileName, filePath);
	        		}
	        	}
	        			
	            return FileVisitResult.CONTINUE;
	        }

	        @Override
	        public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
	            exc.printStackTrace();
	            return path.equals(rootDir) ? FileVisitResult.TERMINATE:FileVisitResult.CONTINUE;
	        }
	    });

		
		return results;
	}

}
