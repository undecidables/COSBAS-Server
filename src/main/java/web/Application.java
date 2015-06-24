package web;

/**
 * Created by Renette on 2015-06-23.
 * Main Spring application.
 * Can be built as a standalone jar
*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
