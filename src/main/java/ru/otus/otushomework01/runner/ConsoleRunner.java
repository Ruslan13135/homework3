package ru.otus.otushomework01.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.otus.otushomework01.service.InteractionService;

@Component
@ConditionalOnProperty(name = "spring.shell.interactive.enabled", havingValue = "false")
@RequiredArgsConstructor
public class ConsoleRunner implements CommandLineRunner {

    private final InteractionService interactionService;

    @Override
    public void run(String... args) {
        interactionService.startInteraction();
    }
}
