package com.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "userAttempt")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAttempt {

    @Id
    private String id;
    private long score;
    private long attemptTime;

    @DBRef
    private User user;

    @DBRef
    private Test test;
}
