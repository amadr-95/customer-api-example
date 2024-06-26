package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(Main.class, args);

        //Beans in Application context
        /*String[] beansNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beansNames) {
            System.out.println(beanName);
        }*/

    }
}
