package com.IB.SL.util;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;

import com.IB.SL.Boot;
import com.IB.SL.Game;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.XML_Mob;
import com.IB.SL.level.Level;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.worlds.Maps;
import com.IB.SL.level.worlds.XML_Level;

public class Commands {

	static String newline = System.getProperty("line.separator");
	static String HelpText = 
			"Note: Commands Are NOT CaSe SeNsItIvE" + newline +
			"Help: (Shows this screen) " + newline + 
			"Speed: (Sets Player Speed)" + newline + 
			"Exp: (Adds/Subs Player EXP)" + newline +
			"Time: (Sets Time), (Works Best At Total Night/Day)" + newline +
			"TP: (Multi-player: TPs to another player)";
	
	public void updateCommandMode(String cmd, Player player) {
		ArrayList<String> cmds = new ArrayList<String>();
		int args = 0;
		String Command = "", Modifier = "", Modifier2 = "";
		
		for (int i = 0; i < cmd.toCharArray().length; i++) {
			if (cmd.toCharArray()[i] == ',') {
				args++;
			}
		}
		
		System.out.println("ARGUMENTS: " + args);

		try {
	if (cmd.contains(" ")) {
		if (args == 0) {
		 Command = cmd.substring(0, cmd.indexOf(" "));
		 Modifier = cmd.substring(cmd.indexOf(" ") + 1, cmd.length());
		} else if (args == 1) {
			 Command = cmd.substring(0, cmd.indexOf(" "));
			 Modifier = cmd.substring(cmd.indexOf(" ") + 1, cmd.indexOf(","));
			 Modifier2 = cmd.substring(cmd.indexOf(",", Modifier.length() - 1) + 1, cmd.length());
			 
			 System.out.println("MODIFIER: " + Modifier + " MOD 2: " + Modifier2);
			}
		} else {
			Command = cmd;
		}
		
		
		} catch (Exception e) {
			if (Modifier == null) {
				Modifier = "";
			}
			if (Modifier2 == null) {
				Modifier2 = "";
			}
		}
		
		cmds.add("help");
		cmds.add("tp");
		cmds.add("tpp");
		cmds.add("speed");
		cmds.add("exp");
		cmds.add("time");
		cmds.add("money");
		cmds.add("avg");
		cmds.add("dir");
		cmds.add("dbg");
		cmds.add("tcl");
		cmds.add("weather");
		cmds.add("nospawns");
		cmds.add("gs3");
		cmds.add("stage");
		cmds.add("cq");
		cmds.add("load");
		cmds.add("spawn");
		cmds.add("xmload");

			if (Command != null && Command.length() > 0) {
					if (cmds.contains(Command.toLowerCase())) {
						try {
				switch (Command.toLowerCase()) {
			/*	case "help": JOptionPane.showMessageDialog(Game.frame, HelpText, "Help", JOptionPane.INFORMATION_MESSAGE);
		break;	*/
				case "tpp": Boot.get().getLevel().MPTeleport(Modifier);
		break;
				case "nospawns":
					if (Boot.launch_args.containsKey("-nospawns")) {
						boolean g = Boot.launch_args.get("-nospawns");						
						Boot.launch_args.put("-nospawns", !Boot.launch_args.get("-nospawns"));
					} else {
						Boot.launch_args.put("-nospawns", false);
					}
		break;
				case "tp": 
					if (Modifier.equals("$")) {
						Modifier = ""+(int)Boot.get().getPlayer().x/TileCoord.TILE_SIZE;
					}
					if (Modifier2.equals("$")) {
						Modifier2 = ""+(int)Boot.get().getPlayer().y/TileCoord.TILE_SIZE;
					}
				Boot.get().getPlayer().setPosition(new TileCoord(Integer.parseInt(Modifier), Integer.parseInt(Modifier2)));

		break;
				case "load":
					int id = Integer.parseInt(Modifier);	
							Boot.get().getPlayer().setPosition(0, 0, id, false);
					break;
				case "speed": 
				{
					try {
						double WalkingSpeedInt = Double.parseDouble(Modifier);
						System.out.println("Set Speed Equal To " + WalkingSpeedInt);
						player.speed = WalkingSpeedInt;
						
					}catch (NumberFormatException e) {
					}
					
				}
		break;
		
				case "weather": 
					if (Modifier.equalsIgnoreCase("rain")) {
						Boot.get().getLevel().isRaining = !Boot.get().getLevel().isRaining;
					} else if (Modifier.equalsIgnoreCase("clear")) {
						Boot.get().getLevel().isRaining = false;
					}
		break;
		
				case "tcl":
					player.noclip = !player.noclip;
				break;
				case "gs3":
					//Boot.get().switchState(Boot.get().gameState.INGAME_A);
					break;
				
				case "dir":
					File f = new File(SaveGame.createSaveFolder());
					if (Modifier.equals("")) {						
					f = new File(SaveGame.createSaveFolder());
					} else if (Modifier.equals("$logs")) {
					f = new File(SaveGame.createSaveFolder() + "/logs/");
					}
					Desktop.getDesktop().open(f);
				break;
				
				case "dbg":
				
				/*	if (!Boot.get().gameState.equals(Boot.get().gameState.INGAME_A)) {
						Game.switchState(Boot.get().gameState.INGAME_A);						
					} else {
						Game.switchState(Boot.get().gameState.INGAME);
					}
					*/
				break;
		
				case "avg": 
				{
					
					Game.showAVG = !Game.showAVG;
					String fileName;
					if (Modifier.equals("$log-start")) {
						boolean path = new File(SaveGame.createSaveFolder() + "/logs/").mkdir();
							Game.recAVG_FPS = true;
					}
						
					if (Modifier.equals("$log-stop")) {
						if (Modifier2.equals("")) {
							fileName = "FPS_LOG_" + System.currentTimeMillis();
						} else {
							fileName = Modifier2;
						}
							Game.recAVG_FPS = false;
							Date date = new Date();
							try (Writer writer = new BufferedWriter(new OutputStreamWriter(
									new FileOutputStream(SaveGame.createSaveFolder() + "/logs/" + fileName + ".txt"), "utf-8"))) {
								writer.write("AVERAGE FPS: " + Game.fpsAVG + ", SAMPLE SIZE: " + Game.fpsIndex);
								writer.write(System.getProperty("line.separator") + System.getProperty("line.separator"));
								writer.write("TEST CONDUCTED ON: " + date);
								writer.close();
							}
							Game.fpsIndex = 0;
							Game.fpsTotal = 0;
							Game.fpsAVG = 0;
						}
					}
		break;
				case "money":
					try {
							player.money += Double.parseDouble(Modifier);
							System.out.println(Modifier + " Gold Added");
						} catch (Exception e) {
							e.printStackTrace();
					}
		break;
				case "time":
					try {
					int time = Integer.parseInt(Modifier);
							Level.brightness = time;
							if(time > 0) {
								Level.daytime = 2600;		
							}
							if (time <= 0) {
								Level.nighttime = 2500;
							}
							System.out.println("Set Time To: " + time);
							time = 0;
					} catch (Exception e) {
					e.printStackTrace();
			}
		break;
				case "cl":
					Boot.get().getPlayer().setPosition(0, 0, Integer.parseInt(Modifier), true);
		break;
				case "spawn":
					Boot.get().getLevel().add(new XML_Mob(Boot.get().getPlayer().x / TileCoord.TILE_SIZE, Boot.get().getPlayer().y / TileCoord.TILE_SIZE, "/XML/Entities/" + Modifier + ".xml"));
		break;
				case "xmload":
					player.setPositionXML(0, 0, "/XML/Levels/" + Modifier, true);
		break;
				case "": 
					System.out.println("... Finished CMD Lap");
		break;
				}
				Command = "";
				
				} catch (Exception e) {
					System.err.println("Improper CMD, try again!");
					e.printStackTrace();
				}
			} else {
			}
		}
			
	}
}
/*
	
	
	
	
	
	
	
	
	
	
	
	
	public void updateCommandMode(String cmd, Player player) {
		ArrayList<String> cmds = new ArrayList<String>();
		int args = 0;
		String Command = "", Modifier = "", Modifier2 = "";
		
		for (int i = 0; i < cmd.toCharArray().length; i++) {
			if (cmd.toCharArray()[i] == ',') {
				args++;
			}
		}
		
		System.out.println("ARUMENTS: " + args);
		
		if (args == 0) {
		 Command = cmd.substring(0, cmd.indexOf(" "));
		 Modifier = cmd.substring(cmd.indexOf(" ") + 1, cmd.length());
		} else if (args == 1) {
			 Command = cmd.substring(0, cmd.indexOf(" "));
			 Modifier = cmd.substring(cmd.indexOf(" ") + 1, cmd.indexOf(","));
			 Modifier2 = cmd.substring(cmd.indexOf(",", Modifier.length() - 1) + 1, cmd.length());
			 
			 System.out.println("MODIFIER: " + Modifier + " MOD 2: " + Modifier2);
		}
		
		cmds.add("help");
		cmds.add("tp");
		cmds.add("tpp");
		cmds.add("speed");
		cmds.add("exp");
		cmds.add("time");
		cmds.add("money");
		cmds.add("ip");
		
			if (Command != null && Command.length() > 0) {
					if (cmds.contains(Command.toLowerCase())) {
						try {
				switch (Command.toLowerCase()) {
			/*	case "help": JOptionPane.showMessageDialog(Game.frame, HelpText, "Help", JOptionPane.INFORMATION_MESSAGE);
		break;	*/
		/*		case "tpp": Game.getGame().getLevel().MPTeleport(Modifier);
		break;
				case "tp": Game.getGame().getPlayer().setPosition(new TileCoord(Integer.parseInt(Modifier), Integer.parseInt(Modifier2)));
		break;
				case "ip": Game.getGame().PersonNameGetter = (Modifier);
		break;
				case "speed": 
				{
					try {
						double WalkingSpeedInt = Double.parseDouble(Modifier);
						System.out.println("Set Speed Equal To " + WalkingSpeedInt);
						player.speed = WalkingSpeedInt;
						
					}catch (NumberFormatException e) {
					}
					
				}
		break;
				case "exp":
					try {
							player.ExpC += Double.parseDouble(Modifier);
							System.out.println("Added: " + Modifier + "To Current EXP");
						} catch (Exception e) {
							e.printStackTrace();
					}
		break;
				case "money":
					try {
							player.money += Double.parseDouble(Modifier);
							System.out.println(Modifier + " Gold Added");
						} catch (Exception e) {
							e.printStackTrace();
					}
		break;
				case "time":
					try {
					int time = Integer.parseInt(Modifier);
							Level.brightness = time;
							if(time > 0) {
								Level.daytime = 2600;		
							}
							if (time <= 0) {
								Level.nighttime = 2500;
							}
							System.out.println("Set Time To: " + time);
							time = 0;
					} catch (Exception e) {
					e.printStackTrace();
			}
		break;
				case "": 
					System.out.println("... Finished CMD Lap");
				}
				Command = "";
				
	} catch (Exception e) {
	System.err.println("Improper CMD, try again!");
	JOptionPane.showMessageDialog (Game.frame, "Improper CMD Arg", "Oh noes!", JOptionPane.ERROR_MESSAGE);
	player.commandModeOn = true;
	updateCommandMode(player, true);
	}
	} else {
	JOptionPane.showMessageDialog (Game.frame, "Invalid Command", "Command Mode", JOptionPane.ERROR_MESSAGE);
	player.commandModeOn = true;
	updateCommandMode(player, true);		
	}
		}
		}

	
}*/