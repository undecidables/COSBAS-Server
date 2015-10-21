package cosbas.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ImportResource("beans.xml")
@ComponentScan("cosbas")
@EnableScheduling
@EnableCaching
public class Application {

    public static void main(String[] args) throws Throwable {
       SpringApplication.run(Application.class, args);
    }

}