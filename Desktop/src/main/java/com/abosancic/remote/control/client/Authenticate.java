package com.abosancic.remote.control.client;

import com.abosancic.gui.DemoFrame;
import com.abosancic.web.controllers.models.ScreenDetails;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component
public class Authenticate extends JFrame implements ActionListener
{

    @Autowired
    private DemoFrame demoFrame;
    
    private Socket cSocket = null;
    DataOutputStream psswrchk = null;
    DataInputStream verification = null;
    String verify = "";
    JButton SUBMIT;
    JPanel panel;
    JLabel label, label1;
    String width = "", height = "";
    final JTextField text1;

    public void setcSocket(Socket cSocket)
    {
        this.cSocket = cSocket;
    }
    
    Authenticate()
    {
        label1 = new JLabel();
        label1.setText("Password");
        text1 = new JTextField(15);
        text1.setText("aco");

        label = new JLabel();
        label.setText("");
        this.setLayout(new BorderLayout());

        SUBMIT = new JButton("SUBMIT");

        panel = new JPanel(new GridLayout(2, 1));
        panel.add(label1);
        panel.add(text1);
        panel.add(label);
        panel.add(SUBMIT);
        add(panel, BorderLayout.CENTER);
        SUBMIT.addActionListener(this);
        setTitle("LOGIN FORM");
    }

    public void actionPerformed(ActionEvent ae)
    {

        String value1 = text1.getText();

        try
        {
            psswrchk = new DataOutputStream(cSocket.getOutputStream());
            verification = new DataInputStream(cSocket.getInputStream());
            psswrchk.writeUTF(value1);
            verify = verification.readUTF();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (verify.equals("valid"))
        {
            try
            {
                width = verification.readUTF();
                height = verification.readUTF();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            //CreateFrame abc = new CreateFrame(cSocket, width, height);
            /* display the form using the AWT EventQueue */
            java.awt.EventQueue.invokeLater(new Runnable()
            {

                @Override
                public void run()
                {
                    demoFrame.init(cSocket, width, height);
                    demoFrame.setVisible(true);
                    dispose();
                }
            });
        }
        else
        {
            System.out.println("enter the valid password");
            JOptionPane.showMessageDialog(this, "Incorrect  password", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }

    }
    
    public ScreenDetails getScreenDetails()
    {
        return new ScreenDetails(width, height);
    }

    public String getWidth1()
    {
        return width;
    }

    public String getHeight1()
    {
        return height;
    }
}
