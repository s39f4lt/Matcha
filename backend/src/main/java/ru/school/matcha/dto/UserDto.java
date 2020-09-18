package ru.school.matcha.dto;

import lombok.Data;
import ru.school.matcha.enums.Gender;

import java.util.Date;

@Data
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private Date birthday;
    private String description;
    private Boolean isActive;
    private FormDto form;
}
