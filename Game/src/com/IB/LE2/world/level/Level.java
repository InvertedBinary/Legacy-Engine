package com.IB.LE2.world.level;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.IB.LE2.Boot;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.Vector2i;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.EntityContainer;
import com.IB.LE2.world.entity.emitter.Emitter;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.entity.mob.PlayerMP;
import com.IB.LE2.world.entity.particle.Particle;
import com.IB.LE2.world.entity.projectile.Projectile;
import com.IB.LE2.world.level.tile.Tile;


public class Level extends EntityContainer implements Serializable {
	private static final long serialVersionUID = -2980023760275759466L;

	public int width, height;
	public int[] tiles;
	transient public Tile tile;

	transient private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;

		}
	};

	public Level(String path) {
		loadLevel(path);
	}
	
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
	
	public String returnTile(List<Entity> entities) {
		Tile tile;
		tile = getTile((int) entities.get(0).x() >> VARS.TILE_BIT_SHIFT,
				(int) entities.get(0).y() >> VARS.TILE_BIT_SHIFT);
		String tileString = tile.toString();
		tileString = tileString.replace("com.IB.SL.level.tile.", "");
		tileString = tileString.substring(0, tileString.indexOf("@"));
		return tileString;
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
		Collections.sort(entitiesList, ySort);

		if (!VARS.suspend_world) {
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).update();
			}
			for (int i = 0; i < Projectiles.size(); i++) {
				Projectiles.get(i).update();
			}
			for (int i = 0; i < particles.size(); i++) {
				particles.get(i).update();
			}

			for (int i = 0; i < emitters.size(); i++) {
				emitters.get(i).update();
			}
		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}

		// Water.update();

	}
	
	public List<Projectile> getProjectiles() {
		return Projectiles;
	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solidtwo = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size - xOffset) >> VARS.TILE_BIT_SHIFT;
			int yt = (y - c / 2 * size - yOffset) >> VARS.TILE_BIT_SHIFT;
			if (getTile(xt, yt).solidtwo())
				solidtwo = true;
		}
		return solidtwo;
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

		for (int i = 0; i < entitiesList.size(); i++) {
			// if (this.getPlayersBool(entitiesList.get(i), 350)) { //TODO: Make a list of
			// entities for entities that need to update even when the player is not within
			// 350px
			if (entitiesList.get(i).ySort) entitiesList.get(i).render(screen);
			// }
		}

		for (int i = 0; i < entitiesList.size(); i++) {
			entitiesList.get(i).renderGUI(screen);
		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).renderGUI(screen);
		}

		//renderMiniMap(screen);
		drawExtendedLevel(screen);

		remove();
	}
	
	public void drawExtendedLevel(Screen screen) {}
	
	private void remove() {
		for(int i = 0; i < entitiesList.size(); i++){
	         if(entitiesList.get(i).isRemoved()) entitiesList.remove(i);
	     }
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved())
				entities.remove(i);
		}
		for (int i = 0; i < Projectiles.size(); i++) {
			if (Projectiles.get(i).isRemoved())
				Projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved())
				particles.remove(i);
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved())
				players.remove(i);
		}
		for (int i = 0; i < emitters.size(); i++) {
			if (emitters.get(i).isRemoved())
			emitters.remove(i);
		}
	}

	public void add(Entity e) {
		e.init(this);
		entitiesList.add(e);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			Projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((PlayerMP) e);
			e.added();
		} else if (e instanceof Emitter) {
			emitters.add((Emitter) e);
		} else {
			entities.add(e);
		}
	}

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
	
	//TODO: Rewrite with new getEntitiesListFixed(x, y, rad);
	//TODO: actually scrap getEntitiesListFixed into FilterList(List, rad);
	public List<Entity> AOEFull(Screen screen, int x, int y, int rad, boolean render, int color) {
		List<Entity> result = null;
		if (render) {
			drawAOE(screen, x, y, rad, color);
		}

		x >>= 4;
		y >>= 4;

		//result = getEntitiesListFixed(x, y, rad);
		return result;
	}
	
	
	public void drawAOE(Screen screen, int x, int y, int rad, int color) {
		x >>= 4;
		y >>= 4;
		
		screen.DrawCircle(x, y, rad, color, true);

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
	
	public RayCast RayCast(Vector2i pos, double angle, float rayLength){
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
		}
	
	public List<Node> findPath(Vector2i start, Vector2i goal) {
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
				double gCost = current.gCost + (getDistance(current.tile, a)/* == 1 ? 1 : 1*/);
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
	}

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
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.VoidTile;
		
		Tile t = Tile.TileIndex.get((tiles[x + y * width]));
		t = (t == null) ? Tile.VoidTile : t;
		
		return t;
	}
	
	// Create one damage method, check if the entity shooting
	// is the entity hit detected and don't damage it

	public List<Entity> getEntities() {
		return entities;
	}

}
