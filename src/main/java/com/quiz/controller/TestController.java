package com.quiz.controller;

import com.quiz.dto.TestDto;
import com.quiz.model.Test;
import com.quiz.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/question/test")
public class TestController {

    @Autowired
    private TestServiceImpl testService;

    @GetMapping
    private ResponseEntity<?> getAll(){
        List<Test> data = testService.getALl();
        Map<String,Object> response = new HashMap<>();
        int code = HttpStatus.NO_CONTENT.value();
        if (data.isEmpty()){
            response.put("message","No data found.");
            response.put("code",code);
            response.put("status",false);
        }else {
            code = HttpStatus.FOUND.value();
            response.put("message","Test found.");
            response.put("code",code);
            response.put("status",true);
            response.put("data",data);
        }
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping
    private ResponseEntity<?> save(@RequestBody TestDto testDto) throws Exception {
        Map<String, Object> response = new HashMap<>();
        testService.save(testDto);
        response.put("message","Test created successfully.");
        response.put("status",true);
        response.put("code",HttpStatus.CREATED.value());

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody TestDto testDto) throws Exception {
        Test existingTest = testService.updateById(id, testDto);
        Map<String,Object>response  = new HashMap<>();
        response.put("message","Test updated successfully.");
        response.put("status",true);
        response.put("code",HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        testService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Test deleted Successfully.");
    }
}
