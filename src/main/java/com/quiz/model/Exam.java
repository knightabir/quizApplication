package com.quiz.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quiz.dto.ExamCategoryDto;
import com.quiz.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "exam")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam {

    @Id
    private String id;
    private String name;
    private long totalTest;
    private String logo;
    private long createdAt;
    private long updatedAt;

    @DBRef
    private ExamCategory examCategory;

    @DBRef
    private User createdBy;

    @DBRef
    private User updatedBy;

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

    @JsonProperty("examCategory")
    public ExamCategoryDto examCategoryDto(){
        if (examCategory != null){
            ExamCategoryDto dto = new ExamCategoryDto();
            dto.setId(examCategory.getId());
            dto.setCategoryName(examCategory.getName());
            return dto;
        }
        return null;
    }

}
