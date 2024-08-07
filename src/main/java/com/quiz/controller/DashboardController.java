package com.quiz.controller;

import com.quiz.helper.CurrentUser;
import com.quiz.model.Test;
import com.quiz.model.User;
import com.quiz.service.impl.DashboardService;
import com.quiz.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @Autowired
    CurrentUser currentUser;

    @Autowired
    TestServiceImpl testService;

    @GetMapping("/total-exam")
    public long getTotalExamsAttempts() throws Exception {
        return dashboardService.getTotalExamsAttempted(currentUser.getCurrentUserId());
    }

    @GetMapping("/tests")
    public List<Test> getTestsAttemptedByUser() throws Exception {
        User user = currentUser.getCurrentUserId();
        return dashboardService.getTestsAttemptedByUser(user);
    }

    @GetMapping("/test/{testId}/users-count")
    public long getNumberOfUsersAttempted(@PathVariable String testId){
        Test test = testService.getById(testId);
        return dashboardService.getNumberOfUsersAttemptedTest(test);
    }

    @GetMapping("/user/{testId}/score")
    public long getUserScoreForTest(@PathVariable String testId) throws Exception {
        User user = currentUser.getCurrentUserId();
        Test test = testService.getById(testId);
        return dashboardService.getUserScoreForTest(user,test);
    }
}
