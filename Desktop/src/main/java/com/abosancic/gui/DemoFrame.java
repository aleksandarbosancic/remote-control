/*
 * Copyright (c) 2017
 * AlphaGate Automatisierungstechnik, Rankweil, Austria  * 
 * @author      original: abo, last edit: $Author$
 * @version     $Id$
 */
package com.abosancic.gui;

import com.abosancic.remote.control.client.ReceiveScreen;
import com.abosancic.remote.control.client.SendEvents;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author abosancic
 */
@Component
public class DemoFrame extends javax.swing.JFrame
{
    
    @Autowired
    private ReceiveScreen receiveScreen;
    
    @Autowired
    private SendEvents sendEvents;
    
    String width = "", height = "";

    private Socket cSocket = null;
    
    /**
     * Creates new form DemoFrame
     */
    public DemoFrame()
    {
        initComponents();
        
        this.width = width;
        this.height = height;
        this.cSocket = cSocket;
        
        addComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1060, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    public void init(Socket cSocket, String width, String height)
    {
        InputStream in = null;
        //start drawing GUI
        
        //This allows to handle KeyListener events
        jPanel1.setFocusable(true);
        jPanel1.requestFocusInWindow();

        try
        {
            in = cSocket.getInputStream();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        //Start receiving screenshots
        receiveScreen.init(in, jPanel1);
        receiveScreen.start();
        //Start sending events to the client
        sendEvents.init(cSocket, jPanel1, width, height);
    }
    
    private void addComponents()
    {
    }

    public ReceiveScreen getReceiveScreen()
    {
        return receiveScreen;
    }
    
}
