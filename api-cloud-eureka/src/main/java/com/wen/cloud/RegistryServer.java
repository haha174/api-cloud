package com.wen.cloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EnableEurekaServer
public class RegistryServer extends SpringBootServletInitializer implements WebApplicationInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(RegistryServer.class, args);
    }
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(RegistryServer.class);
    }

}
