package com.quiz.controller;

import com.quiz.helper.CurrentUser;
import com.quiz.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private CurrentUser user;

    @GetMapping
    public User demo() throws Exception {
        return user.getCurrentUserId();
    }
}
