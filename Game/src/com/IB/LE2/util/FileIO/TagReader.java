package com.IB.LE2.util.FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TagReader {

	protected String PATH = "";
	protected String TAG = "";
	protected String ROOT_ELEMENT;
	protected InputStream tag_stream = null;

	private ArrayList<Tag> tags = new ArrayList<>();
	public Tag HEAD;

	public TagReadListener callbacks;
	
	
	public TagReader(String path, String root_element, TagReadListener callbacks) {
		this.PATH = path;
		
		if (TAG.contains("."))
			this.TAG = path.substring(path.lastIndexOf('\\') + 1, path.lastIndexOf("."));
		else 
			this.TAG = path.substring(path.lastIndexOf('\\') + 1, path.length());
		
		this.callbacks = callbacks;
		this.ROOT_ELEMENT = root_element;
	}
	
	public String getPath() {
		return PATH;
	}
	
	public ArrayList<Tag> getTags() {
		return this.tags;
	}
	
	public void start() {
		if (PATH.equals("")) {
			PATH = TAG;
		}
		
		if (!PATH.endsWith(".xml") && !PATH.endsWith(".tmx") && !PATH.endsWith(".tsx")) {
			PATH += ".xml";
		}
		
		System.out.println("PATH: "  + PATH);
		
		try {
			tag_stream = new FileInputStream(new File(PATH));

			if (tag_stream == null) {
				callbacks.TagsError();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		readTags();
		
		try {
			tag_stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		callbacks.TagsRead();
	}
	
	private void readTags() {
		try {
			DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
			Document doc = dBuilder.parse(tag_stream);
			doc.getDocumentElement().normalize();
			System.out.println("BUILDING TAGS...");
			BuildTags(null, doc.getChildNodes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void BuildTags(Tag parent, NodeList nodes) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			if (n.getNodeType() == Node.TEXT_NODE) {
				if (n.getNodeValue() != null)
					parent.value = n.getNodeValue().trim();
				continue;
			}
			
			if (n.getNodeType() != Node.ELEMENT_NODE) continue;

			String tagname = n.getNodeName();
			
			Node hnode = n.getParentNode();
			String uri = tagname;
			while (hnode.getParentNode() != null) {
				uri = hnode.getNodeName() + "." + uri;
				hnode = hnode.getParentNode();
			}
			
			Tag t = new Tag(tagname, uri, "");
			if (HEAD == null)
				HEAD = t;
			else {
				parent.addChild(t);
				t.parent = parent;				
			}
			
			
			t.setAttributes(n.getAttributes());
			
			tags.add(t);
			BuildTags(t, n.getChildNodes());
		}
	}
	
	public void PrintTags() {
		System.out.println("-=-=-=-=-=-==-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=");
		PrintTags(HEAD);
		System.out.println("-=-=-=-=-=-==-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=");
	}
	
	public void PrintTags(Tag start) {
		System.out.print("<" + start.name);
		if (start.getAttributes().size() != 0) {
			for (String s : start.getAttributes().keySet()) {
				System.out.print(" " + s + "=\"" + start.getAttributes().get(s) + "\"");
			}
		}
		
		if (!start.value.equals("")) {			
			System.out.println(">\n" + start.value);				
		} else {
			if (!start.hasChild()) {
				System.out.print("/>\n");
				return;
			}
			System.out.print(">\n");
		}
		
		if (start.hasChild()) {
			for (Tag t : start.getChildren()) {
				PrintTags(t);
			}
		}
		System.out.println("</" + start.name + ">");
	}
}
