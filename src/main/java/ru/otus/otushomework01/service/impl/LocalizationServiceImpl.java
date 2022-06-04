package ru.otus.otushomework01.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.otushomework01.config.LocalizationConfig;
import ru.otus.otushomework01.service.LocalizationService;

import java.util.List;
import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService, InitializingBean {

    private final List<String> availableLanguages;
    private final LocalizationConfig properties;
    private final MessageSource messageSource;
    private Locale currentLocale;


    public LocalizationServiceImpl(MessageSource messageSource, LocalizationConfig properties) {
        this.availableLanguages = properties.getLanguage();
        this.messageSource = messageSource;
        this.properties = properties;
    }

    @Override
    public List<String> getAvailableLanguages() {
        return availableLanguages;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    @Override
    public void setCurrentLocale(Locale locale) {
        this.currentLocale = locale;
    }

    public String getBundledMessage(String key) {
        return messageSource.getMessage(key, null, currentLocale);
    }

    @Override
    public void afterPropertiesSet() {
        currentLocale = properties.getAvailableLocales().entrySet().iterator().next().getValue();
    }
}
