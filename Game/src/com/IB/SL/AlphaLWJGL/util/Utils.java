package com.IB.SL.AlphaLWJGL.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static com.IB.SL.AlphaLWJGL.util.Utils.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.openvr.Texture;
import org.lwjgl.stb.STBImage;

import com.IB.SL.AlphaLWJGL.util.Utils;
import com.IB.SL.AlphaLWJGL.util.Shader;

public class Utils
{
	public final static int FLOAT_SIZE = 4;
	
	   private static final int BYTES_PER_PIXEL = 4;
	   public static int loadTexture(BufferedImage image){
	      
	      int[] pixels = new int[image.getWidth() * image.getHeight()];
	        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

	        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
	        
	        for(int y = 0; y < image.getHeight(); y++){
	            for(int x = 0; x < image.getWidth(); x++){
	                int pixel = pixels[y * image.getWidth() + x];
	                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
	                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
	                buffer.put((byte) (pixel & 0xFF));               // Blue component
	                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
	            }
	        }

	        buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

	        int textureID = glGenTextures(); //Generate texture ID
	        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
	        
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
	        /*float borderColor[] = { 1.0f, 1.0f, 0.0f, 1.0f };
	        glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor); */
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	        
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

	        //Send texel data to OpenGL
	        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	        glGenerateMipmap(GL_TEXTURE_2D);
	        
	        
	        //Return the texture ID so we can bind it later again
	      return textureID;
}
	   
	   public static File getResourceAsFile(String resourcePath) {
			    try {
			        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
			        if (in == null) {
			            return null;
			        }

			        File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
			        tempFile.deleteOnExit();

			        try (FileOutputStream out = new FileOutputStream(tempFile)) {
			            //copy stream
			            byte[] buffer = new byte[1024];
			            int bytesRead;
			            while ((bytesRead = in.read(buffer)) != -1) {
			                out.write(buffer, 0, bytesRead);
			            }
			        }
			        return tempFile;
			    } catch (IOException e) {
			        e.printStackTrace();
			        return null;
			    }
			}

	public String readFileAsString(String filename) throws Exception
		{
			StringBuilder source = new StringBuilder();
			InputStream in = Utils.class.getResourceAsStream(filename);;
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
