package com.quiz.controller;

import com.quiz.dto.ExamInputDto;
import com.quiz.model.Exam;
import com.quiz.service.impl.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/exam")
public class ExamController {

    @Autowired
    private ExamServiceImpl examService;


    @GetMapping
    public ResponseEntity<?> getAll (){
        Map<String, Object> response = new HashMap<>();
        List<Exam> data = examService.getAll();
        if (data.isEmpty()){
            response.put("message","No exams found.");
            response.put("status",false);
            response.put("code",HttpStatus.NOT_FOUND.value());
        }else {
            response.put("message","List of exams found.");
            response.put("status",true);
            response.put("code",HttpStatus.OK.value() );
            response.put("data",data);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(examService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ExamInputDto exam) throws Exception {
        examService.save(exam);
        Map<String, Object> response = new HashMap<>();
        response.put("message","Exam created successfully.");
        response.put("status",true);
        response.put("code", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ExamInputDto exam) throws Exception {
        Exam data = examService.updateById(id,exam);
        Map<String,Object> response = new HashMap<>();
        response.put("message","Data updated successfully.");
        response.put("status",true);
        response.put("code",HttpStatus.CREATED.value());
        response.put("data",data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws Exception {
        examService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message","Exam deleted successfully.");
        response.put("status",true);
        response.put("code",HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
