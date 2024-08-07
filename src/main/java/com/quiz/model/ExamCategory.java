package com.quiz.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quiz.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "examCategory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamCategory {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String logo;

    private long createdAt;

    @DBRef
    private User createdBy;

    private long updatedAt;

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
}
