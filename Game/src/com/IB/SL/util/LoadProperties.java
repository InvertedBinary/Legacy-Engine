package com.IB.SL.util;

import java.io.File;
import java.io.Serializable;

import com.IB.SL.Game;
import com.IB.SL.level.TileCoord;

public class LoadProperties implements Serializable {
	public transient static String root = "/SquareLegacy";
	public transient static String SystemUsername = System.getProperty("user.name");
	public transient static String basePath = null;
	
	public boolean multienabled = false;
	public boolean autoSave = true;
	public int psX = 52, psY = 72;
	
	public void savePrefs(Game game) {
		autoSave = game.autoSave;
		psX = game.playerRespawn.x();
		psY = game.playerRespawn.y();

		SaveGame.save(this, SaveGame.prefsFileName);
	}
	
	public void loadPrefs(Game game) {
		LoadProperties temp = SaveGame.loadPrefs();

		game.autoSave = temp.autoSave;
		autoSave = game.autoSave;
		game.playerRespawn = new TileCoord(temp.psX / 16, temp.psY / 16);
		psX = game.playerRespawn.x();
		psY = game.playerRespawn.y();
	}

	/*public static String getOsName() {

		if (OS == null) {
			OS = System.getProperty("os.name").toLowerCase();
		}
		return OS;
	}
	
	public static boolean isWindows() {
		return getOsName().startsWith("windows");
	}
	
	public static boolean isMac() {
		return getOsName().startsWith("mac");
	}
	
	public static boolean isSolaris() {
		return getOsName().startsWith("sunos");
	}
	
	public static boolean isUnix() {
		return getOsName().startsWith("nix") || getOsName().startsWith("nux")
				|| getOsName().startsWith("aix");
	}
	
	public void decidePath() {
	if (isWindows()) {
		basePath = "C:/ProgramData/SquareLegacy";
		File baseFilePath = new File(basePath);

		/**
		 * Default: basePath = "/Users/" + SystemUsername +
		 * "/Library/Application Support/SquareLegacy";
		 
	} else if (isMac()) {
		basePath = "/Applications/SquareLegacy";
		File baseFilePath = new File(basePath);
		if (!baseFilePath.canWrite()) {
			basePath = "/Users/" + SystemUsername
					+ "/Library/Application Support/SquareLegacy";		
			File baseFilePath1 = new File(basePath);
			if (!baseFilePath1.canWrite()) {
				basePath = "/Library/SquareLegacy";
				File baseFilePath2 = new File(basePath);
				if (!baseFilePath2.canWrite()) {
					JOptionPane
							.showMessageDialog(
									null,
									"System OS Not Supported Or, Try Running As Administrator",
									"Path Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		}
		/**
		 * basePath = "\home\" + SystemUsername + "\SquareLegacy";
		 
	} else if (isUnix()) {
		basePath = "/home/" + SystemUsername + "/SquareLegacy";
		File baseFilePath = new File(basePath);
		if (!baseFilePath.canWrite()) {
			JOptionPane
					.showMessageDialog(
							null,
							"System OS Not Supported Or, Try Running As Administrator",
							"Path Error", JOptionPane.ERROR_MESSAGE);
		}
		/**
		 * No Path For Solaris
		 
	} else if (isSolaris()) {
		basePath = "C:/ProgramData/SquareLegacy";
		File baseFilePath = new File(basePath);
		if (!baseFilePath.canWrite()) {
			JOptionPane
					.showMessageDialog(
							null,
							"System OS Not Supported Or, Try Running As Administrator",
							"Path Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	}
	*/
	
	public static void createDataFolder() {
		if (basePath == null) {
		String home = System.getProperty("user.home");
		String OS = System.getProperty("os.name").toLowerCase();
		
		if (OS.contains("win")) {
			home = System.getenv("appdata");
		} else if (OS.contains("mac")) {
			home += "~/Applications/";
		} else if (OS.contains("nix")  || OS.contains("aix")) {
			home += "~/.";
		}
		
		File dir = new File(home);
		dir = new File(dir, root);
		
		if (!dir.exists()) {
			dir.mkdir();
			System.out.println("Creating directory");
			
		}
		
		basePath = dir.getAbsolutePath();
		}
	}
	
	
	
}



































