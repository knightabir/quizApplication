package com.quiz.service;

import com.quiz.dto.QuestionDto;
import com.quiz.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAll();
    Question getById(String id) throws Exception;
    Question update(String id, QuestionDto question) throws Exception;
    void save(QuestionDto question) throws Exception;
    void deleteById(String id) throws Exception;
}
