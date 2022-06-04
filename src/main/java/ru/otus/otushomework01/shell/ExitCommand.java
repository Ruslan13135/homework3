package ru.otus.otushomework01.shell;

import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class ExitCommand implements Quit.Command {

    @ShellMethod(value = "Exit from shell", key = {"выйти", "exit", "-"})
    public void quit() {
        throw new ExitRequest();
    }
}
