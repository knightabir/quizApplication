package com.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "userAnswer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswer {
    @Id
    private String id;
    private String selectedOption;
    private boolean correct;

    @DBRef
    private UserAttempt userAttempt;

    @DBRef
    private Question question;
}
