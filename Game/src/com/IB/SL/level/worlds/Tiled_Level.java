package com.IB.SL.level.worlds;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.IB.SL.Boot;
import com.IB.SL.VARS;
import com.IB.SL.level.Level;
import com.IB.SL.level.tile.Tile;

public class Tiled_Level extends Level {

	String path = "";
	
	String tiled_xml = "";
	String tiled_version = "";
	
	boolean readingTiles = false;
	int[] xml_tiles;
	String tile_string = "";
	
	public Tiled_Level(String path) {
		super(path);
		
		String lvn = path.substring(path.lastIndexOf('/') + 1, path.length());

		this.tiled_xml = path + "/" + lvn + ".tmx";
		System.out.println("TILED: " + tiled_xml);
		this.path = path;
	}
	
	protected void loadLevel(String path) {
		path = this.tiled_xml;
		
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser sp;
		
		System.out.println("Loading A Tiled Level..");
		try {
			sp = parserFactory.newSAXParser();
			sp.parse("E:\\Dev\\Square Legacy 2\\Square-Legacy-2\\Game\\res\\XML\\Levels\\b10\\b10.tmx", this);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
             case "map": {
              this.tiled_version = attributes.getValue("tiledversion");
              Boot.log(this.tiled_version.equals(VARS.VAL_TILED_VER) ? "Using a Valid Version of Tiled!" : "This Version of Tiled may not be Supported!", "Tiled_Level.java", false);
              	
           	  this.width = Integer.parseInt(attributes.getValue("width"));
           	  this.height = Integer.parseInt(attributes.getValue("height"));
           	  xml_tiles = new int[width * height];
           	  
           	  break;
             }
             
             case "data": {
            	 if (attributes.getValue("encoding").equals("csv")) {
            		 readingTiles = true;
            	 } else {
            		 Boot.log("This level's tiles are encoded in an unsupported method. (Must use CSV!)..", "Tiled_Level.java", true);
            		 Boot.get().quit();
            	 }
             }
        }
	}
	
	@Override
    public void characters(char ch[], int start, int length) throws SAXException {
		if (readingTiles) {
           this.tile_string += ((new String(ch, start, length)));
		}
    }
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
        case "data": {
			this.readingTiles = false;
			
			//System.out.println("TILES: " + this.tile_string);
			List<String> items = Arrays.asList(tile_string.split("\\s*,\\s*"));
			for (int i = 0; i < items.size(); i++) {
				String str = items.get(i);
				int id = -1;
				
				try {
					id = Integer.parseInt(str);
				} catch (NumberFormatException e) {}
				
				if (id != -1) {
					xml_tiles[i] = id;
				}
			}
			

			this.tiles = xml_tiles;
			this.overlayTiles = xml_tiles;
			this.torchTiles = xml_tiles;
		}

		case "map": {
			//for (int i = 0; i < tiles.length; i++) {
			//	System.out.println(tiles[i]);
			//}
			Boot.log("Level fully loaded..", "Tiled_Level.java", false);
		}
		}
	}
	

	/*public void readXML(String path, String root, String tag) {
		try {
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			System.out.println("Root Element: " + doc.getDocumentElement());
			
			NodeList nList = doc.getElementsByTagName(root);
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nNode;
					
					
					
					getContent(e, "layer");
		 //<layer name="Tile Layer 1" width="100" height="50" offsetx="2" offsety="-27.6667">

				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public String getContent(Element e, String tag) {
		return e.getElementsByTagName(tag).item(0).getTextContent();
	}
	
	public String getAttribute(Element e, String tag) {
		return e.getAttribute(tag);
	}*/
}
