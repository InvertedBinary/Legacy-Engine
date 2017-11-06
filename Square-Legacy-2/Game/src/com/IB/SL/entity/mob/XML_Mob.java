package com.IB.SL.entity.mob;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.IB.SL.entity.Entity;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;

public class XML_Mob extends Mob {
	
	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.zombie_down, 16, 16, 3);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.zombie_up, 16, 16, 3);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.zombie_left, 16, 16, 2);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.zombie_right, 16, 16, 2);
	transient private AnimatedSprite animSprite = down;
	transient private GUI gui = new GUI();

	
	transient private int time = 0;
	transient double xa = 0;
	transient double ya = 0; 
	
	transient List<Player> players;
	transient Player p;
	transient Entity tracking = null;
	transient private int visability;
	transient List<Entity> entities;

	public String XML_String = "";
	
	public XML_Mob(String XML) {
		readXML(XML);
	}
	
	public XML_Mob(double x, double y, String XML) {
		readXML(XML);
		this.x = x * 32;
		this.y = y * 32;
	}
	
	public void readXML(String path) {
		this.XML_String = path;
		try {
		InputStream fXmlFile = getClass().getResourceAsStream(path);
		DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		doc.getDocumentElement().normalize();
		
		System.out.println("ROOT: " + doc.getDocumentElement().getNodeName());
		
		initMob(doc);
		initSprite(doc);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void initMob(Document doc) {
		NodeList nList = doc.getElementsByTagName("mob");
		System.out.println("----------------------------");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				try {
					setProperties(eElement);
					} catch (Exception e) {
						e.printStackTrace();
				}
			}
		}
	}
	
	public void setProperties(Element eElement) {
		try {
		this.id = Integer.parseInt(eElement.getAttribute("id"));
		this.name = (eElement.getElementsByTagName("name").item(0).getTextContent());
		this.speed = Double.parseDouble(eElement.getElementsByTagName("speed").item(0).getTextContent());
		this.maxhealth = Double.parseDouble(eElement.getElementsByTagName("maxhealth").item(0).getTextContent());
		this.mobhealth = maxhealth;
		this.rarity = Integer.parseInt(eElement.getElementsByTagName("rarity").item(0).getTextContent());
		this.Exp = Integer.parseInt(eElement.getElementsByTagName("exp").item(0).getTextContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initSprite(Document doc) {
		NodeList nList = doc.getElementsByTagName("sprite");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				
				try {
				this.xOffset = Integer.parseInt(eElement.getAttribute("xOffset"));
				this.yOffset = Integer.parseInt(eElement.getAttribute("yOffset"));
				int size = Integer.parseInt(eElement.getAttribute("size"));

				Element xml_down = (Element) eElement.getElementsByTagName("down").item(0);
				Element xml_up = (Element) eElement.getElementsByTagName("up").item(0);
				Element xml_left = (Element) eElement.getElementsByTagName("left").item(0);
				Element xml_right = (Element) eElement.getElementsByTagName("right").item(0);
				
				this.down = buildAnimSprite(size, xml_down);
				this.up = buildAnimSprite(size, xml_up);
				this.left = buildAnimSprite(size, xml_left);
				this.right = buildAnimSprite(size, xml_right);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public AnimatedSprite buildAnimSprite(int size, Element XML) {
		AnimatedSprite xanim;
		try {
			Field field = SpriteSheet.class.getField(XML.getTextContent());
			Object sprite = field.get(field.getType());
			xanim = new AnimatedSprite((SpriteSheet) sprite, size, size,
					Integer.parseInt(XML.getAttribute("length")));
			return xanim;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void update() {
		time = (time % 60 == 0) ? 0 : time++;
		if (time % 8 == 0) {
			this.hurt = false;
		}
		
		if (level.brightness <= 0) {
			visability = level.brightness * -1 / 2;
		} else {
			visability = 0;
		}
		
		visability -= this.mobhealth / 2;
		
		moveSimple();
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (ya < 0) {
			animSprite = up;
			dir = DIRECTION.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = DIRECTION.DOWN;
		} 
		
		if (xa < 0) {
			animSprite = left;
			dir = DIRECTION.LEFT;
		} else if (xa > 0) {
			animSprite = right;
			dir = DIRECTION.RIGHT;
		}
	}
	
    private void updateShooting() {
       /* List<Player> players = level.getPlayers(this, 150 - visability);  
        if ((players.size() > 0) && fireRate <= 0) {
            double px = level.getClientPlayer().getX();
            double py = level.getClientPlayer().getY();
            double sx = this.getX();
            double sy = this.getY();
            double dx = px - sx;
            double dy = py - sy;
            double dir = Math.atan2(dy, dx);
            RockTHROWING(x, y + 4, dir, this);
			level.getClientPlayer().incombat = true;
            fireRate = ThrowableRock.FIRE_RATE;
        }*/
    }
    
    private void updateMobShooting(Entity e) {
        /*if (fireRate <= 0) {
            double px = e.getX();
            double py = e.getY();
            double sx = this.getX();
            double sy = this.getY();
            double dx = px - sx;
            double dy = py - sy;
            double dir = Math.atan2(dy, dx);
            RockTHROWING(x, y + 4, dir, this);
            fireRate = ThrowableRock.FIRE_RATE;
        }*/
    }	
	
	private void moveSimple() {
		xa = 0;
		ya = 0;
		players = level.getPlayers(this, 125 - visability);
		List<Player> players2 = level.getPlayers(this, 215 - visability);
		entities = level.getEntities(this, 150 - visability, hostility.NEU, hostility.PASS);

		if (entities.size() > 0) {
			if (players2.size() > 0) {
				Entity e = entities.get(0);
				tracking = e;
				if ((int) x < (int) e.getX() + 20)
					xa += this.speed;
				if ((int) x > (int) e.getX() - 20)
					xa -= this.speed;
				if ((int) y < (int) e.getY() + 20)
					ya += this.speed;
				if ((int) y > (int) e.getY() - 20)
					ya -= this.speed;
				//updateMobShooting(e);
			}
		} else if (players.size() > 0) {
			p = players.get(0);
			tracking = p;
			if ((int) x < (int) p.getX() + 20)
				xa += this.speed;
			if ((int) x > (int) p.getX() - 20)
				xa -= this.speed;
			if ((int) y < (int) p.getY() + 20)
				ya += this.speed;
			if ((int) y > (int) p.getY() - 20)
				ya -= this.speed;
			// updateShooting();

		} else {
			if (time % (random.nextInt(50) + 30) == 0) {
				xa = random.nextInt(3) - 1;
				ya = random.nextInt(3) - 1;
				if (random.nextInt(2) == 0) {
					xa = 0;
					ya = 0;
				}
			}
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}
	
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		if (this.mobhealth < this.maxhealth) screen.renderSprite((int) x - 16, (int)y - 24, gui.renderMobHealthExperiment(this, 20), true);
		//gui.renderName(screen, "Zombie", (int)x - 14, (int)y- 25, -4, true, true);
		sprite = animSprite.getSprite();
		screen.renderMobSpriteUniversal((int) (x + xOffset), (int) (y + yOffset), sprite);
	
	}
}
