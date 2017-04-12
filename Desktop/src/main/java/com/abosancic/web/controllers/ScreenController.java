/*
 * Copyright (c) 2017
 * AlphaGate Automatisierungstechnik, Rankweil, Austria  * 
 * @author      original: abo, last edit: $Author$
 * @version     $Id$
 */
package com.abosancic.web.controllers;

import com.abosancic.gui.DemoFrame;
import com.abosancic.remote.control.client.Authenticate;
import com.abosancic.remote.control.client.Commands;
import com.abosancic.remote.control.client.SendEvents;
import com.abosancic.web.controllers.models.Event;
import com.abosancic.web.controllers.models.ScreenDetails;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author abosancic
 */
@RestController
public class ScreenController
{

    @Autowired
    private DemoFrame demoFrame;
    @Autowired
    private SendEvents sendEvents;
    @Autowired
    private Authenticate authenticate;
    
    @RequestMapping("/")
    public String index()
    {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping(value = "/ajaxtest/{type}/{x}/{y}", method = RequestMethod.GET)
    public @ResponseBody
    String getTime(@PathVariable("type") String type, @PathVariable("x") Integer x, @PathVariable("y") Integer y) {
        
        Event event = new Event();
        event.setName(type);
        event.setX(x);
        event.setY(y);
        
        if (Commands.MOVE_MOUSE.getAbbrev() == event.getCommand())
        {
            sendEvents.sendEvent(event);
        }
        else if (Commands.PRESS_MOUSE.getAbbrev() == event.getCommand())
        {
            sendEvents.sendMousePressed(event);
        }
        else if (Commands.RELEASE_MOUSE.getAbbrev() == event.getCommand())
        {
            sendEvents.sendMouseRelease(event);
        }
        
        return "OK";
    }

    @ResponseBody
    @RequestMapping(value = "/photo2", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] testphoto() throws IOException
    {
        try
        {
            return IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("screen.jpg"));
        }
        finally
        {
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/photo1", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] photo() throws IOException
    {
        try
        {
            return demoFrame.getReceiveScreen().getImageBytes();
        }
        finally
        {
        }
    }
    
    @RequestMapping(value = "/getImage", method = RequestMethod.GET)
    public void showImage(HttpServletResponse response) throws Exception
    {

        //response.sendError(HttpServletResponse.SC_NOT_FOUND);

        byte[] imgByte = demoFrame.getReceiveScreen().getImageBytes();

        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(imgByte);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    
    @ResponseBody
    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public Event event(@RequestBody Event event) throws IOException
    {
        System.out.println("event: " + event);
        sendEvents.sendEvent(event);
        return event;
    }
    
    @RequestMapping(value = "/screendetails", method = RequestMethod.GET)
    public @ResponseBody
    ScreenDetails getScreenDetails()
    {
        return authenticate.getScreenDetails();
    }


}
