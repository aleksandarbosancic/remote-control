package com.abosancic.remote.control.client;

import java.net.Socket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Start
{

    static String port = "4907";
    
    @Autowired
    private Authenticate authenticate;

    public void initialize(String ip, int port)
    {
        try
        {

            Socket sc = new Socket(ip, port);
            System.out.println("Connecting to the Server");
            //Authenticate class is responsible for security purposes
            authenticate.setcSocket(sc);

            authenticate.setSize(300, 80);
            authenticate.setLocation(500, 300);
            authenticate.setVisible(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
