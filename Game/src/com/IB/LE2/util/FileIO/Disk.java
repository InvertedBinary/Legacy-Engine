package com.IB.LE2.util.FileIO;

import java.io.File;

import com.IB.LE2.Boot;

public class Disk {
	
	private static final String AppDataRoot = "/" + Boot.prefsStr("Disk", "AppDataDirectoryRoot", "LE2Mod");
	public static final String[] subdirs = { "/bin", "/mods" };

	public static final File AppDataDirectory = AppDataDirectory();
	
	private static File AppDataDirectory() {
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
		dir = new File(dir, AppDataRoot);

		if (!dir.exists()) {
			dir.mkdir();
			System.out.println("Creating parent directory");
		}
		
		InitializeDirectoryStructure(dir.getAbsolutePath());
		
		return dir;
	}
	
	private static void InitializeDirectoryStructure(String appdatadir) {
		for (String p : subdirs) {
			File dir = new File(appdatadir + p + "/");
			if (!dir.exists()) {
				dir.mkdirs();
				System.out.println("... Subdirectory \"" + p + "\" created.");
			}
		}
	}
	
	public static void DeleteDir(File index) {
		String[] entries = index.list();
		for (String s : entries) {
			File currentFile = new File(index.getPath(), s);
			currentFile.delete();
		}
		index.delete();
	}

}
