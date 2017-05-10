/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lição06;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import static java.sql.Types.NULL;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 *
 * @author nynha
 */
public class LTexture06 {
    public int mTextureID = 0;

    //Initialize texture dimensions
    public int mTextureWidth = 0;
    public int mTextureHeight = 0;

    public boolean loadTextureFromFile(String caminho) throws IOException
    {
        BufferedImage imagem = ImageIO.read(new File(caminho));
        imagem.getRGB(GL_LINEAR, GL_LINEAR);
        return true;
    }

    public boolean loadTextureFromPixels32(int[] pixels, int width, int height)
    {
        //Free texture if it exists
        freeTexture();

        //Get texture dimensions
        mTextureWidth = width;
        mTextureHeight = height;

        //Generate texture glGenTextures( 1, &mTextureID );ID
        mTextureID = glGenTextures();

        //Bind texture ID
        glBindTexture(GL_TEXTURE_2D, mTextureID);

        //Generate texture
        IntBuffer iBuffer = BufferUtils.createIntBuffer(width*height);
        for (int i = 0; i < pixels.length; i++)
        {
            iBuffer.put(pixels[i]);
        }
        iBuffer.position(0);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, iBuffer);

        //Set texture parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        //Unbind texture
        glBindTexture(GL_TEXTURE_2D, NULL);

        //Checagem de erros
        int erro = glGetError();
        if(erro != GL_NO_ERROR)
        {
            System.out.println("Erro de pixels");
            return false;
        }
        return true;
    }

    public void freeTexture()
    {
        //Delete texture
        if(mTextureID != 0)
        {
            glDeleteTextures(mTextureID);
            mTextureID = 0;
        }

        mTextureWidth = 0;
        mTextureHeight = 0;
    }

    public void render(float x, float y)
    {
        //If the texture exists
        if( mTextureID != 0 )
        {
            //Remove any previous transformations
            glLoadIdentity();

            //Move to rendering point
            glTranslatef( x, y, 0.f );

            //Set texture ID
            glBindTexture( GL_TEXTURE_2D, mTextureID );

            //Render textured quad
            glBegin( GL_QUADS );
                glTexCoord2f(0.f, 0.f); 
                glVertex2f(0.f, 0.f);
                glTexCoord2f(1.f, 0.f); 
                glVertex2f(mTextureWidth, 0.f);
                glTexCoord2f(1.f, 1.f); 
                glVertex2f(mTextureWidth, mTextureHeight);
                glTexCoord2f(0.f, 1.f); 
                glVertex2f(0.f, mTextureHeight);
            glEnd();
        }
    }

    public int getTextureID()
    {
        return mTextureID;
    }

    public int textureWidth()
    {
        return mTextureWidth;
    }

    public int textureHeight()
    {
        return mTextureHeight;
    }
}
