package com.IB.SL.inventory;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.IB.SL.entity.Entity;

public class Item extends Entity {
	
	public final String XML_String;
	public final String ROOT_ELEMENT = "item";
	
	public int required_level = 0;
	
	// - = - = - = - = - = - = - =
	public final byte SLOT_WEAPON = 7;
	public final byte SLOT_HEAD = 0;
	public final byte SLOT_CHEST = 1;
	public final byte SLOT_ARMS = 2;
	public final byte SLOT_LEGS = 3;
	public final byte SLOT_FEET = 4;
	public final byte SLOT_MSC1 = 5;
	public final byte SLOT_MSC2 = 6;
	// - = - = - = - = - = - = - =
	public byte ASSIGNED_SLOT = -1;
	public byte ACTIVE_SLOT = -1;
	// - = - = - = - = - = - = - =


	public Item(String XML) {
		this.XML_String = XML;
		init();
	}
	
	public void init() {
		readXML(XML_String);
	}
	
	public void unequip(Entity e) {
		this.ACTIVE_SLOT = -1;
		e.STAT_ITEM_VIT -= this.STAT_VIT;
		e.STAT_ITEM_END -= this.STAT_END;
		e.STAT_ITEM_STR -= this.STAT_STR;
		e.STAT_ITEM_AGI -= this.STAT_AGI;
		e.STAT_ITEM_RES -= this.STAT_RES;
		e.STAT_ITEM_FAI -= this.STAT_FAI;
		e.STAT_ITEM_INT -= this.STAT_INT;
		e.STAT_ITEM_WIS -= this.STAT_WIS;
	}
	
	public void equip(Entity e) {
		this.ACTIVE_SLOT = this.ASSIGNED_SLOT;
		e.STAT_ITEM_VIT += this.STAT_VIT;
		e.STAT_ITEM_END += this.STAT_END;
		e.STAT_ITEM_STR += this.STAT_STR;
		e.STAT_ITEM_AGI += this.STAT_AGI;
		e.STAT_ITEM_RES += this.STAT_RES;
		e.STAT_ITEM_FAI += this.STAT_FAI;
		e.STAT_ITEM_INT += this.STAT_INT;
		e.STAT_ITEM_WIS += this.STAT_WIS;
	}
	
	public void readXML(String path) {
		try {
		InputStream fXmlFile = getClass().getResourceAsStream(path);
		DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		doc.getDocumentElement().normalize();
		
		System.out.println("ROOT: " + doc.getDocumentElement().getNodeName());
		
		initItem(doc);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void initItem(Document doc) {
		NodeList nList = doc.getElementsByTagName(this.ROOT_ELEMENT);
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
	
	public void setProperties(Element e) {
		try {
		//this.id = Integer.parseInt(getTag(e, "id"));
		this.name = getTag(e, "name");
		this.rarity = Integer.parseInt(getTag(e, "rarity"));
		this.required_level = Integer.parseInt(getTag(e, "level_req"));
		this.ASSIGNED_SLOT = Byte.parseByte(getTag(e, "equip_slot"));
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	
	
	public Element getETag(Element e, String tagname) {
		return (Element) (e.getElementsByTagName(tagname).item(0));		
	}
	
	public String getTag(Element e, String tagname) {
		return (e.getElementsByTagName(tagname).item(0).getTextContent());		
	}
	
	public void initSprite(Element eElement) {
				try {
				this.xOffset = Integer.parseInt(eElement.getAttribute("xOffset"));
				this.yOffset = Integer.parseInt(eElement.getAttribute("yOffset"));
				int size = Integer.parseInt(eElement.getAttribute("size"));

				Element sprite = getETag(eElement, "sprite");
				Element icon = getETag(eElement, "icon");

//				this.sprite = buildAnimSprite(size, sp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
