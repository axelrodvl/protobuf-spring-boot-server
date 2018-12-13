package co.axelrod.protobuf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class WorkerSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkerSpringBootApplication.class, args);
    }
}
