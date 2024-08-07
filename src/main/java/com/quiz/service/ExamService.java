package com.quiz.service;

import com.quiz.dto.ExamInputDto;
import com.quiz.model.Exam;

import java.util.List;

public interface ExamService {
    List<Exam> getAll();
    Exam getById(String id) throws Exception;
    Exam updateById(String id, ExamInputDto exam) throws Exception;
    void save(ExamInputDto exam) throws Exception;
    void delete(String id) throws Exception;
}
