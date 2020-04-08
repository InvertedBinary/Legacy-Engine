package com.IB.LE2._GL.AlphaLWJGL.util;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

import com.IB.LE2.Boot;

@SuppressWarnings("unused")
public class Shader
{
	private String vertexPath, fragPath = "";
	private String internalShaderPath = "/shaders/";
	private int shaderProgramID = 0;
    private int vertShader = 0, fragShader = 0;
	private ResourceManager utils = new ResourceManager();

	private String shaderName = ""; /** Remains as default if the shaders do NOT share a parent folder (and subfolder(s)) within res/shaders **/
	
	private int createShadersRunCount = 0;
	
	public Shader(String vertexPath, String fragPath)
	{
		this.vertexPath = vertexPath;
		this.fragPath = fragPath;
		for (int i = 0; i < vertexPath.length(); i++) {
			if (fragPath.toCharArray()[i] == vertexPath.toCharArray()[i]) {
				shaderName += vertexPath.toCharArray()[i];
			}
		}
	}
	
	public Shader(String name)
	{
		this.vertexPath = internalShaderPath + "/" + name + "/" + name + ".vert";
		this.fragPath =   internalShaderPath + "/" + name + "/" + name + ".frag";

	}

	public int getShaderProgram()
	{
		if (shaderProgramID == 0) 
			createShaders(true);
		
		
		return this.shaderProgramID;
	}

	public void use()
	{
		validateShaderStatus(true);
		glUseProgram(shaderProgramID);
	}

	public void validateShaderStatus(boolean attemptFix)
	{
		if (this.createShadersRunCount > 0)		
		{
			if (shaderProgramID == 0)
				Boot.log("WARNING: INVALID SHADER PRGM!", "Shader", true);
			
			if (vertShader == 0)
				Boot.log("WARNING: INVALID VERT SHADER! [" + this.vertexPath + "]", "Shader", true);
			
			if (fragShader == 0)
				Boot.log("WARNING: INVALID FRAG SHADER! [" + this.fragPath + "]", "Shader", true);
		} else 
		{
			Boot.log("INFO: A SHADER PROGRAM WILL BE INITIALIZED!", false);
		}
		
		if (vertShader == 0 || fragShader == 0 || shaderProgramID == 0)
			createShaders(true);
	}
	
	public void createShaders(boolean create_program)
	{
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
        
        if (create_program)
        	createProgram();
        
        
        createShadersRunCount++;
	}
	
	public void createProgram()
	{
	    shaderProgramID = glCreateProgram();
	    glAttachShader(shaderProgramID, vertShader);
	    glAttachShader(shaderProgramID, fragShader);
	    glLinkProgram(shaderProgramID);
	    
	    glDeleteShader(vertShader);
	    glDeleteShader(fragShader);
	}
	
	public int compileShader(String filename, int shaderType) throws Exception
	{
		int shader = 0;
		try {
			shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

			if (shader == 0) return 0;

			ARBShaderObjects.glShaderSourceARB(shader, utils.readFileAsString(filename));
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
			return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
		}
	
	public void setFloats(String name, float... values) {
		if (values.length > 4) {
			setFloats(name, values[0], values[1], values[2], values[3]);
			return;
		}
		
		switch (values.length)
		{
		case 1:
		    glUniform1f(glGetUniformLocation(shaderProgramID, name), values[0]); 
		    break;
		case 2:
		    glUniform2f(glGetUniformLocation(shaderProgramID, name), values[0], values[1]); 
		    break;
		case 3:
		    glUniform3f(glGetUniformLocation(shaderProgramID, name), values[0], values[1], values[2]); 
		    break;
		case 4:
		    glUniform4f(glGetUniformLocation(shaderProgramID, name), values[0], values[1], values[2], values[3]); 
		    break;
		default:
			System.out.println("Error setting uniform floats: Too many values!");
			break;
		}
	}
	
	public void setInts(String name, int... values) {
		if (values.length > 4) {
			setInts(name, values[0], values[1], values[2], values[3]);
			return;
		}
		
		switch (values.length)
		{
		case 1:
		    glUniform1i(glGetUniformLocation(shaderProgramID, name), values[0]); 
		    break;
		case 2:
		    glUniform2i(glGetUniformLocation(shaderProgramID, name), values[0], values[1]); 
		    break;
		case 3:
		    glUniform3i(glGetUniformLocation(shaderProgramID, name), values[0], values[1], values[2]); 
		    break;
		case 4:
		    glUniform4i(glGetUniformLocation(shaderProgramID, name), values[0], values[1], values[2], values[3]); 
		    break;
		default:
			System.out.println("Error setting uniform ints: Too many values!");
			break;
		}
	}
	
	public void setBools(String name, boolean... values) {
		if (values.length > 4) {
			setBools(name, values[0], values[1], values[2], values[3]);
			return;
		}
		
		switch (values.length)
		{
		case 1:
		    glUniform1i(glGetUniformLocation(shaderProgramID, name), intValue(values[0])); 
		    break;
		case 2:
		    glUniform2i(glGetUniformLocation(shaderProgramID, name), intValue(values[0]), intValue(values[1])); 
		    break;
		case 3:
		    glUniform3i(glGetUniformLocation(shaderProgramID, name), intValue(values[0]), intValue(values[1]), intValue(values[2])); 
		    break;
		case 4:
		    glUniform4i(glGetUniformLocation(shaderProgramID, name), intValue(values[0]), intValue(values[1]), intValue(values[2]), intValue(values[3])); 
		    break;
		}
	}
	
	public void setMat4f(String name, Matrix4f val)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		int location = glGetUniformLocation(this.getShaderProgram(), name);
		glUniformMatrix4fv(location, false, (val.get(buffer)));
	}
	
	public void setVec3(String name, Vector3f val)
	{
		int location = glGetUniformLocation(this.getShaderProgram(), name);
		glUniform3f(location, val.x, val.y, val.z);
	}
	
	public void setVec3(String name, float x, float y, float z)
	{
		setVec3(name, new Vector3f(x, y, z));
	}
	
	public int intValue(boolean val)
	{
		return (val) ? 1 : 0;
	}
}
