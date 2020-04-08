package com.IB.LE2.media.audio;

public class AudioClip {
	
	public String name, path;
	public float volume;

	public AudioClip(String name, String path) {
		this(name, path, 1.0f);
	}
	
	public AudioClip(String name, String path, float volume) {
		this.name = name;
		this.path = path;
		this.volume = volume;
	}
	
}
