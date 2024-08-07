package com.quiz.repository;

import com.quiz.model.Test;
import com.quiz.model.User;
import com.quiz.model.UserAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserAttemptRepository extends MongoRepository<UserAttempt, String> {
    long countByUser(User user);
    long countByTest(Test test);
    List<UserAttempt> findByUser(User user);
    List<UserAttempt> findByTest(Test test);

    List<UserAttempt> findByUserAndTest(User user, Test test);

}
