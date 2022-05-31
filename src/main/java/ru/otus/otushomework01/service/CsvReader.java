package ru.otus.otushomework01.service;


import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.stereotype.Service;
import ru.otus.otushomework01.dao.QuestionDao;
import ru.otus.otushomework01.domain.Question;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class CsvReader {

    @SuppressWarnings("unchecked")
    public List<Question> parseQuestions(String questionResourceUrl) {
        InputStream is = QuestionDao.class.getResourceAsStream(questionResourceUrl);

        CSVReader reader = new CSVReader(new InputStreamReader(is));
        HeaderColumnNameMappingStrategy<Question> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
        mappingStrategy.setType(Question.class);

        CsvToBean<Question> csvToBean = new CsvToBeanBuilder(reader)
                .withMappingStrategy(mappingStrategy)
                .withThrowExceptions(true)
                .build();
        try {
            return csvToBean.parse();
        } catch (RuntimeException e) {
            throw new RuntimeException("Недостаточно данных в файле с вопросами questions.csv", e);
        }
    }

}