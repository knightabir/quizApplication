package com.quiz.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quiz.dto.ExamCategoryDto;
import com.quiz.dto.ExamOutDto;
import com.quiz.dto.TestDto;
import com.quiz.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    private String id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
    private String correctAnswer;
    private int marks = 1;

    @DBRef
    private User createdBy;
    private long createdAt;

    @DBRef
    private User updatedBy;
    private long updatedAt;

    @DBRef
    private Exam exam;
    @DBRef
    private ExamCategory examCategory;
    @DBRef
    private Test test;
    private boolean active = false;

    @JsonProperty("createdBy")
    public UserDTO getCreatedBy(){
        if (createdBy != null){
            UserDTO dto = new UserDTO();
            dto.setId(createdBy.getId());
            dto.setName(createdBy.getFullName());
            return dto;
        }
        return null;
    }

    @JsonProperty("updatedBy")
    public UserDTO getUpdatedBy(){
        if (updatedBy != null){
            UserDTO dto = new UserDTO();
            dto.setName(updatedBy.getFullName());
            dto.setId(updatedBy.getId());
            return dto;
        }
        return null;
    }

    @JsonProperty("exam")
    public ExamOutDto getExamOutDto(){
        if (exam != null){
            ExamOutDto dto = new ExamOutDto();
            dto.setName(exam.getName());
            dto.setId(exam.getId());
            return dto;
        }
        return null;
    }

    @JsonProperty("examCategory")
    public ExamCategoryDto getExamCategory(){
        if (examCategory != null){
            ExamCategoryDto dto = new ExamCategoryDto();
            dto.setId(exam.getId());
            dto.setCategoryName(examCategory.getName());
            return dto;
        }
        return null;
    }

    @JsonProperty("test")
    public TestDto getTestDto(){
        if (exam != null){
            TestDto  dto = new TestDto();
            dto.setId(exam.getId());
            dto.setName(exam.getName());
            return dto;
        }
        return null;
    }
}
