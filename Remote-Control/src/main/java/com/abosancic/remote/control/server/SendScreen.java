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
            BufferedImage image = robot.createScreenCapture(rectangle);

            try
            {
                ImageIO.write(image, "jpeg", oos);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                continueLoop = false;
            }

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                continueLoop = false;
            }
        }
    }
}
