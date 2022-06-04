package ru.otus.otushomework01.domain;


import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(of = {"id", "question", "rightAnswer"})
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @CsvBindByName(column = "id")
    private int id;

    @CsvBindByName(column = "question")
    private String question;

    @CsvBindByName(column = "firstAnswer")
    private String firstAnswer;

    @CsvBindByName(column = "secondAnswer")
    private String secondAnswer;

    @CsvBindByName(column = "ThirdAnswer")
    private String ThirdAnswer;

    @CsvBindByName(column = "rightAnswer")
    private String rightAnswer;
}