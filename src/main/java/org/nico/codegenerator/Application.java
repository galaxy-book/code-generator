package org.nico.codegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) throws Exception {
    	
    	//fix javacv happend 'UnsatisfiedLinkError' on linux
    	//System.getProperty("org.bytedeco.javacpp.logger.debug", "true");
    	//System.setProperty("org.bytedeco.javacpp.logger", "slf4j");
    	
        SpringApplication.run(Application.class, args);
    }
}