package com.IB.SL.AlphaLWJGL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;
import static com.IB.SL.AlphaLWJGL.util.ResourceManager.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.GLUtil;

import com.IB.SL.AlphaLWJGL.util.Camera;
import com.IB.SL.AlphaLWJGL.util.ResourceManager;
import com.IB.SL.AlphaLWJGL.util.Shader;
import com.IB.SL.AlphaLWJGL.util.TextureHandler;
import org.joml.*;
import org.joml.Math;

public class OGLEngine
{
	String title = "";
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	private long window;

	public static Camera camera = new Camera(new Vector3f(0.0f, 0.0f, 3.0f));
	static float lastX = WIDTH / 2.0f;
	static float lastY = HEIGHT / 2.0f;
	static boolean firstMouse = true;
	
	float deltaTime = 0f;
	float lastFrame = 0f;
	
	Vector3f cameraPos   = new Vector3f(0.0f, 0.0f,  3.0f);
	Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
	Vector3f cameraUp    = new Vector3f(0.0f, 1.0f,  0.0f);
	
	public OGLEngine(String title)
	{
		System.out.println(this.title);
		this.title = title + " (OGL :: " + Version.getVersion() + ")";
		initGLFW();
		init();
	}

	public void initGLFW()
	{
		if (!GLFW.glfwInit()) throw new IllegalStateException("UNABLE TO INIT GLFW");

		glfwDefaultWindowHints();
		window = glfwCreateWindow(WIDTH, HEIGHT, title, NULL, NULL);

		if (window == NULL) {
			System.err.println("Creation of GLFW Window Failed!");
			glfwTerminate();
			return;
		}

		glfwMakeContextCurrent(window);
		glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);
		glfwSetCursorPosCallback(window, mouse_callback);
		glfwSetScrollCallback(window, scroll_callback);
		
	    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		
		GL.createCapabilities();
		GLUtil.setupDebugMessageCallback();
	}

	public void init()
	{
		
		glEnable(GL_DEPTH_TEST);

		Shader shaders = new Shader("/shaders/screen.vert", "/shaders/screen.frag");
		shaders.getShaderProgram();

		float vertices[] = {
				// positions // colors // texture coords
				0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // top right
				0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // bottom right
				-0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // bottom left
				-0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f // top left
		};
		
		float cubeVerts[] = {
			    -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
			     0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
			     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
			     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
			    -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
			    -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

			    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
			     0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
			     0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
			     0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
			    -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
			    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

			    -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
			    -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
			    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
			    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
			    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
			    -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

			     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
			     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
			     0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
			     0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
			     0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
			     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

			    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
			     0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
			     0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
			     0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
			    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
			    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

			    -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
			     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
			     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
			     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
			    -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
			    -0.5f,  0.5f, -0.5f,  0.0f, 1.0f
			};
		
		Vector3f cubePositions[] = {
				  new Vector3f( 0.0f,  0.0f,  0.0f), 
				  new Vector3f( 2.0f,  5.0f, -15.0f), 
				  new Vector3f(-1.5f, -2.2f, -2.5f),  
				  new Vector3f(-3.8f, -2.0f, -12.3f),  
				  new Vector3f( 2.4f, -0.4f, -3.5f),  
				  new Vector3f(-1.7f,  3.0f, -7.5f),  
				  new Vector3f( 1.3f, -2.0f, -2.5f),  
				  new Vector3f( 1.5f,  2.0f, -2.5f), 
				  new Vector3f( 1.5f,  0.2f, -1.5f), 
				  new Vector3f(-1.3f,  1.0f, -1.5f)  
				};

		int indices[] = { 0, 1, 3, 1, 2, 3 };

		float texCoords[] = { 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f };

		TextureHandler.createTexture("GL_Textures/wall.jpg");

		int VBO, VAO, EBO;
		VAO = glGenVertexArrays();
		VBO = glGenBuffers();
		//EBO = glGenBuffers();
		glBindVertexArray(VAO);

		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, cubeVerts, GL_STATIC_DRAW);

		//glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		//glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

	    //position
	    glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * FLOAT_SIZE, 0);
	    glEnableVertexAttribArray(0);
	    //textures
	    glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * FLOAT_SIZE, (3 * FLOAT_SIZE));
	    glEnableVertexAttribArray(1);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); //WIREFRAME
		
		//Camera Direction
		Vector3f cameraTarget = new Vector3f(0f, 0f, 0f);
		Vector3f cameraDir = new Vector3f().normalize(new Vector3f().sub(cameraPos, cameraTarget));
		
		//Right Axis
		Vector3f up = new Vector3f(0f, 1f, 0f);
		Vector3f cameraRight = new Vector3f().normalize(new Vector3f().cross(up, cameraDir));
		
		//Up Axis
		Vector3f cameraUp = new Vector3f().cross(cameraDir, cameraRight);
		
		Matrix4f view = new Matrix4f().lookAt(new Vector3f(0.0f, 0.0f, 3.0f), 
		   	 new Vector3f(0.0f, 0.0f, 0.0f), 
		     new Vector3f(0.0f, 1.0f, 0.0f));
		
		while (!glfwWindowShouldClose(window)) {
			float currentFrame = (float) glfwGetTime();
			deltaTime = currentFrame - lastFrame;
			lastFrame = currentFrame;
			
			processInput(window);

			glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			shaders.use();
			
			/*float radius = 10.0f;
			float camX = (float) (Math.sin(glfwGetTime()) * radius);
			float camZ = (float) (Math.cos(glfwGetTime()) * radius);
			view = new Matrix4f().lookAt(
					new Vector3f(camX, 0.0f, camZ), 
					new Vector3f(0.0f, 0.0f, 0.0f), 
					new Vector3f(0.0f, 1.0f, 0.0f));  */
			
			/*cameraPos = new Vector3f(0f, 0f, 3f);
			cameraFront = new Vector3f(0f, 0f, -1f);
			cameraUp = new Vector3f(0f, 1f, 0f);*/
			
			//System.out.println("POS: " + cameraPos);
			
	//		Vector3f dest = new Vector3f();
//			cameraPos.add(cameraFront, dest);
			//view = new Matrix4f().lookAt(cameraPos, dest, cameraUp);
			
			view = new Matrix4f();
			//System.out.println("VIEW: \n" + view);
			view = camera.getViewMatrix();
			//view = view.lookAt(cameraPos, cameraPos.add(cameraPos, cameraFront), cameraUp);
			//System.out.println("VIEW 2: \n" + view);
			shaders.setMat4f("view", view);
			
			Matrix4f projection = new Matrix4f();
			projection = projection.perspective((float) Math.toRadians(camera.Fov), WIDTH / HEIGHT, 0.1f, 100.0f);
			shaders.setMat4f("projection", projection);
			
			
			
			
			glBindVertexArray(VAO);

			for (int i = 0; i < 10; i++) {
				Matrix4f model = new Matrix4f();
				model = model.translate(cubePositions[i]);
				//model = model.rotate(-(float) Math.toRadians(-55.0f), new Vector3f(1.0f, 0.0f, 0.0f)); 
				model = model.rotate((float) (Math.toRadians(glfwGetTime() * (i - 5) * 1)), new Vector3f(1f, 0.3f, 0.5f));  
				shaders.setMat4f("model", model);
				glDrawArrays(GL_TRIANGLES, 0, 36);
			}
			//glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
			//glDrawArrays(GL_TRIANGLES, 0, 3);

			
			glfwSwapBuffers(window);
			glfwPollEvents();
		}

		glDeleteVertexArrays(VAO);
		glDeleteBuffers(VBO);

		glfwTerminate();
	}

	private void processInput(long window)
	{
		if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
			glfwSetWindowShouldClose(window, true);
	    if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.FORWARD, deltaTime);
	    if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.BACKWARD, deltaTime);
	    if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.LEFT, deltaTime);
	    if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.RIGHT, deltaTime);
	    
	    if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.UP, deltaTime);
	    if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.DOWN, deltaTime);
	    
	    if (glfwGetKey(window, GLFW_KEY_KP_4) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_7) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_1) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.ROT_LEFT, deltaTime);
	    if (glfwGetKey(window, GLFW_KEY_KP_6) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_9) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_3) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.ROT_RIGHT, deltaTime);
	    if (glfwGetKey(window, GLFW_KEY_KP_8) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_7) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_9) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.ROT_UP, deltaTime);
	    if (glfwGetKey(window, GLFW_KEY_KP_2) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_1) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_3) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.ROT_DOWN, deltaTime);
	}

	private static GLFWCursorPosCallback mouse_callback = new GLFWCursorPosCallback()
	{

		@Override
		public void invoke(long window, double xpos, double ypos)
		{
			if (firstMouse)
			{
				lastX = (float) xpos;
				lastY = (float) ypos;
				firstMouse = false;
			}
			
			float xO = (float) (xpos - lastX);
			float yO = (float) (lastY - ypos);
			
			lastX = (float) xpos;
			lastY = (float) ypos;
			
			camera.ProcessMouseMovement(xO, yO, true);
		}
	};
	
	private static GLFWScrollCallback scroll_callback = new GLFWScrollCallback()
		{

			@Override
			public void invoke(long window, double xO, double yO)
			{
				camera.ProcessMouseScroll((float) yO);
			}
		
		};
	
	private static GLFWFramebufferSizeCallback framebuffer_size_callback = new GLFWFramebufferSizeCallback()
	{
		public void invoke(long window, int width, int height)
		{
			OGLEngine.WIDTH = width;
			OGLEngine.HEIGHT = height;
			glViewport(0, 0, width, height);
		}
	};

}
