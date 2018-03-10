package com.wen.cloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.core.env.Environment;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GatewayApplication extends SpringBootServletInitializer implements WebApplicationInitializer {



  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
  {
    return application.sources(GatewayApplication.class);
  }
  
  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }


}
