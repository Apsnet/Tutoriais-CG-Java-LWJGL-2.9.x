/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lição02;

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
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 *
 * @author nynha
 */
public class MainLição02 {
    public static final int DISPLAY_HEIGHT = 480;
    public static final int DISPLAY_WIDTH = 640;
    public static final Logger LOGGER = Logger.getLogger(MainLição02.class.getName());
    public int gColorMode = 0; //0 para CYAN e 1 para MULTI
    public float gProjectionScale = 1.f;
    
    public static void main(String[] args) {
        MainLição02 main = null;
        try {
          main = new MainLição02();
          main.create();
          main.run();
        }
        catch(Exception ex) {
          LOGGER.log(Level.SEVERE,ex.toString(),ex);
        }
        /*finally {
          if(main != null) {
            main.destroy();
          }
        }*/
    }
    
    public void create() throws LWJGLException {
        //Display
        Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH,DISPLAY_HEIGHT));
        Display.setFullscreen(false);
        Display.setTitle("Lição 02");
        Display.create();

        //Keyboard
        Keyboard.create();

        //OpenGL
        initGL();
    }

    public void destroy() {
        //Methods already check if created before destroying.
        Keyboard.destroy();
        Display.destroy();
    }

    public boolean initGL() {
        //Iniciar projeção da matriz
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, DISPLAY_WIDTH, DISPLAY_HEIGHT, 0.0, 1.0, -1.0);
        
        //Inicializar ModelView da Matrix
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        
        //Iniciar cor
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        //Checagem de erros
        int erro = glGetError();
        if(erro != GL_NO_ERROR)
        {
            System.out.println("Erro de inicialização do OpenGL");
            return false;
        }
        return true;
    }

    public void processKeyboard() {
        //Se pressionar a letra Q
        if(Keyboard.isKeyDown(Keyboard.KEY_Q))
        {
            //Mudando a cor
            if(gColorMode == 0) //CYAN
                gColorMode = 1; //MULTI
            else
                gColorMode = 0; //CYAN
        }
        //Se pressionar a letra E
        else if (Keyboard.isKeyDown(Keyboard.KEY_E))
        {
            //Muda as escalas
            if(gProjectionScale == 1.f)
                gProjectionScale = 2.f; //Mais zoom
            else if(gProjectionScale == 2.f)
                gProjectionScale = 0.5f; //Menos zoom
            else if(gProjectionScale == 0.5f)
                gProjectionScale = 1.f; //Tamanho normal            
        }
        
        //Atualizando a projeção da matrix
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, DISPLAY_WIDTH * gProjectionScale, DISPLAY_HEIGHT * gProjectionScale, 0.0, 1.0, -1.0);
    }
    
    public void render() throws LWJGLException {
        //Clear color buffer
        glClear(GL_COLOR_BUFFER_BIT);
        
        //Reset ModelView Matrix
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        
        //Move to center of the display
        glTranslatef(DISPLAY_WIDTH / 2.f, DISPLAY_HEIGHT / 2.f, 0.f);
        
        //Render quad
        if(gColorMode == 0) //CYAN
        {
            //Solid Cyan
            glBegin(GL_QUADS);
                glColor3f(0.f, 1.f, 1.f);
                glVertex2f(-50.f, -50.f);
                glVertex2f(50.f, -50.f);
                glVertex2f(50.f, 50.f);
                glVertex2f(-50.f, 50.f);
            glEnd();       
        }
        else
        {
            //RYGB Mix
            glBegin(GL_QUADS);
                glColor3f(1.f, 0.f, 0.f); 
                glVertex2f(-50.f, -50.f);
                glColor3f(1.f, 1.f, 0.f); 
                glVertex2f(50.f, -50.f);
                glColor3f(0.f, 1.f, 0.f); 
                glVertex2f(50.f, 50.f);
                glColor3f(0.f, 0.f, 1.f); 
                glVertex2f(-50.f, 50.f);
            glEnd();
        }
        Display.swapBuffers();
    }
      
    public void run() throws LWJGLException, InterruptedException {    

        while(!Display.isCloseRequested()) {
            //System.out.println(Display.isVisible());
            if(Display.isVisible()){
              processKeyboard();
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
