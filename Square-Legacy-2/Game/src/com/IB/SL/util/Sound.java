package com.IB.SL.util;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import org.newdawn.easyogg.OggClip;

public class Sound {
	
	//Wavs
	public static String Spell1Path = "/sound/Items/Weapons/Spell1.wav";
	public static String Spell2Path = "/sound/Items/Weapons/Spell2.wav";
	public static String WalkingCobblestonePath = "/sound/World/WalkingCobblestone.wav";
	public static String WalkingCobbleStoneFastPath = "/sound/World/WalkingCobblestoneFast.wav";
	public static String WalkingGrassPath = "/sound/World/WalkingGrass.wav";
	public static String PotionPath = "/sound/Items/Consumables/Potion.wav";
	public static String ClickPath = "/sound/Menu/Click.wav";
	public static String BossSummonPath = "/sound/BossSummon.wav";
	public static String RockPath = "/sound/Items/Weapons/RockSmash.wav";
	public static String CopperGuardianSwordPath = "/sound/Items/Weapons/CopperGuardianSword.wav";

	/*public static String HopePath = "/sound/Hope.wav";
	public static String menuMusicPath = "/sound/menuMusic.wav";*/
	public static String walkingSandPath = "/sound/World/WalkingSand.wav";
	public static String walkingWaterPath = "/sound/World/WalkingWater.wav";
	public static String lavelUpPath = "/sound/Music/Lavel_Up.wav";
	public static String CoinPath = "/sound/Items/Consumables/Coin.wav";

	//public static String Explosion1Path = "/sound/Explosion.wav";
	public static String Explosion2Path = "/sound/World/Explosion2.wav";
	public static String Explosion3Path = "/sound/World/Explosion3.wav";
	public static String Explosion4Path = "/sound/World/Explosion4.wav";
	public static String Siphon1Path = "/sound/Mobs/Hostile/Siphon.wav";
	public static String PulsefireWandPath = "/sound/Items/Weapons/PulsefireWand.wav";
	public static String StygianWandPath = "/sound/Items/Weapons/Spell2.wav";

	public static String InvOpenPath = "/sound/Items/inventory_Open.wav";
	public static String InvClosePath = "/sound/Items/inventory_Close.wav";
	public static String InvAddPath = "/sound/Items/itemPickup.wav";
	
	//Ogg Volume
	public static double HopeVol = 0.8;
	public static double menuMusicVol = 0.7;
	/*public static double Spell1Vol = 0.8;
	public static double Spell2Vol = 0.8;
	public static double WalkingCobbleVol = 0.8;
	public static double WalkingCobbleFastVol = 0.8;
	public static double WalkingGrassVol = 0.8;
	public static double HealthPotionVol = 0.8;
	public static double ClickVol = 0.8;
	public static double BossSummonVol = 0.8;
	public static double RockVol = 0.8;
	public static double WalkingSandVol = 0.8;
	public static double WalkingWaterVol = 0.8;*/

	private static Clip clip;
	private static OggClip ogg;
	public static Clip clipM;
	
	//Oggs
	/*public static String Spell1Path = "sound/Spell1.ogg";
	public static String Spell2Path = "sound/Spell2.ogg";
	public static String WalkingCobblestonePath = "sound/WalkingCobblestone.ogg";
	public static String WalkingCobbleStoneFastPath = "sound/WalkingCobblestoneFast.ogg";
	public static String WalkingGrassPath = "sound/WalkingGrass.ogg";
	public static String HealthPotionPath = "sound/HealthPotion.ogg";
	public static String ClickPath = "sound/Click.ogg";
	//public static String BossSummonPath = "/sound/BossSummon.ogg";
	public static String RockPath = "sound/Rock.ogg";
	public static String walkingSandPath = "sound/WalkingSand.ogg";
	public static String walkingWaterPath = "sound/WalkingWater.ogg";*/

	public static Clip Spell2 = loadSound(Spell2Path, -8);
	public static Clip Spell1 = loadSound(Spell1Path, -8);
	public static Clip WalkingCobblestone = loadSound(WalkingCobblestonePath, 1);
	public static Clip WalkingCobblestoneFast = loadSound(WalkingCobbleStoneFastPath, 1);
	public static Clip WalkingGrass = loadSound(WalkingGrassPath, 1);
	public static Clip Potion = loadSound(PotionPath, -10);
	public static Clip Click = loadSound(ClickPath, -4);
	public static Clip lavel_up = loadSound(lavelUpPath, -20);
	//public static Clip  BossSummon= loadSound(BossSummonPath); N/A
//	public static Clip menuMusic = loadMusic(menuMusicPath, -18);
// static Clip Hope = loadMusic(HopePath, -6);
	public static Clip Rock = loadSound(RockPath, -14);
	public static Clip walkingSand = loadSound(walkingSandPath, -6);
	public static Clip walkingWater = loadSound(walkingWaterPath, -7);
	public static Clip CopperGuardianSword = loadSound(CopperGuardianSwordPath, -8);
	public static Clip coin = loadSound(CoinPath, -3);
	
