package com.IB.SL.AlphaLWJGL;

import static com.IB.SL.AlphaLWJGL.util.ResourceManager.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import com.IB.SL.AlphaLWJGL.util.Camera;
import com.IB.SL.AlphaLWJGL.util.Shader;
import com.IB.SL.AlphaLWJGL.util.TextureHandler;

@SuppressWarnings("unused")
public class OGL_TEST
{
	String title = "";
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	public static float ASPECT = (float)WIDTH / (float)HEIGHT;
	private long window;

	//camera
	public static Camera camera = new Camera(new Vector3f(0.0f, 0.0f, 3.0f));
	static float lastX = WIDTH / 2.0f;
	static float lastY = HEIGHT / 2.0f;
	static boolean firstMouse = true;
	
	//timing
	float deltaTime = 0f;
	float lastFrame = 0f;
	
	//lighting
	public Vector3f lightPos = new Vector3f(1.2f, 1.0f, 2.0f);
	
	public OGL_TEST(String title)
	{
		System.out.println(this.title);
		this.title = title + " (OGL :: " + Version.getVersion() + ")";
		initGLFW();
		init();
	}

	public void initGLFW()
	{
		if (!GLFW.glfwInit()) throw new IllegalStateException("UNABLE TO INIT GLFW");

		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE);

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
	    
	    //glfwSetWindowIcon(window, null);
		
