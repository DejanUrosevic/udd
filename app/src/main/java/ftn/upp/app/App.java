package ftn.upp.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableElasticsearchRepositories(basePackages = "ftn.upp.app.elasticsearch.repository")
@EnableJpaRepositories(basePackages = {"ftn.upp.app.repository"})
public class App {

    public static void main( String[] args ){
        SpringApplication.run(App.class, args);
    }
}
