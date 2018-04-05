package com.IB.SL.AlphaLWJGL.util;

import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

public class Shader
{
	private String vertexPath, fragPath = "";
	private int shaderProgram = 0;
	private IO io = new IO();
	
	public Shader(String vertexPath, String fragPath)
		{
			this.vertexPath = vertexPath;
			this.fragPath = fragPath;
		}
	
	public int getShaderProgram()
	{
			if (shaderProgram == 0)
				createShaders();
			
			return this.shaderProgram;
	}
	
	public void createShaders()
	{
		    int vertShader = 0, fragShader = 0;
	        try {
	            vertShader = compileShader(vertexPath, ARBVertexShader.GL_VERTEX_SHADER_ARB);
	            fragShader = compileShader(fragPath, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
	        }
	        catch(Exception exc) {
	            exc.printStackTrace();
	            return;
	        }
	        finally {
	            if(vertShader == 0 || fragShader == 0) {
	            	System.out.println("Failed to create shaders!");
	                return;
	            }
	        }
	        
	        shaderProgram = glCreateProgram();
	        glAttachShader(shaderProgram, vertShader);
	        glAttachShader(shaderProgram, fragShader);
	        glLinkProgram(shaderProgram);
	        
	        glDeleteShader(vertShader);
	        glDeleteShader(fragShader);
	}
	
	public int compileShader(String filename, int shaderType) throws Exception
		{
			int shader = 0;
			try {
				shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

				if (shader == 0) return 0;

				ARBShaderObjects.glShaderSourceARB(shader, io.readFileAsString(filename));
				ARBShaderObjects.glCompileShaderARB(shader);

				if (ARBShaderObjects.glGetObjectParameteriARB(shader,
						ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
					throw new RuntimeException("Error creating shader: " + getLogInfo(shader));

				return shader;
			} catch (Exception exc) {
				ARBShaderObjects.glDeleteObjectARB(shader);
				throw exc;
			}
		}
	
	private static String getLogInfo(int obj)
		{
			return ARBShaderObjects.glGetInfoLogARB(obj,
					ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
		}
	
}
