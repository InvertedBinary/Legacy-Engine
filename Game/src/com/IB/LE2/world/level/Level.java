package com.IB.LE2.world.level;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.IB.LE2.Boot;
import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.Vector2i;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.EntityContainer;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.entity.mob.PlayerMP;
import com.IB.LE2.world.entity.projectile.Projectile;
import com.IB.LE2.world.level.tile.Tile;


public class Level extends EntityContainer implements Serializable {
	private static final long serialVersionUID = -2980023760275759466L;

	transient public int width, height;
	transient public int[] tiles;
	transient public int[] lightmap;
	transient public int[] dynamic_lightmap;
	
	transient public Tile tile;
	transient public String name;
	
	public transient HashMap<Integer, Tile> tile_map = new HashMap<>();

	transient public boolean DoDayCycle = true;
	transient public int BaseBrightness = 0;
	transient public final static int DayTime = 24000;
	transient public final static int NightTime = 36000;
	transient public static int WorldTime = 0;
	transient public static int time_per_tick = 1;
	
	transient private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};

	public void loadLua() {}
	
	protected void loadLevel(String path) { }
	
	@Deprecated
	public static Entity createEntity(String msg)
		{
			System.out.println("CREATING ENTITY: " + msg);
			int eid = Integer.parseInt(msg.substring(msg.indexOf("id_e=") + 5, msg.indexOf("&")));
			String UUID = (msg.substring(msg.indexOf("id=") + 3, msg.indexOf("@")));
			double x = Double.parseDouble(msg.substring(msg.indexOf("x=") + 2, msg.indexOf(",")));
			double y = Double.parseDouble(msg.substring(msg.indexOf("y=") + 2, msg.indexOf("#")));
			//System.out.println("EID: " + eid /* + " ID: " + id */ + " X: " + x + " Y: " + y);

			Entity e = null;
			switch (eid) {
			case 0:
				String usrn = (msg.substring(msg.indexOf("un=") + 2));
				e = new PlayerMP(x, y, usrn, UUID);
				e.name = usrn;
				break;
			}
			return e;
		}
	
	@Deprecated
	public static String entityStringBuilder(Entity e) {
			String result = "ADD|id_e=0&id=25@x=96,y=1248#un=usrn";
			String eid, UUID, x, y, usrn;
			usrn = "";
			if (e instanceof Player) {
			 eid = "0";
			 if(e.name != null)
				 usrn = e.name;
			} else {
			 eid = "-1";
			}
			 UUID = e.UUID;
			 x = "" + e.x();
			 y = "" + e.y();
			
			result = "ADD|id_e=" + eid + "&id=" + UUID + "@x=" + x + ",y=" + y + "#un=" + usrn;
					
		return result;
	}
	
	 transient private Vector2i start;
	 transient private Vector2i goal;
	
	 transient private Comparator<Entity> ySort = new Comparator<Entity>() {
		    public int compare(Entity e1, Entity e2) {
		      if (e1.y() > e2.y()) return 1; // Shift Up
		      if (e1.y() < e2.y()) return -1; // Shift Down
		      return 0;
		    }
		  };
		  
	public void update() {
		Collections.sort(all, ySort);

		if (!VARS.suspend_world) {
			for (int i = 0; i < dynamic_lightmap.length; i++) {
				if (dynamic_lightmap[i] > 0) {
					dynamic_lightmap[i]--;					
				}
			}
			
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).update();
			}
			
			for (int i = 0; i < projectiles.size(); i++) {
				projectiles.get(i).update();
			}
			
			for (int i = 0; i < particles.size(); i++) {
				particles.get(i).update();
			}

			for (int i = 0; i < emitters.size(); i++) {
				emitters.get(i).update();
			}

			for (int i = 0; i < players.size(); i++) {
				players.get(i).update();
			}
			
		}
		
		for (int id : tile_map.keySet()) {
			tile_map.get(id).update();
		}
		
		DaylightCycle();
	}
	
	public static void DaylightCycle() {
		if (WorldTime > (DayTime + NightTime))
			WorldTime = 0;
		WorldTime += time_per_tick;
	}
	
	
	public static int getGlobalBrightness() {
		double TotalDay = DayTime + NightTime;
		double PeakNight = DayTime + NightTime/3;
		double PeakNight2 = DayTime + NightTime/3 + NightTime/3;
		double maxdark = -255;
		if (WorldTime < DayTime)
			return 0;
		else if (WorldTime < PeakNight) {
			double perc = (WorldTime - DayTime) / (PeakNight - DayTime) * maxdark;
			return (int) perc;
		} else if (WorldTime < PeakNight2) {
			return (int) maxdark;
		} else {
			double perc = maxdark - ((WorldTime - PeakNight2) / (TotalDay - PeakNight2) * maxdark);
			return (int) perc;
		}
	}
	
	
	public int getLevelBrightness() {
		int result = this.BaseBrightness;
		if (this.DoDayCycle) {
			result += getGlobalBrightness();			
		}
		
		return result;
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	int bgcolor = 0xffBED0CA;
	/*
	 * SpriteSheet pl_bg = new SpriteSheet("/XML/Levels/a10/assets/parallax/bg.png",
	 * 640, 360); SpriteSheet pl_first = new
	 * SpriteSheet("/XML/Levels/a10/assets/parallax/first_layer.png", 149, 35);
	 * SpriteSheet pl_second = new
	 * SpriteSheet("/XML/Levels/a10/assets/parallax/second_layer.png", 234, 92);
	 * SpriteSheet pl_third = new
	 * SpriteSheet("/XML/Levels/a10/assets/parallax/third_layer.png", 352, 297);
	 */

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> VARS.TILE_BIT_SHIFT;
		int x1 = (xScroll + screen.width + TileCoord.TILE_SIZE) >> VARS.TILE_BIT_SHIFT;
		int y0 = yScroll >> VARS.TILE_BIT_SHIFT;
		int y1 = (yScroll + screen.height + TileCoord.TILE_SIZE) >> VARS.TILE_BIT_SHIFT;

		try {

			//screen.renderSheet(0, 0, SpriteSheet.get("level_background"), false);
			// screen.renderParallax(bgcolor, pl_first, pl_second, pl_third, 37);

			// screen.renderSheet(0, 37, pl_first, false);
			// screen.renderSheet(0, 36, pl_second, false);
			// screen.renderSheet(0, 63, pl_third, false);
		} catch (Exception e) {
			
		}

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				// renderMiniMap(screen, 32, 32, 40, 40);
				Tile tile = getTile(x, y);
				tile.render(x, y, screen);
			}
		}

		for (int i = 0; i < entities.size(); i++) {
			if (!entities.get(i).ySort) entities.get(i).render(screen);
		}

		for (int i = 0; i < all.size(); i++) {
			// if (this.getPlayersBool(entitiesList.get(i), 350)) { //TODO: Make a list of
			// entities for entities that need to update even when the player is not within
			// 350px
			if (all.get(i).ySort) all.get(i).render(screen);
			// }
		}

		for (int i = 0; i < all.size(); i++) {
			all.get(i).renderGUI(screen);
		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).renderGUI(screen);
		}

		//renderMiniMap(screen);
		drawExtendedLevel(screen);

		remove();
	}
	
	public void drawExtendedLevel(Screen screen) {}
	
	public List<PlayerMP> getPlayers() {
		return players;
	}

	public Player getPlayerAt(int index) {
		return players.get(index);
	}
	
	public Entity getEntityAt(int index) {
		return entities.get(index);
	}

	public Player getClientPlayer() {
		return Boot.get().getPlayer();
	}
	
	public List<Vector2i> BresenhamLine(int x1, int y1, int x2, int y2){
		   List<Vector2i> result = new ArrayList<Vector2i>();
		   int dy = y2 - y1;
		   int dx = x2 - x1;
		   int xs, ys;
		   
		   if(dy < 0) {dy = -dy; ys = -1;} else { ys = 1;}
		   if(dx < 0) {dx = -dx; xs = -1;} else { xs = 1;}
		   dy <<= 1;
		   dx <<= 1;
		   
		   result.add(new Vector2i(x1, y1));
		   if(dx > dy){
		      int fraction = dy - (dx >> 1);
		      while(x1 != x2){
		         if(fraction >= 0){
		            y1 += ys;
		            fraction -= dx;
		         }
		         x1 += xs;
		         fraction += dy;
		         result.add(new Vector2i(x1, y1));
		      }
		   }else{
		      int fraction = dx - (dy >> 1);
		      while(y1 != y2){
		         if(fraction >= 0){
		            x1 += xs;
		            fraction -= dy;
		         }
		         y1 += ys;
		         fraction += dx;
		         result.add(new Vector2i(x1, y1));
		      }
		   }
		   return result;
		}
	
	/*public RayCast RayCast(Vector2i pos, double angle, float rayLength){
		   RayCast result = new RayCast();
		   result.setCollided(false);
		   
		   if(rayLength <= 0) {
		      result.setCollided(this.getTile(pos.getX()>>4, pos.getY()>>4).solid());
		      result.setPosition(pos);
		      return result;
		   }
		   
		   double adjacent = pos.getX()+rayLength*Math.cos(angle);
		   double opposite = pos.getY()+rayLength*Math.sin(angle);
		   List<Vector2i> rayLine = BresenhamLine(pos.getX(), pos.getY(), (int)adjacent, (int)opposite);
		   
		   if(!rayLine.isEmpty()){
		      for(int rayVectorIndex = 0; rayVectorIndex < rayLine.size(); rayVectorIndex++){
		         Vector2i rayVector = rayLine.get(rayVectorIndex);
		         result.rayVector = rayVector;
		         if(this.getTile(rayVector.getX()>>4, rayVector.getY()>>4).solid()){
		            result.setPosition(rayVector);
		            result.setCollided(true);
		            break;
		         }
		      }
		   }
		   
		   return result;
		}*/
	
	/*public List<Node> findPath(Vector2i start, Vector2i goal) {
		this.start = start;
		this.goal = goal;
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) {
			current = openList.get(0);
			Collections.sort(openList, nodeSorter);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4)
					continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null)
					continue;
				if (at.solid())
					continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a)/* == 1 ? 1 : 1*//*);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost)
					continue;
				if (!vecInList(openList, a) || gCost < node.gCost)
					openList.add(node);

			}
		}
		closedList.clear();
		return null;
	}*/

	private boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector))
				return true;
		}
		return false;
	}

	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		
		return Math.sqrt(dx * dx + dy * dy);
	}

	/*public void addTile(AnimatedTile t) {
		tiles_anim.add(t);
	}*/
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return getTile(0, 0);
		
		Tile t = tile_map.get(tiles[x + y * width]);
		t = (t == null) ? Tile.Air : t;
		
		return t;
	}
	
	public static void DamageEntity(Entity e, double damage) {
		if (e.nvar("health") != -1) {
			e.modvar("health", -damage);
			e.hurt = 5;
			if (e.nvar("health") <= 0) {
				e.remove();
			}
		}
	}
	
	public List<Entity> getEntities() {
		return entities;
	}

	public int LightValues(int tilesx, int tilesy) {
		int idx = tilesx + tilesy * width;
		return getLevelBrightness() + lightmap[idx] + dynamic_lightmap[idx];
	}
}
