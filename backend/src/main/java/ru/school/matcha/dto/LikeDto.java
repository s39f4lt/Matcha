package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class LikeDto {

    private ImageDto avatar;
    private String username;
    private Long id;

}