	//public static Clip Explosion1 = loadSound(Explosion1Path, -3);
	public static Clip Explosion2 = loadSound(Explosion2Path, -5);
	public static Clip Explosion3 = loadSound(Explosion3Path, -3);
	public static Clip Explosion4 = loadSound(Explosion4Path, -8);
	public static Clip Siphon1 = loadSound(Siphon1Path, -6);
	
	public static Clip PulsefireWand = loadSound(PulsefireWandPath, -20);
	public static Clip StygianWand = loadSound(StygianWandPath, -5);
	public static Clip erie = loadSound("/sound/Mobs/Hostile/Erie.wav", -5);
	public static Clip VoidCrook = loadSound("/sound/Items/Weapons/VoidCrook.wav", -22);
	public static Clip Sword = loadSound("/sound/Items/Weapons/Sword_1.wav", -4);


	public static Clip horse_Breath = loadSound("/sound/Mobs/Neutral/horse_Breath.wav", -8);;
	public static Clip horse_Whinny = loadSound("/sound/Mobs/Neutral/horse_Whinny.wav", -20);;

	public static Clip InvOpen = loadSound(InvOpenPath, -14);
	public static Clip InvClose = loadSound(InvClosePath, -14);
	public static Clip InvAdd = loadSound(InvAddPath, -2);
	/*public static OggClip Spell1Ogg;
	public static OggClip Spell2Ogg;
	public static OggClip WalkingCobbleOgg;
	public static OggClip WalkingCobbleFastOgg;
	public static OggClip WalkingGrassOgg;
	public static OggClip HealthPotionOgg;
	public static OggClip ClickOgg;
	public static OggClip BossSummonOgg;
	public static OggClip RockOgg;
	public static OggClip walkingSandOgg;
	public static OggClip walkingWaterOgg;*/
	
	public static String HopePath = "sound/Music/Hope.ogg";
	public static String menuMusicPath = "sound/Music/The_Mysterious_Travler.ogg";
	public static String ValliantPath = "sound/Music/Valliant.ogg";
	public static String BossMusicPath = "sound/Music/Boss01.ogg";
	public static String VoidDungeonPath = "sound/Music/Void.ogg";
	public static String TheIcicleFallsPath = "sound/Music/The_Icicle_Falls.ogg";
	public static String WindWalkerPath = "sound/Music/Windwalker.ogg";
	public static String CallOfTheVoidPath = "sound/Music/Call of the Void.ogg";
	public static String TheMightyWillCrumblePath = "sound/Music/The Mighty will Crumble.ogg";
	public static String OasisSandsPath = "sound/Music/Oasis Sands.ogg";

	public static OggClip HopeOgg;
	public static OggClip menuMusOgg;
	public static OggClip Boss;
	public static OggClip VoidDungeon;
	public static OggClip Valliant;
	public static OggClip TheIcicleFalls;
	public static OggClip Windwalker;
	public static OggClip CallOfTheVoid;
	public static OggClip TheMightyWillCrumble;
	public static OggClip OasisSands;

	public static OggClip lastTrack;

	public static void loadOggs() {
		try {
			/*Spell1Ogg = new OggClip(Spell1Path);
			Spell2Ogg = new OggClip(Spell2Path);
			WalkingCobbleOgg = new OggClip(WalkingCobblestonePath);
			WalkingCobbleFastOgg = new OggClip(WalkingCobbleStoneFastPath);
			WalkingGrassOgg = new OggClip(WalkingGrassPath);
			HealthPotionOgg = new OggClip(HealthPotionPath);
			ClickOgg = new OggClip(ClickPath);
			//BossSummonOgg = new OggClip(BossSummonPath);
			RockOgg = new OggClip(RockPath);
			walkingSandOgg = new OggClip(walkingSandPath);
			walkingWaterOgg = new OggClip(walkingWaterPath);*/
			menuMusOgg = new OggClip(menuMusicPath);
			HopeOgg = new OggClip(HopePath);
			Boss = new OggClip(BossMusicPath);
			VoidDungeon = new OggClip(VoidDungeonPath);
			Valliant = new OggClip(ValliantPath);
			TheIcicleFalls = new OggClip(TheIcicleFallsPath);
			Windwalker = new OggClip(WindWalkerPath);
			CallOfTheVoid = new OggClip(CallOfTheVoidPath);
			TheMightyWillCrumble = new OggClip(TheMightyWillCrumblePath);
			OasisSands = new OggClip(OasisSandsPath);

			lastTrack = menuMusOgg;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void PlayOgg(OggClip oggFile, boolean repeat, double volume) {
		if (!oggFile.stopped()) oggFile.stop();
		oggFile.setGain((float) volume);
			if (repeat) {
				oggFile.loop();
			} else {
			oggFile.play();
			}
			ogg = oggFile;
	}
	
	public static OggClip returnMusic() {
		return ogg;
	}
	
	public static Clip returnSound() {
		return clip;
	}
	
	public static void StopOgg() {
		if (ogg != null)
		ogg.stop();
	}
	
	public static void pauseOgg() {
		if (ogg != null)
		ogg.pause();
	}
	
	public static void resumeOgg() {
		if (ogg != null)
		ogg.resume();
	}
	
	public static Clip loadSound(String path, float volume) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume);  
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return clip;
	}
	
