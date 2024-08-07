package com.quiz.service.impl;

import com.quiz.dto.TestDto;
import com.quiz.helper.CurrentUser;
import com.quiz.model.Test;
import com.quiz.repository.TestRepository;
import com.quiz.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ExamCategoryServiceImpl categoryService;

    @Autowired
    private ExamServiceImpl examService;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public List<Test> getALl() {
        return testRepository.findAll();
    }

    @Override
    public Test getById(String id) {
        return testRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("Test not found with this id."));
    }

    @Override
    public Test updateById(String id, TestDto test) throws Exception {
        Test exisitngTest = getById(id);

        if (test.getName() != null || !(test.getName().trim().isEmpty())){
            exisitngTest.setName(test.getName());
        } else if (test.getExam() != null || !(test.getExam().trim().isBlank())) {
            exisitngTest.setExam(examService.getById(test.getExam()));
        } else if (test.getExamCategory() != null || !(test.getExamCategory().trim().isBlank())) {
            exisitngTest.setExamCategory(categoryService.getExamCategoryById(test.getExamCategory()));
        }

        exisitngTest.setUpdatedAt(System.currentTimeMillis());
        exisitngTest.setUpdatedBy(currentUser.getCurrentUserId());
        return testRepository.save(exisitngTest);
    }

    @Override
    public void save(TestDto test) throws Exception {
        if (test.getName() == null || test.getName().trim().isEmpty() || test.getName().trim().isBlank()){
            throw new Exception("Please enter test name");
        } else if (test.getExam() == null || test.getExam().trim().isBlank() || test.getExam().trim().isEmpty()) {
            throw new Exception("Please select exam.");
        } else if (test.getExamCategory() == null || test.getExamCategory().trim().isBlank() || test.getExamCategory().trim().isEmpty()) {
            throw new Exception("Please select exam category.");
        }
        Test record = new Test();
        record.setName(test.getName());
        record.setCreatedBy(currentUser.getCurrentUserId());
        record.setExamCategory(categoryService.getExamCategoryById(test.getExamCategory()));
        record.setExam(examService.getById(test.getExam()));
        record.setCreatedAt(System.currentTimeMillis());
        testRepository.save(record);
    }

    @Override
    public void delete(String id) {
        Test test = getById(id);
        if (test != null){
            testRepository.deleteById(id);
        }
    }
}
