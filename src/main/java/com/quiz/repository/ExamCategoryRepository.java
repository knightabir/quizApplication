package com.quiz.repository;

import com.quiz.model.ExamCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExamCategoryRepository extends MongoRepository<ExamCategory, String> {
}
