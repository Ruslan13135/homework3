package ru.otus.otushomework01.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.localization-properties")
public class LocalizationConfig {

    public static final String RUSSIAN_RU = "рус";
    public static final String ENGLISH_RU = "ru";

    private String greeting;
    private List<String> language;
    private List<String> exit;
    private HashMap<String, String> resourceUrls;

    public Map<String, Locale> getAvailableLocales() {
        Map<String, Locale> localesMap = new HashMap<>();
        for (String command : language) {
            if (RUSSIAN_RU.equals(command)) {
                localesMap.put(command, new Locale(ENGLISH_RU, ENGLISH_RU.toUpperCase()));
            } else {
                localesMap.put(command, new Locale(command, command.toUpperCase()));
            }
        }
        return localesMap;
    }
}