package com.quiz.service.impl;

import com.quiz.model.*;
import com.quiz.repository.UserAnswerRepository;
import com.quiz.repository.UserAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private UserAttemptRepository userAttemptRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    public long getTotalExamsAttempted(User user){
        return userAttemptRepository.countByUser(user);
    }

    public long getNumberOfUsersAttemptedTest(Test test){
        return userAttemptRepository.countByTest(test);
    }

    public Map<Question, Long> getQuestionScoreBreakdown(Test test) {
        Map<Question, Long> scoreBreakdown = new HashMap<>();

        List<UserAttempt> attempts = userAttemptRepository.findByTest(test);

        for (UserAttempt attempt : attempts) {
            List<UserAnswer> answers = userAnswerRepository.findByUserAttempt(attempt);
            for (UserAnswer answer : answers) {
                if (answer.isCorrect()) {
                    Question question = answer.getQuestion();
                    scoreBreakdown.put(question, scoreBreakdown.getOrDefault(question, 0L) + question.getMarks());
                }
            }
        }
        return scoreBreakdown;
    }


    public long getUserScoreForTest(User user, Test test){
        List<UserAttempt> attempts = userAttemptRepository.findByUserAndTest(user,test);
        return attempts.stream().mapToLong(UserAttempt::getScore).sum();
    }

    public List<Test> getTestsAttemptedByUser(User user){
        List<UserAttempt> attempts = userAttemptRepository.findByUser(user);
        return attempts.stream()
                .map(UserAttempt::getTest)
                .distinct()
                .collect(Collectors.toList());
    }

}
