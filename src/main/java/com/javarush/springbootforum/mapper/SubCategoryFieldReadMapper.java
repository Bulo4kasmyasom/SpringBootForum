package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.SubCategoryFieldReadDto;
import com.javarush.springbootforum.entity.SubCategory;
import org.springframework.stereotype.Component;

@Component
public class SubCategoryFieldReadMapper implements Mapper<SubCategory, SubCategoryFieldReadDto> {

    @Override
    public SubCategoryFieldReadDto map(SubCategory object) {
        return new SubCategoryFieldReadDto(
                object.getTitle(),
                object.getDescription()
        );
    }

}
