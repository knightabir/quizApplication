package com.quiz.repository;

import com.quiz.model.UserAnswer;
import com.quiz.model.UserAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserAnswerRepository extends MongoRepository<UserAnswer, String> {
    List<UserAnswer> findByUserAttempt(UserAttempt userAttempt);
}
