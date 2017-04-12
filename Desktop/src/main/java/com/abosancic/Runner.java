/*
 * Copyright (c) 2017
 * AlphaGate Automatisierungstechnik, Rankweil, Austria  * 
 * @author      original: abo, last edit: $Author$
 * @version     $Id$
 */
package com.abosancic;

import com.abosancic.gui.DemoFrame;
import com.abosancic.remote.control.client.Start;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author abosancic
 */
/**
 * Launches the application GUI.
 *
 * @author Paul Campbell
 */
@Component
public class Runner implements CommandLineRunner
{

    static String port = "4907";
    
    /**
     * Pull in the JFrame to be displayed.
     */
    @Autowired
    private DemoFrame frame;
    
    /**
     * Pull in the JFrame Start to be displayed.
     */
    @Autowired
    private Start start;

    @Override
    public void run(String... args) throws Exception
    {
        String ip = "192.168.1.13";
//        String ip = "10.89.40.174"; //mws
        start.initialize(ip, Integer.parseInt(port));
    }
    
}
