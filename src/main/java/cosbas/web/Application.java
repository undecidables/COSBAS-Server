package cosbas.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("beans.xml")
@ComponentScan("cosbas")
public class Application {

    public static void main(String[] args) throws Throwable {
       SpringApplication.run(Application.class, args);
    }

}