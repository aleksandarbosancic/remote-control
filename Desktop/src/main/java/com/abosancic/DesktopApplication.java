package com.abosancic;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DesktopApplication
{
    
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DesktopApplication.class)
                .headless(false)
                .web(false)
                .run(args);
    }

    /**
     * Creates the {@link DemoFrame} object and returns it.
     *
     * This @Bean could have been replaced by a @Component annotation being
     * added to the {@link DemoFrame} class.
     *
     * @return the application window
     */
    @Bean
    public DemoFrame frame()
    {
        return new DemoFrame();
    }
    
}