		GL.createCapabilities();
		GLUtil.setupDebugMessageCallback();
	}

	public void init()
	{
		
		glEnable(GL_DEPTH_TEST);

		Shader shaders = new Shader("screen");
		Shader lampShader = new Shader("lamp");

		float vertices[] = {
				// positions // colors // texture coords
				0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // top right
				0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // bottom right
				-0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // bottom left
				-0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f // top left
		};
		
		float cubeVerts[] = {
		        //positions         //normals         //texture coords
		        -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  0.0f,
		         0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  0.0f,
		         0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  1.0f,
		         0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  1.0f,
		        -0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  1.0f,
		        -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  0.0f,

		        -0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  0.0f,
		         0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  0.0f,
		         0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,
		         0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,
		        -0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  1.0f,
		        -0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  0.0f,

		        -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  0.0f,
		        -0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  1.0f,
		        -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
		        -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
		        -0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  0.0f,
		        -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  0.0f,

		         0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  0.0f,
		         0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  1.0f,
		         0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
		         0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
		         0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  0.0f,
		         0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  0.0f,

		        -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  1.0f,
		         0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  1.0f,
		         0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  0.0f,
		         0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  0.0f,
		        -0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  0.0f,
		        -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  1.0f,

		        -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  1.0f,
		         0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  1.0f,
		         0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  0.0f,
		         0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  0.0f,
		        -0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  0.0f,
		        -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  1.0f
			};
		
		
		int indices[] = { 0, 1, 3, 1, 2, 3 };
		float texCoords[] = { 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f };

		int diffuseMap = TextureHandler.createTexture("GL_Textures/container2.png", GL_TEXTURE0);
		int specularMap = TextureHandler.createTexture("GL_Textures/container2_spec.png", GL_TEXTURE1);

		
		int VBO, VAO, EBO;
		VAO = glGenVertexArrays();
		VBO = glGenBuffers();
		//EBO = glGenBuffers();
		glBindVertexArray(VAO);
		

		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, cubeVerts, GL_STATIC_DRAW);

		glBindVertexArray(VAO);
		//glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		//glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

	    //position
	    glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * FLOAT_SIZE, 0);
	    glEnableVertexAttribArray(0);
	    
	    //lighting normal vector
	    glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * FLOAT_SIZE, (3 * FLOAT_SIZE));
	    glEnableVertexAttribArray(1);
	   
	    //texture
	    glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * FLOAT_SIZE, (6 * FLOAT_SIZE));
	    glEnableVertexAttribArray(2);
	    
		int lightVAO = glGenVertexArrays();
		glBindVertexArray(lightVAO);
		
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		
	    glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * FLOAT_SIZE, 0);
		glEnableVertexAttribArray(0);
		
		//glBindVertexArray(0);

		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); //WIREFRAME
		
	    shaders.use();
	    shaders.setInts("material.diffuse", 0);
	    shaders.setInts("material.specular", 1);

	    
		while (!glfwWindowShouldClose(window)) {
			float currentFrame = (float) glfwGetTime();
			deltaTime = currentFrame - lastFrame;
			lastFrame = currentFrame;
			
			processInput(window);
			
			//glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
	        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			shaders.use();
			//x^2 + y^2 = 1
			//x^2 = 1 - y^2
			//y^2 = 1 - x^2
			lightPos.x = (float)Math.cos(glfwGetTime()) * (float)(glfwGetTime() / 4);
			lightPos.y = (float)Math.sin(glfwGetTime()) * 1; 
			lightPos.z = (float)Math.sin(glfwGetTime()) * 1; 

			//shaders.setFloats("asp_rat",ASP_RATIO);
			shaders.setVec3("light.position", lightPos);
			shaders.setVec3("viewPos", camera.Position);
			
			shaders.setFloats("material.shininess", 64.0f);
			
			shaders.setVec3("light.ambient", 0.2f, 0.2f, 0.2f);
			shaders.setVec3("light.diffuse", 0.5f, 0.5f, 0.5f);
			shaders.setVec3("light.specular", 1.0f, 1.0f, 1.0f);
			
			Matrix4f view = new Matrix4f();
			view = camera.getViewMatrix();
			shaders.setMat4f("view", view);
			
			Matrix4f projection = new Matrix4f();
			projection = projection.perspective((float) Math.toRadians(camera.Fov), ASPECT, 0.1f, 100.0f);
			shaders.setMat4f("projection", projection);
			
			Matrix4f model = new Matrix4f();
			//model = model.rotate((float) Math.toRadians(glfwGetTime() * 50), new Vector3f(0.2f, 0.5f, 0.1f));
			shaders.setMat4f("model", model);
			
	        // bind diffuse map
	        glActiveTexture(GL_TEXTURE0);
	        glBindTexture(GL_TEXTURE_2D, diffuseMap);
	        // bind specular map
	        glActiveTexture(GL_TEXTURE1);
	        glBindTexture(GL_TEXTURE_2D, specularMap);
			
			glBindVertexArray(VAO);
			glDrawArrays(GL_TRIANGLES, 0, 36);

			lampShader.use();
			lampShader.setMat4f("projection", projection);
			lampShader.setMat4f("view", view);
			model = new Matrix4f();
			model = model.translate(lightPos);
			//model = model.rotate(-(float) Math.toRadians(-55.0f), new Vector3f(1.0f, 0.0f, 0.0f)); 
			model.scale(new Vector3f(0.2f));
			lampShader.setMat4f("model", model);
			glBindVertexArray(lightVAO);
			glDrawArrays(GL_TRIANGLES, 0, 36);
			
			//glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
			//glDrawArrays(GL_TRIANGLES, 0, 3);

			
			glfwSwapBuffers(window);
			glfwPollEvents();
		}

		glDeleteVertexArrays(VAO);
		glDeleteVertexArrays(lightVAO);
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
	    if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.ROT_LEFT, deltaTime);
	    if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.ROT_RIGHT, deltaTime);
	   
	    if (glfwGetKey(window, GLFW_KEY_KP_4) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_7) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_1) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.ROT_LEFT, deltaTime);
	    if (glfwGetKey(window, GLFW_KEY_KP_6) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_9) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_3) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.ROT_RIGHT, deltaTime);
	    if (glfwGetKey(window, GLFW_KEY_KP_8) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_7) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_9) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.ROT_UP, deltaTime);
	    if (glfwGetKey(window, GLFW_KEY_KP_2) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_1) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_KP_3) == GLFW_PRESS)
	        camera.ProcessKeyboard(Camera.ROT_DOWN, deltaTime);
	    
	    
	    
	    if (glfwGetKey(window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS)
	    	camera.leftControl = true;
	    
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
			OGL_TEST.WIDTH = width;
			OGL_TEST.HEIGHT = height;
			OGL_TEST.ASPECT = (float)((float)WIDTH / (float)HEIGHT);
			glViewport(0, 0, width, height);
		}
	};

}
