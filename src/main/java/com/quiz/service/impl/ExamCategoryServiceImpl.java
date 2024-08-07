package com.quiz.service.impl;

import com.quiz.helper.CurrentUser;
import com.quiz.model.ExamCategory;
import com.quiz.repository.ExamCategoryRepository;
import com.quiz.service.ExamCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamCategoryServiceImpl implements ExamCategoryService {

    @Autowired
    private ExamCategoryRepository examCategoryRepository;


    @Autowired
    private CurrentUser user;

    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public List<ExamCategory> getAllCategory() {
        return examCategoryRepository.findAll();
    }

    @Override
    public ExamCategory getExamCategoryById(String id) throws Exception {
        return examCategoryRepository.findById(id).orElseThrow(()-> new Exception("Category not found."));
    }

    @Override
    public ExamCategory updateExamCategoryById(String id, ExamCategory examCategory) throws Exception {
        ExamCategory existingExamCategory = getExamCategoryById(id);
        existingExamCategory.setUpdatedAt(System.currentTimeMillis());
        if (examCategory.getName() == null && examCategory.getName().trim().isEmpty()) {
            existingExamCategory.setName(examCategory.getName());
        }
        if (examCategory.getLogo() == null && examCategory.getLogo().trim().isEmpty()) {
            existingExamCategory.setLogo(examCategory.getLogo());
        }
        examCategory.setUpdatedBy(user.getCurrentUserId());
        return examCategoryRepository.save(existingExamCategory);
    }


    @Override
    public void saveExamCategory(ExamCategory examCategory) throws Exception {
        if (examCategory.getName() == null && examCategory.getName().trim().isEmpty()){
            throw new Exception("Name should not be blank.");
        }
        if (examCategory.getLogo() == null && examCategory.getLogo().trim().isEmpty()){
            throw new Exception("Please upload exam category logo.");
        }
        examCategory.setCreatedAt(System.currentTimeMillis());
        examCategory.setCreatedBy(user.getCurrentUserId());
        examCategoryRepository.save(examCategory);
    }

    @Override
    public void deleteExamCategoryById(String id) throws Exception {
        ExamCategory existingExamCategory = getExamCategoryById(id);
        examCategoryRepository.delete(existingExamCategory);
    }
}
