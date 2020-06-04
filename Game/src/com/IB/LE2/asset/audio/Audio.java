package com.IB.LE2.asset.audio;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.IB.LE2.Boot;
import com.IB.LE2.world.entity.Entity;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOgg;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;

public class Audio {

	private static SoundSystem system;
	private static String prev_tag_path = "";
	
	private static HashMap<String, AudioClip> files = new HashMap<>();

	public static AudioClip previous_music = null;
	public static String cur_music_source = "";
	
	public static void Initialize() {
		ConfigureLibrary();
		LoadCodecs();
		CreateSystem();
	}
	
	public static void SwapSources(String path) {
		UnloadSounds(prev_tag_path);
		LoadSounds(path);
	}

	public static ArrayList<AudioClip> ReadSounds(String path) {
		ArrayList<AudioClip> result = new ArrayList<>();
		DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
			Document doc = dBuilder.parse(path + "PersistentSources.xml");
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("source");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String name = eElement.getAttribute("name");
					String file = path + "assets/" + eElement.getAttribute("file");
					file = file.replace('\\', '/');
					float volume = Float.parseFloat(eElement.getAttribute("vol"));
					
					result.add(new AudioClip(name, file, volume));
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("The system could not find Audio Sources for this asset pack.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		prev_tag_path = path;
		return result;
	}
	
	public static void UnloadSounds(String path) {
		ArrayList<AudioClip> l = ReadSounds(path);
		for (AudioClip clip : l)
			system.unloadSound(clip.name);
	}
	
	public static void LoadSounds(String path) {
		ArrayList<AudioClip> l = ReadSounds(path);
		try {
			for (AudioClip clip : l) {
				files.put(clip.name, clip);
				system.loadSound(new URL("file:///" + clip.path), clip.name);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private static void ConfigureLibrary() {
		try {
            //SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			//SoundSystemConfig.addLibrary(LibraryJOALOpenAL.class);
			SoundSystemConfig.addLibrary(LibraryJavaSound.class);
			SoundSystemConfig.setSoundFilesPackage("");
		} catch (SoundSystemException e) {
			Boot.log("An exception was thrown choosing a sound library!", "Audio.java", true);
		}
	}
	
	private static void LoadCodecs() {
		try {
			SoundSystemConfig.setCodec("wav", CodecWav.class);
			SoundSystemConfig.setCodec("jogg", CodecJOgg.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
		} catch (SoundSystemException e) {
			Boot.log("An exception was thrown loading audio codecs!", "Audio.java", true);
		}
	}
	
	private static void CreateSystem() {
		system = new SoundSystem();
	}
	
	public static void Play(String name, double x, double y, double z, boolean loop) {
		int aModel = SoundSystemConfig.ATTENUATION_ROLLOFF;
		float rFactor = SoundSystemConfig.getDefaultRolloff();
		
		AudioClip ac = files.get(name);
		if (ac == null)
			return;
		
		if (ac.path.endsWith(".mid"))
			Audio.cur_music_source = name;

		/*try {
			//system.newSource(false, name, new URL("file:///" + ac.path), name + ".ogg", loop, (float)x, (float)y, (float)z, aModel, rFactor);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}*/
		system.setVolume(name, ac.volume);
		//system.play(name);
		
        //system.quickPlay(true, ac.path, false, (int)x, (int)y, (int)z, aModel, rFactor);
		try {
			system.quickPlay(true, new URL("file:///" + ac.path), name + ".ogg", false, (float)x, (float)y, (float)z, aModel, rFactor);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void Play(String name, double x, double y, double z) {
		Play(name, x, y, z, false);
	}
	
	public static void Play(String name, Entity e) {
		Play(name, e.x(), e.y(), 0);
	}
	
	public static void Play(String name) {
		Play(name, 0, 0, 0);
	}
	
	public static void PlayMusic(String name, boolean loop) {
		AudioClip ac = files.get(name);
			if (ac == null)
				return;
		
		Audio.previous_music = new AudioClip(name, ac.path);
		Audio.cur_music_source = name;
		Play(name, 0, 0, 0, loop);
	}
	
	public static void PlayMusic(String name, String source, boolean loop) {
		Audio.previous_music = new AudioClip(name, source);
		Audio.cur_music_source = name;
		system.backgroundMusic(name, source, loop);
	}
	
	public static void PlayMusic(String name, String source) {
		PlayMusic(name, source, false);
	}
	
	public static void MoveListener(double x, double y, double z) {
		system.moveListener((float)x, (float)y, (float)z);
	}
	
	public static void StopMusic() {
		system.stop(Audio.cur_music_source);
	}
	
	public static void Close() {
		system.cleanup();
	}
	
	public static void SetVolume(float level) {
		system.setMasterVolume(level);
	}
	
	
		/*boolean priority = false;
		String sourcename = "Source 1";
		String filename = "Oasis Sands.ogg";
		boolean loop = false;
		float x = 0;
		float y = 0;
		float z = 0;
		int aModel = SoundSystemConfig.ATTENUATION_ROLLOFF;
		float rFactor = SoundSystemConfig.getDefaultRolloff();
		
		system.newSource(priority, sourcename, filename, loop, x, y, z, aModel, rFactor);
		
		system.setMasterVolume(0.25f);
		system.setVolume(sourcename, 0.5f);
		
		system.moveListener( 0, 0, 0); 
		system.setPosition(sourcename, 0, 0, 0);
		system.play(sourcename);
		
		system.loadSound("explosion.wav");*/
	
}
