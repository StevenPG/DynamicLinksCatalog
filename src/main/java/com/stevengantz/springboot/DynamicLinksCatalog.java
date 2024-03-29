package com.stevengantz.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Steven Gantz on 2/15/2017.
 *
 * Set executable as embedded tomcat
 */
@SpringBootApplication(scanBasePackages={"com.stevengantz.json","com.stevengantz.config","com.stevengantz.springboot"})
public class DynamicLinksCatalog {
    public static void main(String[] args){
        SpringApplication.run(DynamicLinksCatalog.class);
    }
}
