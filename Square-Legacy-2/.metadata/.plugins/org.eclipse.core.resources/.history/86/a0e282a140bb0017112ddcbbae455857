package com.IB.SL.level.interactables;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.font8x8;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Debug;

public class Location_Shrine extends Interactable {
	transient public static Sprite sprite;
	
	transient private font8x8 font = new font8x8();
	transient TileCoord p_spawn;
	private int sx = 0, sy = 0;
	
	public void onLoad(Entity e) {
		this.sx = ((Location_Shrine)e).sx;
		this.sy = ((Location_Shrine)e).sy;
		this.p_spawn = new TileCoord(sx / 16, sy / 16);
	}
	
	public Location_Shrine(int x, int y) {
		this.x = x << Game.TILE_BIT_SHIFT;
		this.y = y << Game.TILE_BIT_SHIFT;
		//p_spawn = t;
		this.mobhealth = 1000;
		this.invulnerable = true;
		this.ySort = true;
	}
	public Location_Shrine(int x, int y, TileCoord t) {
		this.x = x << Game.TILE_BIT_SHIFT;
		this.y = y << Game.TILE_BIT_SHIFT;
		p_spawn = t;
		sx = t.x();
		sy = t.y();
		this.mobhealth = 1000;
		this.invulnerable = true;
		this.ySort = true;
	}
	
	
	List<PlayerMP> players;
	public void update() {
		players = level.getPlayersFixed((int) x + 17, (int) y + 16, 24);

		if (players.size() > 0) {
			for (int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				if (p.input.generalActivator) {
					Game.getGame().playerRespawn = new TileCoord(52, 72);
					Game.getGame().playerRespawn = p_spawn;
					System.out.println("Set player spawn location to: " + Game.getGame().playerRespawn.x() + ", " + Game.getGame().playerRespawn.y());
				}
			}
		}
	}

	public void render(Screen screen) {
		if (Game.getGame().gameState == gameState.INGAME_A) {
			Debug.drawRect(screen, (int)x, (int)y, 32, 32, 0xFF00FF, true);
		}
		sprite = Sprite.loc_shrine;
		screen.renderMobSpriteUniversal((int)x, (int)y - 16, sprite);
		if (players != null) {
			if (players.size() > 0) {
				String st = "Press F to Set Spawn";
				font.render((int)x - ((st.length() * 8) / 2) + 20, (int)y - 39, 0xffFFFFFF, st, screen, true, true);
			}
		}
	}
	
}
