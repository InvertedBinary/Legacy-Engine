package com.IB.SL.AlphaLWJGL.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

public class IO
{

	public String readFileAsString(String filename) throws Exception
		{
			StringBuilder source = new StringBuilder();
			InputStream in = IO.class.getResourceAsStream(filename);;
			Exception exception = null;
			BufferedReader reader;
			
			try {
				reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

				Exception innerExc = null;
				try {
					String line;
					while ((line = reader.readLine()) != null)
						source.append(line).append('\n');
				} catch (Exception exc) {
					exception = exc;
				} finally {
					try {
						reader.close();
					} catch (Exception exc) {
						if (innerExc == null)
							innerExc = exc;
						else
							exc.printStackTrace();
					}
				}

				if (innerExc != null) throw innerExc;
			} catch (Exception exc) {
				exception = exc;
			} finally {
				try {
					in.close();
				} catch (Exception exc) {
					if (exception == null)
						exception = exc;
					else
						exc.printStackTrace();
				}

				if (exception != null) throw exception;
			}

			return source.toString();
		}
}
