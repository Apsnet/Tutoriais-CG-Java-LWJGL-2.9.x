/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lição03;

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
import static org.lwjgl.opengl.GL11.glViewport;

/**
 *
 * @author nynha
 */
public class MainLição03 {
    public static final int HEIGHT = 480;
    public static final int WIDTH = 640;
    public static final Logger LOGGER = Logger.getLogger(MainLição03.class.getName());
    public int gViewportMode = 0; //VIEW_MODE_FULL
    
    /*
        VIEWPORT_MODE_FULL = 0
        VIEWPORT_MODE_HALF_CENTER = 1
        VIEWPORT_MODE_HALF_TOP = 2
        VIEWPORT_MODE_QUAD = 3
        VIEWPORT_MODE_RADAR = 4
    */
    
    public static void main(String[] args) {
        MainLição03 main = null;
        try {
          main = new MainLição03();
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
        Display.setTitle("Lição 03");
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
        //Mudar a visão
        glViewport(0, 0, WIDTH, HEIGHT);
        
        //Inicializar a projeção da matriz
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, WIDTH, HEIGHT, 0.0, 1.0, -1.0);
        
        //Inicializar o ModelView da matriz
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        
        //Inicializar a cor
        glClearColor(0.f, 0.f, 0.f, 1.f);
        
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
            //Cycle through viewport modes
            gViewportMode++;
            if(gViewportMode > 4)
                gViewportMode = 0;           
        }
    }
    
    public void render() throws LWJGLException {
        //Clear color buffer
        glClear(GL_COLOR_BUFFER_BIT);
        
        //Reset ModelView Matrix
        glLoadIdentity();
        
        //Move to center of the display
        glTranslatef(WIDTH / 2.f, HEIGHT / 2.f, 0.f);
        
        //Full View
        if(gViewportMode == 0) //VIEWPORT_MODE_FULL
        {
            //Preencher a tela
            glViewport(0, 0, WIDTH, HEIGHT);
            
            //Quadrado vermelho
            glBegin(GL_QUADS);
                glColor3f(1.f, 0.f, 0.f);
                glVertex2f(-WIDTH / 2.f, -HEIGHT / 2.f);
                glVertex2f(WIDTH / 2.f, -HEIGHT / 2.f);
                glVertex2f(WIDTH / 2.f, HEIGHT / 2.f);
                glVertex2f(-WIDTH / 2.f, HEIGHT / 2.f);
            glEnd();
        }
        
        //Visão do centro da tela
        else if(gViewportMode == 1) //VIEWPORT_MODE_HALF_CENTER
        {
            //Centro da visão
            glViewport(WIDTH / 4, HEIGHT / 4, WIDTH / 2, HEIGHT / 2);
            
            //Quadrado verde
            glBegin(GL_QUADS);
                glColor3f(0.f, 1.f, 0.f);
                glVertex2f(-WIDTH / 2.f, -HEIGHT / 2.f);
                glVertex2f(WIDTH / 2.f, -HEIGHT / 2.f);
                glVertex2f(WIDTH / 2.f, HEIGHT / 2.f);
                glVertex2f(-WIDTH / 2.f, HEIGHT / 2.f);
            glEnd();
        }
        
        //Visão centro-topo
        else if(gViewportMode == 2) //VIEWPORT_MODE_HALF_TOP
        {
            //Visão do topo
            glViewport(WIDTH / 4, HEIGHT / 2, WIDTH / 2, HEIGHT / 2);
            
            //Quadrado azul
            glBegin(GL_QUADS);
                glColor3f(0.f, 0.f, 1.f);
                glVertex2f(-WIDTH / 2.f, -HEIGHT / 2.f);
                glVertex2f(WIDTH / 2.f, -HEIGHT / 2.f);
                glVertex2f(WIDTH / 2.f, HEIGHT / 2.f);
                glVertex2f(-WIDTH / 2.f, HEIGHT / 2.f);
            glEnd();
        }
        
        //Visão dos quatro quadrados
        else if(gViewportMode == 3) //VIEWPORT_MODE_QUAD
        {
            //Embaixo esquerda quadrado vermelho
            glViewport(0, 0, WIDTH / 2, HEIGHT / 2);
            glBegin(GL_QUADS);
                glColor3f(1.f, 0.f, 0.f);
                glVertex2f(-WIDTH / 4.f, -HEIGHT / 4.f);
                glVertex2f(WIDTH / 4.f, -HEIGHT / 4.f);
                glVertex2f(WIDTH / 4.f, HEIGHT / 4.f);
                glVertex2f(-WIDTH / 4.f, HEIGHT / 4.f);
            glEnd();
            
            //Embaixo direita quadrado verde
            glViewport(WIDTH / 2, 0, WIDTH / 2, HEIGHT / 2);
            glBegin(GL_QUADS);
                glColor3f(0.f, 1.f, 0.f);
                glVertex2f(-WIDTH / 4.f, -HEIGHT / 4.f);
                glVertex2f(WIDTH / 4.f, -HEIGHT / 4.f);
                glVertex2f(WIDTH / 4.f, HEIGHT / 4.f);
                glVertex2f(-WIDTH / 4.f, HEIGHT / 4.f);
            glEnd();
            
            //Cima esquerda quadrado azul
            glViewport(0, HEIGHT / 2, WIDTH / 2, HEIGHT / 2);
            glBegin(GL_QUADS);
                glColor3f(0.f, 0.f, 1.f);
                glVertex2f(-WIDTH / 4.f, -HEIGHT / 4.f);
                glVertex2f(WIDTH / 4.f, -HEIGHT / 4.f);
                glVertex2f(WIDTH / 4.f, HEIGHT / 4.f);
                glVertex2f(-WIDTH / 4.f, HEIGHT / 4.f);
            glEnd();
            
            //Cima direita quadrado amarelo
            glViewport(WIDTH / 2, HEIGHT / 2, WIDTH / 2, HEIGHT / 2);
            glBegin(GL_QUADS);
                glColor3f(1.f, 1.f, 0.f);
                glVertex2f(-WIDTH / 4.f, -HEIGHT / 4.f);
                glVertex2f(WIDTH / 4.f, -HEIGHT / 4.f);
                glVertex2f(WIDTH / 4.f, HEIGHT / 4.f);
                glVertex2f(-WIDTH / 4.f, HEIGHT / 4.f);
            glEnd();
        }
        
        //Visão com radar
        else if(gViewportMode == 4) //VIEWPORT_MODE_RADAR
        {
            //Tamanho do quadrado grande
            glViewport(0, 0, WIDTH, HEIGHT);
            glBegin(GL_QUADS);
                glColor3f(1.f, 1.f, 1.f);
                glVertex2f(-WIDTH / 8.f, -HEIGHT / 8.f);
                glVertex2f(WIDTH / 8.f, -HEIGHT / 8.f);
                glVertex2f(WIDTH / 8.f, HEIGHT / 8.f);
                glVertex2f(-WIDTH / 8.f, HEIGHT / 8.f);
                glColor3f(0.f, 0.f, 0.f);
                glVertex2f(-WIDTH / 16.f, -HEIGHT / 16.f);
                glVertex2f(WIDTH / 16.f, -HEIGHT / 16.f);
                glVertex2f(WIDTH / 16.f, HEIGHT / 16.f);
                glVertex2f(-WIDTH / 16.f, HEIGHT / 16.f);
            glEnd();
            
            //Quadrado radar
            glViewport(WIDTH / 2, HEIGHT / 2, WIDTH / 2, HEIGHT / 2);
            glBegin(GL_QUADS);
                glColor3f(1.f, 1.f, 1.f);
                glVertex2f(-WIDTH / 8.f, -HEIGHT / 8.f);
                glVertex2f(WIDTH / 8.f, -HEIGHT / 8.f);
                glVertex2f(WIDTH / 8.f, HEIGHT / 8.f);
                glVertex2f(-WIDTH / 8.f, HEIGHT / 8.f);
                glColor3f(0.f, 0.f, 0.f);
                glVertex2f(-WIDTH / 16.f, -HEIGHT / 16.f);
                glVertex2f(WIDTH / 16.f, -HEIGHT / 16.f);
                glVertex2f(WIDTH / 16.f, HEIGHT / 16.f);
                glVertex2f(-WIDTH / 16.f, HEIGHT / 16.f);
            glEnd();
        }
        
        //Atualizando a tela
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