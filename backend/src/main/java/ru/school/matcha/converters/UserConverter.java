package ru.school.matcha.converters;

import ru.school.matcha.domain.User;
import ru.school.matcha.dto.UserDto;

import static java.util.Objects.isNull;

public class UserConverter extends Converter<UserDto, User> {

    public UserConverter() {
        super(UserConverter::convertToEntity, UserConverter::convertToDto);
    }

    private static UserDto convertToDto(User source) {
        if (isNull(source)) {
            return null;
        }
        UserDto result = new UserDto();
        result.setUsername(source.getUsername());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setEmail(source.getEmail());
        result.setGender(source.getGender());
        result.setBirthday(source.getBirthday());
        result.setDescription(source.getDescription());
        return result;
    }

    private static User convertToEntity(UserDto source) {
        if (isNull(source)) {
            return null;
        }
        User result = new User();
        result.setUsername(source.getUsername());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setEmail(source.getEmail());
        result.setGender(source.getGender());
        result.setBirthday(source.getBirthday());
        result.setDescription(source.getDescription());
        return result;
    }

}
