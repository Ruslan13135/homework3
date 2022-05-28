package ru.otus.otushomework01;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.otushomework01.service.InteractionService;

@SpringBootApplication
@AllArgsConstructor
public class OtusHomework01Application implements CommandLineRunner {

    private final InteractionService interactionService;

    public static void main(String[] args) {
        SpringApplication.run(OtusHomework01Application.class, args);
    }

    @Override
    public void run(String... args) {
        interactionService.startInteraction();
    }
}
