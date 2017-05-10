/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lição05;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

/**
 *
 * @author nynha
 */
public class MainLição05 {
    public static final int HEIGHT = 480;
    public static final int WIDTH = 640;
    public static final Logger LOGGER = Logger.getLogger(MainLição05.class.getName());
    LTexture gCheckerBoardTexture;
    
    public static void main(String[] args) {
        MainLição05 main = null;
        try {
          main = new MainLição05();
          main.create();
          main.run();
        }
        catch(Exception ex) {
          LOGGER.log(Level.SEVERE,ex.toString(),ex);
        }
        finally {
          if(main != null) {
            main.destroy();
          }
        }
    }
    
    public void create() throws LWJGLException {
        //Display
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
        Display.setFullscreen(false);
        Display.setTitle("Lição 05");
        Display.create();

        //OpenGL
        initGL();
        
        //Load
        loadMedia();
    }

    public void destroy() {
        //Métodos checados antes de destruir
        Display.destroy();
    }

    public boolean initGL() {
        //Set the viewport
        glViewport(0, 0, WIDTH, HEIGHT);

        //Initialize Projection Matrix
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, WIDTH, HEIGHT, 0.0, 1.0, -1.0);

        //Initialize Modelview Matrix
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        //Initialize clear color
        glClearColor(0.f, 0.f, 0.f, 1.f);

        //Enable texturing
        glEnable(GL_TEXTURE_2D);

        //Checagem de erros
        int erro = glGetError();
        if(erro != GL_NO_ERROR)
        {
            System.out.println("Erro de inicialização do OpenGL");
            return false;
        }
        return true;
    }

    public boolean loadMedia()
    {
        //Checkerboard pixels
        int CHECKERBOARD_WIDTH = 128;
        int CHECKERBOARD_HEIGHT = 128;
        int CHECKERBOARD_PIXEL_COUNT = CHECKERBOARD_WIDTH*CHECKERBOARD_HEIGHT;
        int checkerBoard[];
        checkerBoard = new int[CHECKERBOARD_PIXEL_COUNT];

        //Go through pixels
        for(int i=0; i<CHECKERBOARD_PIXEL_COUNT; ++i)
        {
            //Get the individual color components
            byte[] colors;
            String teste;
            teste = checkerBoard.toString();
            colors = teste.getBytes();

            //If the 5th bit of the x and y offsets of the pixel do not match
            if((((i/128) & (16^i/128) & 16) != 0))
            {
                //Set pixel to white
                colors[0] = (byte) 0xFF;
                colors[1] = (byte) 0xFF;
                colors[2] = (byte) 0xFF;
                colors[3] = (byte) 0xFF;
            }
            else
            {
                //Set pixel to red
                colors[0] = (byte) 0xFF;
                colors[1] = 0x00;
                colors[2] = 0x00;
                colors[3] = (byte) 0xFF;
            }
        }

        //Load texture
        if(!gCheckerBoardTexture.loadTextureFromPixels32(checkerBoard, CHECKERBOARD_WIDTH, CHECKERBOARD_HEIGHT))
        {
            System.out.println( "Unable to load checkerboard texture!" );
            return false;
        }
        return true;
    }
    
    public void render() throws LWJGLException {
        //Clear color buffer
        glClear(GL_COLOR_BUFFER_BIT);

        //Calculate centered offsets
        float x = (WIDTH - gCheckerBoardTexture.textureWidth()) / 2.f;
        float y = (HEIGHT - gCheckerBoardTexture.textureHeight()) / 2.f;

        //Render checkerboard texture
        gCheckerBoardTexture.render(x, y);
        
        //Atualizando a tela
        Display.swapBuffers();
    }
      
    public void run() throws LWJGLException, InterruptedException {    
        while(!Display.isCloseRequested()) {
            //System.out.println(Display.isVisible());
            if(Display.isVisible()){
              render();
              update();             
            }
            else {
              if(Display.isDirty()) {
                render();
              }
              try {
                Thread.sleep(100);
              }
              catch(InterruptedException ex) {
              }
            }
            Display.sync(10); 
            Display.update();            
          }
    }

    public void update() {
      
    }
}
