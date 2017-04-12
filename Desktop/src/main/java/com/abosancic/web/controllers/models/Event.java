/*
 * Copyright (c) 2017
 * AlphaGate Automatisierungstechnik, Rankweil, Austria  * 
 * @author      original: abo, last edit: $Author$
 * @version     $Id$
 */
package com.abosancic.web.controllers.models;

import com.abosancic.remote.control.client.Commands;

/**
 *
 * @author abosancic
 */
public class Event
{

    String name;
    int x;
    int y;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getCommand()
    {
        if(this.name == null)
        {
            return 0;
        }
        
        if (this.name.equals("mousemove"))
        {
            return Commands.MOVE_MOUSE.getAbbrev();
        }
        else if (this.name.equals("mousedrag"))
        {
            return Commands.DRAG_MOUSE.getAbbrev();
        }
        else if (this.name.equals("mousedown"))
        {
            return Commands.PRESS_MOUSE.getAbbrev();
        }
        else if (this.name.equals("mouseup"))
        {
            return Commands.RELEASE_MOUSE.getAbbrev();
        }
        else
        {
            return 0;
        }

    }

    @Override
    public String toString()
    {
        return "Event{" + "name=" + name + ", x=" + x + ", y=" + y + ", cmdId=" + getCommand() + '}';
    }

}
