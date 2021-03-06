package ru.school.matcha.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.school.matcha.enums.Role;

import java.util.Date;

@Data
@NoArgsConstructor
public class User {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String gender;
    private Date birthday;
    private String description;
    private Boolean isActive;
    private Form form;
    private Long rate;
    private Image avatar;
    private Role role;
    private Double latitude;
    private Double longitude;
    private Boolean isVerified;
    private Boolean isOnline;
    private Date lastLoginDate;
    private Date createTs;
    private Date updateTs;
    private Date deleteTs;

}
