package com.IB.LE2.input.UI.menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.xml.sax.helpers.DefaultHandler;

import com.IB.LE2.Boot;
import com.IB.LE2.input.Commands;
import com.IB.LE2.input.UI.UI_Manager;
import com.IB.LE2.input.UI.components.basic.UI_Clickable;
import com.IB.LE2.input.UI.components.basic.UI_Container;
import com.IB.LE2.input.UI.components.basic.UI_Keylistener;
import com.IB.LE2.input.UI.components.basic.UI_Root;
import com.IB.LE2.input.hardware.Keyboard;
import com.IB.LE2.media.audio.Audio;
import com.IB.LE2.media.graphics.Font16x;
import com.IB.LE2.media.graphics.Font8x;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;

public abstract class UI_Menu implements KeyListener {
	
	public Sprite bg;
	public SpriteSheet s_bg;
	public UI_Container ui;
	public Font8x font8x8 = new Font8x();
	public Font16x font = new Font16x();
	public int x;
	public int y;
	public boolean enabled = false;
	public static int index = 0;
	
	public void init(int x, int y) {
		this.x = x;
		this.y = y;
		
		if (ui == null)
			ui = new UI_Container();
	}
	
	public void UpdateUnloaded() { }
	public void update() { }
	public void render(Screen screen) {	}
	
	public void continueGame() {
		//UI_Manager.UnloadCurrent();
		if (!Boot.get().getLevel().players.contains(Boot.get().getPlayer())) {
			Boot.get().getPlayer().removed = false;
			Boot.get().getLevel().add(Boot.get().getPlayer());
			//Boot.get().getLevel().loadLua();
		}
		
		Boot.getPlayer().ShowHUD();
	}
	
	public void SetVolume(float level) {
		System.out.println("Setting TO: " + level);
		Audio.SetVolume(level);
		
		//PlayPrevious();
	}
	
	public void PlayMusic(String name, String file) {
		Audio.PlayMusic(name, file, true);
	}
	
	public void PlayPrevious() {
		if (Audio.previous_music == null)
			Audio.StopMusic();
		else
			Audio.PlayMusic(
				Audio.previous_music.name, 
				Audio.previous_music.path
			);
	}
	
	public int progressBar(int total_frames, double max_val, double current_val) {
		double percent = current_val / max_val;
		int frame = (int)(percent * total_frames);
		
		return (total_frames - 1) - frame;
	}
	
	public void RunCommand(String text) {
		Commands.Execute(text, Boot.get().getPlayer());
	}
	
	public void addUI(UI_Root component) {
		this.ui.addUI(component);
	}
	
	public void SetFocused(String id) {
		UI_Root element = GetElementById(id);
		if (element instanceof UI_Clickable) {
			UI_Manager.focused = (UI_Clickable)element;
			element.SetFocused(true);
		}
	}
	
	public UI_Root GetElementById(String id) {
		for (UI_Root e : ui.getAll()) {
			if (e.GetID().equals(id))
				return e;
		}
		
		return null;
	}
	
	public boolean ElementExists(String id) {
		return GetElementById(id) != null;
	}
	
	public String GetElementText(String id) {
		if (ElementExists(id))
			return GetElementById(id).GetText();
		else return "";
	}
	
	public void SetElementText(String id, String text) {
		if (ElementExists(id))
			GetElementById(id).SetText(text);
	}
	
	public void SuspendWorldInput() {
		Boot.get().getPlayer().input.suspendInput();
	}
	
	public void ResumeWorldInput() {
		Boot.get().getPlayer().input.resumeInput();
	}
	
	public Keyboard getKey() {
		return Boot.get().key;
	}
	
	public void OnLoad() {
		
	}
	
	public void OnUnload() {
		
	}
	
	public Sprite getBG() {
		return bg;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (ui != null)
			for (UI_Keylistener element : ui.getFields()) {
				element.KeyPressed(e);
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("Key Released: " + e.getKeyChar());
	}
	
}
