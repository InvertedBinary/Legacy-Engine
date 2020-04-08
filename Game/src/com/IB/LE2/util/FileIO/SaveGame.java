package com.IB.LE2.util.FileIO;

import com.IB.LE2.Boot;

public class SaveGame {

	public static boolean AutoSave = Boot.prefsBool("Disk", "AutoSave", false);
	
	final public static int TIME_TO_SAVE = 320;
	public static int AUTO_SAVE_TIMER = TIME_TO_SAVE;
	
	public static void update() {
		AutoSaveClock();
	}
	
	public static void AutoSaveClock() {
		if (!AutoSave) return;
		
		AUTO_SAVE_TIMER--;
		
		if (AUTO_SAVE_TIMER <= 0) {
			SaveLevel();
			AUTO_SAVE_TIMER = TIME_TO_SAVE;
		}
	}
	
	public static void SaveLevel() {
		if (Boot.isConnected) return; // only save if offline or host
		
		
	}
	
	
}
