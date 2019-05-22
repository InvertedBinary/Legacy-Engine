package com.IB.LE2.world.level.interactables;

import java.util.List;

import com.IB.LE2.Boot;
import com.IB.LE2.media.graphics.Font8x;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.Debug;
import com.IB.LE2.util.VARS;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.entity.mob.PlayerMP;
import com.IB.LE2.world.level.TileCoord;

public class Location_Shrine extends Interactable {
	transient public static Sprite sprite;
	
	transient private Font8x font = new Font8x();
	transient TileCoord p_spawn;
	private int sx = 0, sy = 0;
	
	public void onLoad(Entity e) {
		this.sx = ((Location_Shrine)e).sx;
		this.sy = ((Location_Shrine)e).sy;
		this.p_spawn = new TileCoord(sx / 16, sy / 16);
	}
	
	public Location_Shrine(int x, int y) {
		this.setX(x << VARS.TILE_BIT_SHIFT);
		this.setY(y << VARS.TILE_BIT_SHIFT);
		//p_spawn = t;
		this.mobhealth = 1000;
		this.invulnerable = true;
		this.ySort = true;
	}
	public Location_Shrine(int x, int y, TileCoord t) {
		this.setX(x << VARS.TILE_BIT_SHIFT);
		this.setY(y << VARS.TILE_BIT_SHIFT);
		p_spawn = t;
		sx = t.x();
		sy = t.y();
		this.mobhealth = 1000;
		this.invulnerable = true;
		this.ySort = true;
	}
	
	List<PlayerMP> players;
	public void update() {
		players = level.getPlayersFixed((int) x() + 17, (int) y() + 16, 24);

		if (players.size() > 0) {
			for (int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				if (p.input.generalActivator) {
					Boot.get().playerRespawn = new TileCoord(52, 72);
					Boot.get().playerRespawn = p_spawn;
					System.out.println("Set player spawn location to: " + Boot.get().playerRespawn.x() + ", " + Boot.get().playerRespawn.y());
				}
			}
		}
	}

	public void render(Screen screen) {
		if (Boot.get().devModeOn) {
			Debug.drawRect(screen, (int)x(), (int)y(), 32, 32, 0xFF00FF, true);
		}
		sprite = Sprite.loc_shrine;
		screen.renderMobSpriteUniversal((int)x(), (int)y() - 16, sprite);
		if (players != null) {
			if (players.size() > 0) {
				String st = "Press F to Set Spawn";
				font.render((int)x() - ((st.length() * 8) / 2) + 20, (int)y() - 39, 0xffFFFFFF, st, screen, true, true);
			}
		}
	}
	
}
