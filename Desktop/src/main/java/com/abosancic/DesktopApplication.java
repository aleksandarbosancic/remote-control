package com.abosancic;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DesktopApplication
{
    
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DesktopApplication.class)
                .headless(false)
                //.web(false)
                .run(args);
    }

}
