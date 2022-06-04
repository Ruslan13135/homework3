package ru.otus.otushomework01.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;


@Component
public class ShellPromptProvider implements PromptProvider {
    private boolean isTestStarted = true;

    @Override
    public AttributedString getPrompt() {
        if (isTestStarted) {
            return new AttributedString("shell:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
        } else {
            return new AttributedString("test:>",
                    AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
        }
    }
}
