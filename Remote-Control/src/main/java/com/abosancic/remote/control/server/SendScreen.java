package com.abosancic.remote.control.server;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;

class SendScreen extends Thread
{

    static final int PAYLOAD_BLOCKLENGTH = 4;
    static String IMAGE_FORMAT = "jpg";
    
    Socket socket = null;
    Robot robot = null;
    Rectangle rectangle = null;
    boolean continueLoop = true;

    OutputStream oos = null;

    public SendScreen(Socket socket, Robot robot, Rectangle rect)
    {
        this.socket = socket;
        this.robot = robot;
        rectangle = rect;
        start();
    }

    public void run()
    {

        try
        {
            oos = socket.getOutputStream();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            continueLoop = false;
        }

        while (continueLoop)
        {

//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(image, IMAGE_FORMAT, baos);
//            oos.write(ByteBuffer.allocate(PAYLOAD_BLOCKLENGTH).putInt(baos.size()).array());
//            oos.write(baos.toByteArray());
            try
            {
                BufferedImage image = robot.createScreenCapture(rectangle);
                ImageIO.write(image, "jpeg", oos);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                continueLoop = false;
            }

            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                continueLoop = false;
            }
        }
    }
}
