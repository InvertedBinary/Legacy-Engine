package com.IB.LE2.util.FileIO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.IB.LE2.Boot;

public class Disk {
	
	private static final String AppDataRoot = "/" + Boot.prefsStr("Disk", "AppDataDirectoryRoot", "LE2Mod");
	public static final String[] subdirs = { "/bin", "/mods" };

	public static final File AppDataDirectory = AppDataDirectory();
	
	private static File AppDataDirectory() {
		String home = System.getProperty("user.home");
		String OS = System.getProperty("os.name").toLowerCase();

		if (OS.contains("win")) {
			home = System.getenv("appdata");
		} else if (OS.contains("mac")) {
			home += "~/Library/Application Support";
		} else if (OS.contains("nix") || OS.contains("aix")) {
			home += "~/.";
		}

		File dir = new File(home);
		dir = new File(dir, AppDataRoot);

		if (!dir.exists()) {
			dir.mkdir();
			System.out.println("Creating parent directory");
		}
		
		InitializeDirectoryStructure(dir.getAbsolutePath());
		
		return dir;
	}
	
	private static void InitializeDirectoryStructure(String appdatadir) {
		for (String p : subdirs) {
			File dir = new File(appdatadir + p + "/");
			if (!dir.exists()) {
				dir.mkdirs();
				System.out.println("... Subdirectory \"" + p + "\" created.");
			}
		}
	}
	
	public static void DeleteDir(File index) {
		String[] entries = index.list();
		for (String s : entries) {
			File currentFile = new File(index.getPath(), s);
			currentFile.delete();
		}
		index.delete();
	}
	
	  public static File unpackArchive(URL url, File targetDir) throws IOException {
	      if (!targetDir.exists()) {
	          targetDir.mkdirs();
	      }
	      InputStream in = new BufferedInputStream(url.openStream(), 1024);
	      File zip = File.createTempFile("arc", ".zip", targetDir);
	      OutputStream out = new BufferedOutputStream(new FileOutputStream(zip));
	      copyInputStream(in, out);
	      out.close();
	      return unpackArchive(zip, targetDir);
	  }
 
	  public static File unpackArchive(File theFile, File targetDir) throws IOException {
	      if (!theFile.exists()) {
	          throw new IOException(theFile.getAbsolutePath() + " does not exist");
	      }
	      if (!buildDirectory(targetDir)) {
	          throw new IOException("Could not create directory: " + targetDir);
	      }
	      ZipFile zipFile = new ZipFile(theFile);
	      for (Enumeration entries = zipFile.entries(); entries.hasMoreElements();) {
	          ZipEntry entry = (ZipEntry) entries.nextElement();
	          File file = new File(targetDir, File.separator + entry.getName());
	          if (!buildDirectory(file.getParentFile())) {
	              throw new IOException("Could not create directory: " + file.getParentFile());
	          }
	          if (!entry.isDirectory()) {
	              copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
	          } else {
	              if (!buildDirectory(file)) {
	                  throw new IOException("Could not create directory: " + file);
	              }
	          }
	      }
	      zipFile.close();
	      return theFile;
	  }

	  public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
	      byte[] buffer = new byte[1024];
	      int len = in.read(buffer);
	      while (len >= 0) {
	          out.write(buffer, 0, len);
	          len = in.read(buffer);
	      }
	      in.close();
	      out.close();
	  }

	  public static boolean buildDirectory(File file) {
	      return file.exists() || file.mkdirs();
	  }

}
