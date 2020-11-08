package ru.school.matcha.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.school.matcha.domain.Image;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ImageMapper {

    void createImage(Image image);

    Optional<Image> getImageById(Long id);

    Optional<Image> getImageByExternalId(String externalId);

    Optional<Image> getAvatarByUserId(Long userId);

    List<Image> getImagesByUserId(Long userId);

    void deleteImageById(Long id);

    void deleteImageByExternalId(String externalId);

    void deleteAllImagesByUserId(Long userId);

}
