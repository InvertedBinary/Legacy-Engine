package com.IB.SL;

import java.util.HashMap;

public class Boot {

	public static String title = "Square Legacy 2 [Build 1 : 10/29/17]";
	private static Game g = new Game(title);

	public static Game get() {
		return g;
	}
	
    public static  HashMap<String, Boolean>  launch_args;
	
	public static void main(String[] args) {
    	launch_args = new HashMap<String, Boolean>();

    	for (String s : args) {
    		launch_args.put(s, true);
    	}
		
		g.Launch(g);
		

	}

}