	public static void Play(Clip clip, boolean repeat) {
		if (clip.isRunning()) clip.stop();
			clip.setFramePosition(0);
			if (repeat) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
			clip.start();
			}
	}
	
	public static void Stop() {
		clip.loop(0);
		clip.stop();
	}
	

	
	
	
	
	
	
	
	public static Clip loadMusic(String path, float volume) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			clipM = AudioSystem.getClip();
			clipM.open(audioInputStream);
			FloatControl gainControl = (FloatControl) clipM.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume);  
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return clipM;
	}
	
	
	
	
	

	
	
	public static void PlayMusic(Clip clipM, boolean repeat) {
		if (clipM.isRunning()) clipM.stop();
			clipM.setFramePosition(0);
			clipM.stop();
			if (repeat) {
				clipM.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
			clipM.start();
			}
	}
	
	
	
	public static void StopMusic() {
		ogg.stop();
	}

	public static void switchMusic(OggClip ogg, float volume) {
		if (ogg != returnMusic()) {
			Sound.StopMusic();
			Sound.PlayOgg(ogg, true, volume);
		}
	}
	
	
	public static void toggleSound(Clip clip, boolean repeat) {
		if (clip  != returnSound()) {
			Sound.Stop();
			Sound.Play(clip, repeat);
		}
	}
	
	
}










//////////////////////////////////////////////////////////////////////
/**
* Old Play Method
*/
/*public static void Play(String path, float volume, boolean repeat) {
	
	try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(volume);  
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	  
	
	if (repeat)
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	else
		clip.start();
}

*/

	/*public static void Spell2(String path) {
		try {
			if (Mouse.getButton() == 1 /*&& fireRate <= 0)  {
			AudioInputStream audio = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				clip.loop(0);
				clip.flush();	
			
		}} catch (Exception e) {
			System.out.println("Check Code And Path For S2" + path + "\n");
			e.printStackTrace();
		}
	
	}*/
	

	
	/*public static void Spell1(String path) {
		try {
			if (Mouse.getButton() == 3 /*&& fireRate <= 0)  {
			AudioInputStream audio = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				clip.loop(0);
				clip.flush();

				
			
			
		}} catch (Exception e) {
			System.out.println("Check Code And Path For S1" + path + "\n");
			e.printStackTrace();
		}
	
	}
	public static void WalkingCobblestone(String path) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				clip.loop(0);
				clip.flush();
				
			
			
			} catch (Exception e) {
			System.out.println("Check Code And Path For Walking Cobble" + path + "\n");
			e.printStackTrace();
		}
	
	}
	
	public static void SpellTracker(String path) {
		try {
			if (fireRate == 0)  {
			AudioInputStream audio = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				clip.loop(0);
				clip.flush();

				
			
			
		}} catch (Exception e) {
			System.out.println("Check Code And Path For S2" + path + "\n");
			e.printStackTrace();
			fireRate = WizardProjectile.FIRE_RATE;
		}
	
	}
	
	public static void WalkingCobblestoneFast(String path) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				clip.loop(0);
				clip.flush();
				
			
			
			} catch (Exception e) {
			System.out.println("Check Code And Path For Walking Cobble" + path + "\n");
			e.printStackTrace();
		}
	
	}
	
	public static void WalkingGrass(String path) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				clip.loop(0);
				clip.flush();
			
			
			} catch (Exception e) {
			System.out.println("Check Code And Path For Walking Cobble" + path + "\n");
			e.printStackTrace();
		}
	
	}
	public static void HealthPotion(String path) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				clip.loop(0);
				clip.flush();
			
			
			} catch (Exception e) {
			System.out.println("Check Code And Path For HealthPotion" + path + "\n");
			e.printStackTrace();
		}
	
	}
	public static void Click(String path) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				clip.loop(0);
				clip.flush();
			
			
			} catch (Exception e) {
			System.out.println("Check Code And Path For Click" + path + "\n");
			e.printStackTrace();
		}
	
	}
	
	public static void BossSummon(String path) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				clip.loop(0);
				clip.flush();
			
			
			} catch (Exception e) {
			System.out.println("Check Code And Path For BossSummon" + path + "\n");
			e.printStackTrace();
		}
	
	}
	public static void Rock(String path) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(Sound.class.getResource(path));
			Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				clip.loop(0);
				clip.flush();
			
			
			} catch (Exception e) {
			System.out.println("Check Code And Path For Rock" + path + "\n");
			e.printStackTrace();
		}
	
	}
	
	
	
}*/

