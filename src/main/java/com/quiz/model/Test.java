package com.quiz.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quiz.dto.ExamCategoryDto;
import com.quiz.dto.ExamOutDto;
import com.quiz.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "test")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    @Id
    private String id;
    private String name;
    private long createdAt;
    private long updatedAt;

    @DBRef
    private ExamCategory examCategory;

    @DBRef
    private Exam exam;

    @DBRef
    private User createdBy;

    @DBRef
    private User updatedBy;


    @JsonProperty("examCategory")
    private ExamCategoryDto getExamCategory(){
        ExamCategoryDto dto = new ExamCategoryDto();
        if (examCategory != null){
            dto.setId(examCategory.getId());
            dto.setCategoryName(examCategory.getName());
            return dto;
        }
        return null;
    }

    @JsonProperty("exam")
    private ExamOutDto getExam(){
        ExamOutDto dto = new ExamOutDto();
        if (exam != null){
            dto.setId(exam.getId());
            dto.setName(exam.getName());
            return dto;
        }
        return null;
    }

    @JsonProperty("createdBy")
    public UserDTO getCreatedByDTO() {
        if (createdBy != null) {
            UserDTO dto = new UserDTO();
            dto.setId(createdBy.getId());
            dto.setName(createdBy.getFullName());
            return dto;
        }
        return null;
    }

    @JsonProperty("updatedBy")
    public UserDTO getUpdatedByDTO() {
        if (updatedBy != null) {
            UserDTO dto = new UserDTO();
            dto.setId(updatedBy.getId());
            dto.setName(updatedBy.getFullName());
            return dto;
        }
        return null;
    }

}
