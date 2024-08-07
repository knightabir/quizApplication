package com.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(value = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private String fullName;
    @NonNull
    private String email;
    @NonNull
    private String phone;
    private String address;
    private boolean isActive = true;
    @NonNull
    private String password;
    private List<String> role = List.of("USER");
    private long createdAt;
    private long updatedAt;
}
