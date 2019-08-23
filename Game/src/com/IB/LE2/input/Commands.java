package com.IB.LE2.input;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.input.UI.UI_Manager;
import com.IB.LE2.input.UI.menu.TagMenu;
import com.IB.LE2.media.audio.Audio;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.FileIO.Assets;
import com.IB.LE2.util.FileIO.Disk;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.entity.mob.TagMob;
import com.IB.LE2.world.entity.projectile.Selector;
import com.IB.LE2.world.level.TileCoord;
import com.IB.LE2.world.level.worlds.TiledLevel;

public class Commands {

	public static void Execute(String cmd, Player player) {
		String Command = "", Modifier = "", Modifier2 = "";
		int args = 0;

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
					Modifier2 = cmd.substring(cmd.indexOf(",", Modifier.length() - 1) + 1, cmd.length()).trim();

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

		if (Command != null && Command.length() > 0) {
			try {
				switch (Command.toLowerCase()) {
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
						Modifier = "" + (int) Boot.get().getPlayer().x() / TileCoord.TILE_SIZE;
					}
					if (Modifier2.equals("$")) {
						Modifier2 = "" + (int) Boot.get().getPlayer().y() / TileCoord.TILE_SIZE;
					}
					Boot.get().getPlayer()
							.setPosition(new TileCoord(Integer.parseInt(Modifier), Integer.parseInt(Modifier2)));

					break;
				case "modav":
					try {
						System.out.println("SETTING PLAYER VAR: " + Modifier + " TO: " + Modifier2);
						player.set(Modifier, Modifier2);
					} catch (NumberFormatException e) { }
					break;
					
				case "tcl":
					player.noclip = !player.noclip;
					break;

				case "sus":
					if (Modifier.equalsIgnoreCase("true"))
						VARS.suspend_world = true;
					else if (Modifier.equalsIgnoreCase("false"))
						VARS.suspend_world = false;
					else
						VARS.suspend_world = !VARS.suspend_world;
					
					break;
				case "restart":
					Boot.restart();
					break;

				case "fullscr":
					Boot.get().setBorderlessFullscreen(!Boot.get().frame.isUndecorated());
					break;

				case "tfullscr":
					Boot.get().setTrueFullscreen();
					break;
				case "dir":
					File f = Disk.AppDataDirectory;
					if (!Modifier.equals(""))
						f = new File(Disk.AppDataDirectory.getAbsolutePath() + "/" + Modifier + "/");
					Desktop.getDesktop().open(f);
					break;
				case "dbg":
					Boot.drawDebug = !Boot.drawDebug;
					break;
				case "kill":
					if (Selector.selected != null)
						Selector.selected.remove();
					Selector.selected = null;
					break;
				case "grab":
					if (Selector.selected != null) {
						VARS.do_possession = !VARS.do_possession;
					}
					break;
				case "push":
					if (Selector.selected != null) {
						Selector.selected.vel().set(Double.parseDouble(Modifier), Double.parseDouble(Modifier2));
					}
					break;
				case "snd":
				case "aud":
					Audio.Play(Modifier);
					break;

				case "killmus":
				case "killmusic":
					Audio.StopMusic();
					break;

				case "menu":
				case "ui":
					UI_Manager.Current().ResumeWorldInput();
					UI_Manager.Load(new TagMenu(Modifier));
					break;
				case "ldap":
				case "loadap":
				case "loadpack":
					Assets.LoadPack(Modifier);
					break;
				case "avg": {
					Game.showAVG = !Game.showAVG;
					String fileName;
					if (Modifier.equals("$log-start")) {
						boolean path = new File(Disk.AppDataDirectory + "/logs/").mkdir();
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
								new FileOutputStream(Disk.AppDataDirectory + "/logs/" + fileName + ".txt"), "utf-8"))) {
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
						// player.money += Double.parseDouble(Modifier);
						System.out.println(Modifier + " Gold Added");
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "time":
					break;
				case "cl":
					// Boot.get().getPlayer().setPosition(0, 0, Integer.parseInt(Modifier), true);
					break;
				case "spawn":
					Boot.get().getLevel()
							.add(new TagMob("/Tags/Entities/" + Modifier + ".xml",
									Boot.get().getPlayer().x() / TileCoord.TILE_SIZE,
									Boot.get().getPlayer().y() / TileCoord.TILE_SIZE));
					break;
				case "ld":
					if (Modifier.equals(""))
						Modifier = ((TiledLevel) Boot.getLevel()).path;
					else
						Modifier = "/Tags/Levels/" + Modifier;

					player.setPositionTiled(-1, -1, Modifier, true);
					break;
				case "con":
					if (Modifier2.equals("")) {
						Boot.OpenConnection("localhost");
						Boot.get().getPlayer().name = Modifier;
					} else {
						Boot.get().getPlayer().name = Modifier2;
						Boot.OpenConnection(Modifier);
					}

					boolean attempting = true;
					Boot.get().conTime = 0;
					while (attempting) {
						attempting = !Boot.isConnected;
						if ((Boot.get().conTime >= 120) || Boot.isConnected) {
							attempting = false;
						}
						System.out.println("in connection loop!");
					}

					// Boot.c.sendMessage(Level.entityStringBuilder(Boot.get().getPlayer()));
					break;
				case "svr":
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
		}

	}
}
