package com.quiz.service.impl;

import com.quiz.dto.ExamInputDto;
import com.quiz.helper.CurrentUser;
import com.quiz.model.Exam;
import com.quiz.model.ExamCategory;
import com.quiz.repository.ExamRepository;
import com.quiz.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {


    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private CurrentUser user;

    @Autowired
    private ExamCategoryServiceImpl categoryService;

    @Override
    public List<Exam> getAll() {
        return examRepository.findAll();
    }

    @Override
    public Exam getById(String id) throws Exception {
        return examRepository.findById(id).orElseThrow(()-> new Exception("Exam not found with this id."));
    }

    @Override
    public Exam updateById(String id, ExamInputDto exam) throws Exception {
        Exam existingExam = getById(id);
        if (existingExam == null) {
            throw new Exception("Exam not found");
        }
        if (exam.getName() != null && !exam.getName().trim().isEmpty()) {
            existingExam.setName(exam.getName());
        }
        if (exam.getExamCategory() == null || exam.getExamCategory().trim().isEmpty()) {
            throw new Exception("Please select an exam category.");
        }
        if (exam.getLogo() != null && !exam.getLogo().trim().isEmpty()) {
            existingExam.setLogo(exam.getLogo());
        }
        ExamCategory existingCategory = categoryService.getExamCategoryById(exam.getExamCategory());
        if (existingCategory == null) {
            throw new Exception("Exam category not found");
        }
        existingExam.setExamCategory(existingCategory);
        existingExam.setUpdatedAt(System.currentTimeMillis());
        existingExam.setUpdatedBy(user.getCurrentUserId());
        return examRepository.save(existingExam);
    }



    @Override
    public void save(ExamInputDto exam) throws Exception {
        if (exam == null) {
            throw new IllegalArgumentException("Exam cannot be null");
        }
        if (exam.getName() == null || exam.getName().isEmpty()) {
            throw new IllegalArgumentException("Exam name cannot be empty");
        }
        if (exam.getLogo() == null || exam.getLogo().isEmpty()) {
            throw new IllegalArgumentException("Exam logo cannot be empty");
        }
        if (exam.getExamCategory() == null ){
            throw new Exception("Please select exam category.");
        }

        Exam data = new Exam();
        data.setCreatedAt(System.currentTimeMillis());
        data.setCreatedBy(user.getCurrentUserId());
        data.setName(exam.getName());
        data.setLogo(exam.getLogo());
        data.setExamCategory(categoryService.getExamCategoryById(exam.getExamCategory()));
        examRepository.save(data);
    }


    @Override
    public void delete(String id) throws Exception {
        Exam existingExam = getById(id);
        if (existingExam != null){
            examRepository.deleteById(id);
        }
    }
}
