/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lição06;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
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
public class MainLição06 {
     public static final int HEIGHT = 480;
    public static final int WIDTH = 640;
    public static final Logger LOGGER = Logger.getLogger(MainLição06.class.getName());
    LTexture06 gLoadedTexture;
    
    public static void main(String[] args) {
        MainLição06 main = null;
        try {
          main = new MainLição06();
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
    
    public void create() throws LWJGLException, IOException {
        //Display
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
        Display.setFullscreen(false);
        Display.setTitle("Lição 06");
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
        glOrtho( 0.0, WIDTH, HEIGHT, 0.0, 1.0, -1.0);

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

    public boolean loadMedia() throws IOException
    {
        //Load texture
        if(!gLoadedTexture.loadTextureFromFile("texture.png"))
        {
            System.out.println( "Não abriu imagem" );
            return false;
        }

        return true;
    }
    
    public void render() throws LWJGLException {
        //Clear color buffer
        glClear(GL_COLOR_BUFFER_BIT);

        //Calculate centered offsets
        float x = (WIDTH - gLoadedTexture.textureWidth()) / 2.f;
        float y = (HEIGHT - gLoadedTexture.textureHeight()) / 2.f;

        //Render texture
        gLoadedTexture.render( x, y );

        //Atualizando a tela
        Display.swapBuffers();
    }
      
    public void run() throws LWJGLException, InterruptedException {    
        while(!Display.isCloseRequested()) {
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
