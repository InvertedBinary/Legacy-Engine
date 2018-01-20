package com.IB.SL.level.worlds;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.IB.SL.Boot;
import com.IB.SL.VARS;
import com.IB.SL.level.Level;

public class Tiled_Level extends Level {

	String path = "";
	
	String tiled_xml = "";
	String tiled_version = "";

	public Tiled_Level(String path) {
		super(path);
		
		String lvn = path.substring(path.lastIndexOf('/') + 1, path.length());

		this.tiled_xml = path + "/" + lvn + ".tmx";
		System.out.println("TILED: " + tiled_xml);
		this.path = path;
	}
	
	protected void loadLevel(String path) {
		path =  this.tiled_xml;
		
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
	  	System.out.println("TiledMap Pre-Height: " + height);

        switch (qName) {
             case "map": {
              this.tiled_version = attributes.getValue("tiledversion");
              Boot.log(this.tiled_version.equals(VARS.VAL_TILED_VER) ? "Using a Valid Version of Tiled!" : "This Version of Tiled may not be Supported!", "Tiled_Level", false);
              	
           	  this.width = Integer.parseInt(attributes.getValue("width"));
           	  this.height = Integer.parseInt(attributes.getValue("height"));
           	  	System.out.println("TiledMap Post-Height: " + height);
           	  break;
             }
        }
	}

	// A start tag is encountered.
	/*@Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
         throws SAXException {
	
    }*/
	
	
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
         switch (qName) {
         }
    }

	public void readXML(String path, String root, String tag) {
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
	}
}
