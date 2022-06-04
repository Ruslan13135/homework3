package ru.otus.otushomework01.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.otushomework01.config.LocalizationConfig;
import ru.otus.otushomework01.domain.Question;
import ru.otus.otushomework01.service.LocalizationService;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {LocalizationConfig.class, QuestionDaoImpl.class})
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class QuestionDaoImplTest {

    @Autowired
    LocalizationConfig properties;
    @Autowired
    QuestionDaoImpl questionDao;
    @MockBean
    LocalizationService localizationService;

    @Test
    void getAllQuestionRussian() {
        Mockito.when(localizationService.getCurrentLocale()).thenReturn(new Locale("ru", "RU"));
        Map<Integer, Question> questions = questionDao.getAllQuestion("ru");
        assertNotNull(questions);
    }

    @Test
    void getAllQuestionEnglish() {
        Mockito.when(localizationService.getCurrentLocale()).thenReturn(new Locale("en", "EN"));
        Map<Integer, Question> questions = questionDao.getAllQuestion("en");
        assertFalse(questions.isEmpty());
    }
}