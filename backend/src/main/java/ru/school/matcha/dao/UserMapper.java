package ru.school.matcha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.school.matcha.domain.Credentials;
import ru.school.matcha.domain.User;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    Long createUser(@Param("credentials") Credentials credentials, @Param("formId") Long formId);

    void updateUser(User user);

    void deleteUserById(Long id);

    void deleteUserByUsername(String username);
}
