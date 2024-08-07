package com.quiz.service;

import com.quiz.dto.TestDto;
import com.quiz.model.Test;

import java.util.List;

public interface TestService {
    List<Test> getALl();
    Test getById(String id);
    Test updateById(String id,TestDto test) throws Exception;
    void save(TestDto test) throws Exception;
    void delete(String id);

}
