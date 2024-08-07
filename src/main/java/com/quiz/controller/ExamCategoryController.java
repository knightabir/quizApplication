package com.quiz.controller;

import com.quiz.model.ExamCategory;
import com.quiz.service.impl.ExamCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/exam/category")
public class ExamCategoryController {

    @Autowired
    private  ExamCategoryServiceImpl examCategoryService;


    @GetMapping
    public ResponseEntity<?> getAll() {
//        List<ExamCategoryDto> categories = ;
        return ResponseEntity.ok(examCategoryService.getAllCategory());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ExamCategory category) throws Exception {
        examCategoryService.saveExamCategory(category);
        Map<String, Object> response = new HashMap<>();
        response.put("message","Category saved successfully.");
        response.put("status",true);
        response.put("code", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamCategory> update(@RequestParam String id, @RequestBody ExamCategory category) throws Exception {
        ExamCategory updatedCategory = examCategoryService.updateExamCategoryById(id,category);
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamCategory> getOne(@RequestParam String id) throws Exception {
        ExamCategory existingCategory = examCategoryService.getExamCategoryById(id);
        return ResponseEntity.ok(existingCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@RequestParam String id) throws Exception {
        examCategoryService.deleteExamCategoryById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message","Category deleted successfully.");
        response.put("status",true);
        response.put("code", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

}
