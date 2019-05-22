package com.IB.LE2.world.level.interactables;

import java.util.List;
import java.util.Random;

import com.IB.LE2.Boot;
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.util.Debug;
import com.IB.LE2.util.VARS;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.emitter.WallParticleSpawner;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.level.Level;

public class Teleporter extends Interactable {
	transient Random dropChance = new Random();
	transient int sx = 0, sy = 0;
	transient int dx, dy;

	transient boolean isRandom;
	transient public static Sprite sprite;

	transient private AnimatedSprite anim_Teleport = new AnimatedSprite(SpriteSheet.anim_Teleporter, 32, 32, 4);

	transient private AnimatedSprite animSprite = anim_Teleport;

	public Teleporter(int x, int y) {
		this.setX(x << VARS.TILE_BIT_SHIFT);
		this.setY(y << VARS.TILE_BIT_SHIFT);
		this.isRandom = true;
		this.mobhealth = 1000;
		this.invulnerable = true;
	}

	public Teleporter(int x, int y, int dx, int dy) {
		this.setX(x << VARS.TILE_BIT_SHIFT);
		this.setY(y << VARS.TILE_BIT_SHIFT);
		this.dx = dx << VARS.TILE_BIT_SHIFT;
		this.dy = dy << VARS.TILE_BIT_SHIFT;
		this.isRandom = false;
		this.mobhealth = 1000;
		this.invulnerable = true;
	}

	int xpa = 0;
	int ypa = 0;

	public void drawParticles(Entity e, double rate) {
		if ((int) x() < (int) e.x() + 20) xpa += this.speed;
		if ((int) y() < (int) e.y() + 20) ypa += this.speed;

		if (xpa == x()) {
			xpa = 0;
		}
		if (ypa == y()) {
			ypa = 0;
		}

		// System.out.println("TRUE " + xpa + "," + ypa);
		level.add(new WallParticleSpawner((int) ((int) e.x() + xpa), (int) ((int) e.y() + ypa), 100, 1, level));
	}

	public void update() {
		animSprite.update();
		// List<Entity> entities = level.entities;
		List<Player> players = level.getPlayers(this, 60);

		/*
		 * for (int i = 0; i < players.size(); i++) { if (players.size() > 0) { //
		 * drawParticles(players.get(i), 2); } }
		 */
		Player player = Boot.get().getLevel().getClientPlayer();

		if (PlayerTele(x(), y(), level, player)) {

			if (isRandom) {
				/*while (!level.returnTileXY(sx, sy).toString().equals(level.tile.Wood.toString())
						&& !level.returnTileXY(sx, sy).toString().equals(level.tile.CobbleStone.toString())) {
					sx = (dropChance.nextInt((1024 - 1) + 1) + 1);
					sy = (dropChance.nextInt((1024 - 1) + 1) + 1);
				}*/
				player.setX(sx << VARS.TILE_BIT_SHIFT);
				player.setY(sy << VARS.TILE_BIT_SHIFT);
				sx = 0;
				sy = 0;
				// remove();
			} else {
				player.setX(dx);
				player.setY(dy);
			}
		}
	}
	/*
	 * if (EntityTele(x,y, level, entities)) { for (int i = 0; i < entities.size();
	 * i++) { entities.get(i).setX(239 << Game.TILE_BIT_SHIFT);
	 * entities.get(i).setY(223 << Game.TILE_BIT_SHIFT); //remove(); } }
	 */

	public boolean PlayerTele(double xi, double yi, Level level, Player player) {
		boolean Transport = false;
		try {

			/*
			 * if (!player.riding) { int xp = 0; int yp = 0; xp = (int) player.x(); yp =
			 * (int) player.y(); if(xp < (int) x() + sprite.getWidth() && xp > (int) x() &&
			 * yp < (int) y() + sprite.getHeight() && yp > (int) y()
			 * 
			 * ) { Transport = true; } }
			 */
		} catch (Exception e) {
			Transport = false;
		}
		return Transport;
	}

	/*
	 * public boolean EntityTele(double xi, double yi, Level level, List<Entity>
	 * entities) { boolean TransportEntity = false; int xp = 0, yp = 0; for (int i =
	 * 0; i < entities.size(); i++) { xp = (int) entities.get(i).getX(); yp = (int)
	 * entities.get(i).getY(); if (xp < (int) x + sprite.getWidth() + 1 && xp >
	 * (int) x - 1 && yp < (int) y + sprite.getHeight() + 1 && yp > (int)y - 1
	 * 
	 * ) { TransportEntity = true; } } return TransportEntity; }
	 */

	public void render(Screen screen) {
		if (Boot.get().devModeOn) {
			Debug.drawRect(screen, (int) x(), (int) y(), 32, 32, 0xFF00FF, true);
		}
		// int radius = level.radius / 2 + 5;
		// screen.renderLight((int)x - 34 + radius, (int)y - 30 + radius, 50 - radius,
		// 20, 20, 40);
		sprite = anim_Teleport.getSprite();
		screen.renderMobSpriteUniversal((int) x(), (int) y(), sprite);
	}

}
