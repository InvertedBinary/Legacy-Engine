package com.IB.SL.AlphaLWJGL.util;

import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL46.*;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class TextureHandler
{
	   public static int loadTexture(BufferedImage image, int GL_TEX){
			      
			      int[] pixels = new int[image.getWidth() * image.getHeight()];
			        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

			        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * ResourceManager.BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
			        
			        for(int y = 0; y < image.getHeight(); y++){
			            for(int x = 0; x < image.getWidth(); x++){
			                int pixel = pixels[y * image.getWidth() + x];
			                buffer.put((byte) ((pixel >> 16) & 0xFF));    // Red component
			                buffer.put((byte) ((pixel >> 8) & 0xFF));     // Green component
			                buffer.put((byte) (pixel & 0xFF));            // Blue component
			                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
			            }
			        }

			        buffer.flip();

			        int textureID = glGenTextures(); //Generate texture ID

			        glActiveTexture(GL_TEX);
			        glBindTexture(GL_TEXTURE_2D, textureID);
					
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
	   
	   public static int createTexture(String res, int GL_TEX)
	   {
		        BufferedImage texture = null;
					try {
						texture = ImageIO.read(ResourceManager.getResourceAsFile(res));
					} catch (IOException e) {
						e.printStackTrace();
					}
		        
		        int textureID = TextureHandler.loadTexture(texture, GL_TEX);
		        
		        return textureID;
	   }
	   
	   public static int createTexture(String res)
	   {
		   return createTexture(res, GL_TEXTURE0);
	   }	   
	   
}
