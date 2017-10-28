package com.IB.SL.level.interactables;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.effects.WellRested;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.spawner.WallParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.font8x8;
import com.IB.SL.util.Debug;

public class Interactable_Bed extends Interactable {
	transient public static Sprite sprite;
	
	transient public int id;
	transient private font8x8 font = new font8x8();
	
	public Interactable_Bed(int x, int y, int id) {
		this.x = x << Game.TILE_BIT_SHIFT;
		this.y = y << Game.TILE_BIT_SHIFT;
		this.id = id;
		this.mobhealth = 1000;
		this.invulnerable = true;
		this.ySort = true;
		//font.fixBg = true;
	}
	
	transient int xpa = 0; int ypa = 0;
	public void drawParticles(Entity e, double rate) {
		if ((int)x < (int)e.getX() + 20) xpa+= this.speed;
		if ((int)y < (int)e.getY() + 20) ypa+= this.speed;
		
		if (xpa == x) {
			xpa = 0;
		}
		if (ypa == y) {
			ypa = 0;
		}
		
		level.add(new WallParticleSpawner((int)((int)e.getX() + xpa), (int)((int)e.getY() + ypa), 100, 1, level));
	}
	
	
	
	List<PlayerMP> players; 
	public void update() {
		players = level.getPlayersFixed((int)x + 8, (int)y + 8, 15);
		
		if (players.size() > 0) {
			for (int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				if (p.input.generalActivator && level.night) {
				if (p.inventory.removeItemByName("Room Key: " + id)) {
					
					p.mobhealth = p.maxhealth;
					p.mana = p.maxmana;
					p.stamina = p.maxstamina;
					
					level.day = true;
					level.night = false;
					level.time = 0;
					level.brightness = 50;
					p.effects.addEffect(new WellRested(p, 8200));

				}
				else
					System.out.println("Need a room key id: " + id);
				}
			}
		}
	}
	
	public void render(Screen screen) {
		if (Game.getGame().gameState == gameState.INGAME_A) {
			Debug.drawRect(screen, (int)x, (int)y, 32, 32, 0xFF00FF, true);
		}
		sprite = Sprite.BlueBed;
		screen.renderMobSpriteUniversal((int)x, (int)y - 7, sprite);
		if (players != null) {
			if (players.size() > 0) {
				String st = "Press F to Sleep";
				String str = "Can Only Sleep At Night";
				
				if (!level.night) {
					font.render((int)x - (str.length() * 8) / 2 + 8 + ((st.length())), (int)y - 24, -2, str, screen, true, true);
				} else {
					font.render((int)x - (st.length() * 8) / 2 + 8 + ((st.length()) / 2), (int)y - 24, -2, st, screen, true, true);					
				}
			}
		}
	}
	 
}
