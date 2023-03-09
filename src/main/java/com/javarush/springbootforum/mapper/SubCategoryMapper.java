package com.javarush.springbootforum.mapper;


import com.javarush.springbootforum.dto.SubCategoryReadDto;
import com.javarush.springbootforum.entity.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubCategoryMapper implements Mapper<SubCategory, SubCategoryReadDto> {

    @Override
    public SubCategoryReadDto map(SubCategory object) {
        return new SubCategoryReadDto(
                object.getId(),
                object.getCategory().getTitle(),
                object.getTitle(),
                object.getDescription()
        );
    }

}
