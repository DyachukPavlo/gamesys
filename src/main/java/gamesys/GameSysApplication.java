package gamesys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class GameSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameSysApplication.class, args);
    }
}
