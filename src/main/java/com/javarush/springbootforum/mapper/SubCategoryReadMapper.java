package com.javarush.springbootforum.mapper;


import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.entity.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubCategoryReadMapper implements Mapper<SubCategory, SubCategoryReadDto> {

    @Override
    public SubCategoryReadDto map(SubCategory object) {
        return new SubCategoryReadDto(
                object.getId(),
                object.getCategory().getId(),
                object.getCategory().getTitle(),
                object.getTitle(),
                object.getDescription(),
                object.getTopicCount()
        );
    }

}
