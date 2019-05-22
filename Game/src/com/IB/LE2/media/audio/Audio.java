package com.IB.LE2.media.audio;

import java.io.InputStream;
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
	
	private static int aModel = SoundSystemConfig.ATTENUATION_ROLLOFF;
	private static float rFactor = SoundSystemConfig.getDefaultRolloff();
	
	private static HashMap<String, AudioClip> files = new HashMap<>();

	public static AudioClip previous_music = null;
	public static String cur_music_source = "";
	
	public static void Initialize() {
		ConfigureLibrary();
		LoadCodecs();
		CreateSystem();
		
		LoadSources("/XML/Audio/PersistentSources.xml");
	}
	
	public static void SwapSources(String path) {
		UnloadSources(prev_tag_path);
		LoadSources(path);
	}

	private static void LoadSources(String path) {
		InputStream fXmlFile = Audio.class.getResourceAsStream(path);
		DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("source");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String name = eElement.getAttribute("name");
					String file = eElement.getAttribute("file");
					float volume = Float.parseFloat(eElement.getAttribute("vol"));
					
					files.put(name, new AudioClip(name, file, volume));
					system.loadSound(file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		prev_tag_path = path;
	}
	
	private static void UnloadSources(String path) {
		InputStream fXmlFile = Audio.class.getResourceAsStream(path);
		DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("source");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String name = eElement.getAttribute("name");
					String file = eElement.getAttribute("file");

					system.unloadSound(file);
					files.remove(name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void ConfigureLibrary() {
		try {
			SoundSystemConfig.addLibrary(LibraryJavaSound.class);
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
		try {
			system = new SoundSystem(LibraryJavaSound.class);
		} catch (SoundSystemException e) {
			e.printStackTrace();
		}
	}
	
	public static void Play(String name, double x, double y, double z, boolean loop) {
		AudioClip ac = files.get(name);
		if (ac == null)
			return;
		
		if (ac.path.endsWith(".mid"))
			Audio.cur_music_source = name;

		system.newSource(false, name, ac.path, loop, 0, 0, 0, aModel, rFactor);
		system.setVolume(name, ac.volume);
		system.setPosition(name, (float)x, (float)y, (float)z);
		
		system.play(name);
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
