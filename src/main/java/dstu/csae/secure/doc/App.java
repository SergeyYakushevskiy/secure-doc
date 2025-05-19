package dstu.csae.secure.doc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "dstu.csae.secure.doc.repository")

public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
