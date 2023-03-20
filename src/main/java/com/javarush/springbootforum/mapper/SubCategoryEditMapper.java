package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.SubCategoryEditDto;
import com.javarush.springbootforum.entity.SubCategory;
import org.springframework.stereotype.Component;

@Component
public class SubCategoryEditMapper implements Mapper<SubCategoryEditDto, SubCategory> {

    @Override
    public SubCategory map(SubCategoryEditDto object) {
        return null;
    }

    @Override
    public SubCategory map(SubCategoryEditDto fromObject, SubCategory toObject) {
        toObject.setTitle(fromObject.getTitle());
        toObject.setDescription(fromObject.getDescription());
        return toObject;
    }

}
