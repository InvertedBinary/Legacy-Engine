package com.IB.SL.AlphaLWJGL.util;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL14.GL_MIRRORED_REPEAT;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class TextureHandler
{
	   public static int loadTexture(BufferedImage image){
			      
			      int[] pixels = new int[image.getWidth() * image.getHeight()];
			        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

			        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * Utils.BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
			        
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
}
