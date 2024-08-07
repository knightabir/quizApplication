package com.quiz.service;

import com.quiz.model.ExamCategory;

import java.util.List;

public interface ExamCategoryService {
    List<ExamCategory> getAllCategory();
    ExamCategory getExamCategoryById(String id) throws Exception;
    ExamCategory updateExamCategoryById(String id, ExamCategory examCategory) throws Exception;
    void saveExamCategory(ExamCategory examCategory) throws Exception;
    void deleteExamCategoryById(String id) throws Exception;
}
