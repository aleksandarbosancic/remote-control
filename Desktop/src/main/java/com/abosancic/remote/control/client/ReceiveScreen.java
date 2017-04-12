package com.abosancic.remote.control.client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ReceiveScreen extends Thread
{

    public static final int PAYLOAD_BLOCKLENGTH = 4;
    
//    private static final int IMG_WIDTH = 960;
//    private static final int IMG_HEIGHT = 540;
    private static final int IMG_WIDTH = 960;
    private static final int IMG_HEIGHT = 540;
        
    private JPanel cPanel = null;
    private boolean continueLoop = true;
    InputStream oin = null;
    Image image = null;
    byte[] imageBytes;
    BufferedImage webImage = null;
    
    public void init(InputStream in, JPanel p)
    {
        oin = in;
        cPanel = p;
    }

    @Override
    public void run()
    {
        try
        {
            //Read screenshots of the client and then draw them
            while (continueLoop)
            {
                byte[] bytes = new byte[1024 * 1024];
                
                int count = 0;
                do
                {
                    count += oin.read(bytes, count, bytes.length - count);
                }
                while (!(count > 4 && bytes[count - 2] == (byte) -1 && bytes[count - 1] == (byte) -39)); // end of jpeg
                
                synchronized (this)
                {
                    webImage = ImageIO.read(new ByteArrayInputStream(bytes));
                    image = webImage.getScaledInstance(cPanel.getWidth(), cPanel.getHeight(), Image.SCALE_SMOOTH);
                }

                //Draw the received screenshots
                Graphics graphics = cPanel.getGraphics();
                graphics.drawImage(image, 0, 0, cPanel.getWidth(), cPanel.getHeight(), cPanel);
            }

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public synchronized byte[] getImageBytes()
    {
        try
        {

            byte[] imageInByte;

            // convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(webImage, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            
            return imageInByte;

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        } 
        return new byte[0];
    }
    
    private static BufferedImage resizeImage(BufferedImage originalImage, int type)
    {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }
    
    
}
