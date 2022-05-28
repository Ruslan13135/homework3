package ru.otus.otushomework01.service;

import java.util.List;

public interface IOService {

    String getTyped();

    void print(String message);

    void printSurroundQuotes(List<String> source);

    void printBundledMessage(String key);
}
