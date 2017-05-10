/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lição04;

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
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

/**
 *
 * @author nynha
 */
public class MainLição04 {
    public static final int HEIGHT = 480;
    public static final int WIDTH = 640;
    public static final Logger LOGGER = Logger.getLogger(MainLição04.class.getName());
    public float gCameraX = 0.f, gCameraY = 0.f; //Posição inicial da câmera
    
    public static void main(String[] args) {
        MainLição04 main = null;
        try {
          main = new MainLição04();
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
        Display.setTitle("Lição 04");
        Display.create();

        //Keyboard
        Keyboard.create();

        //OpenGL
        initGL();
    }

    public void destroy() {
        //Métodos checados antes de destruir
        Keyboard.destroy();
        Display.destroy();
    }

    public boolean initGL() {
        //Mudar a visão
        glViewport(0, 0, WIDTH, HEIGHT);

        //Inicializa a projeção da matriz
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, WIDTH, HEIGHT, 0.0, 1.0, -1.0);

        //Inicializa o Modelview da matriz
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity(); //Reinicializa as transformações

        //Salva as configurações atuais numa pilha do OpenGL
        glPushMatrix();

        //Inicializa as cores
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
        //Se for cliclado w/a/s/d, vai mudar a visão dos quadrados
        if(Keyboard.isKeyDown(Keyboard.KEY_S))
        {
            gCameraY -= 16.f;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_W))
        {
            gCameraY += 16.f;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_D))
        {
            gCameraX -= 16.f;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_A))
        {
            gCameraX += 16.f;
        }

        //Take saved matrix off the stack and reset it
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix(); //Restaura as transformações salvas pelo glPushMatrix()
        glLoadIdentity();

        //Move a posição dos quadrados
        glTranslatef(-gCameraX, -gCameraY, 0.f);

        //Salva as configurações atuais numa pilha do OpenGL de novo
        glPushMatrix();
    }
    
    public void render() throws LWJGLException {
        //Limpa o buffer de cor
        glClear(GL_COLOR_BUFFER_BIT);

        //Pop default matrix onto current matrix
        glMatrixMode(GL_MODELVIEW); //Especifica a pilha de matrizes que será mexida
        glPopMatrix(); //Restaura as transformações salvas pelo glPushMatrix()

        //Salva as configurações atuais numa pilha do OpenGL de novo
        glPushMatrix();

        //Move o quadradoVermelho para o centro da tela
        glTranslatef(WIDTH / 2.f, HEIGHT / 2.f, 0.f);

        //Quadrado vermelho
        glBegin(GL_QUADS);
            glColor3f(1.f, 0.f, 0.f);
            glVertex2f(-WIDTH / 4.f, -HEIGHT / 4.f);
            glVertex2f(WIDTH / 4.f, -HEIGHT / 4.f);
            glVertex2f(WIDTH / 4.f, HEIGHT / 4.f);
            glVertex2f(-WIDTH / 4.f, HEIGHT / 4.f);
        glEnd();

        //Move o quadradoVerde para a esquerda da tela
        glTranslatef(WIDTH, 0.f, 0.f);

        //Qaudrado verde
        glBegin(GL_QUADS);
            glColor3f(0.f, 1.f, 0.f);
            glVertex2f(-WIDTH / 4.f, -HEIGHT / 4.f);
            glVertex2f(WIDTH / 4.f, -HEIGHT / 4.f);
            glVertex2f(WIDTH / 4.f, HEIGHT / 4.f);
            glVertex2f(-WIDTH / 4.f, HEIGHT / 4.f);
        glEnd();

        //Move o quadradoAzul para o baixo-esquerda
        glTranslatef(0.f, HEIGHT, 0.f);

        //Quadrado azul
        glBegin(GL_QUADS);
            glColor3f(0.f, 0.f, 1.f);
            glVertex2f(-WIDTH / 4.f, -HEIGHT / 4.f);
            glVertex2f(WIDTH / 4.f, -HEIGHT / 4.f);
            glVertex2f(WIDTH / 4.f, HEIGHT / 4.f);
            glVertex2f(-WIDTH / 4.f, HEIGHT / 4.f);
        glEnd();

        //Move o quadradoAmarelo para baixo-direita da tela
        glTranslatef(-WIDTH, 0.f, 0.f);

        //Quadrado amarelo
        glBegin(GL_QUADS);
            glColor3f(1.f, 1.f, 0.f);
            glVertex2f(-WIDTH / 4.f, -HEIGHT / 4.f);
            glVertex2f(WIDTH / 4.f, -HEIGHT / 4.f);
            glVertex2f(WIDTH / 4.f, HEIGHT / 4.f);
            glVertex2f(-WIDTH / 4.f, HEIGHT / 4.f);
        glEnd();

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
