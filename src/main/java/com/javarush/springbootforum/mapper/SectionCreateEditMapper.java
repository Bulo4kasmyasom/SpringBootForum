package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.SectionCreateEditDto;
import com.javarush.springbootforum.entity.Section;
import org.springframework.stereotype.Component;

@Component
public class SectionCreateEditMapper implements Mapper<SectionCreateEditDto, Section> {
    @Override
    public Section map(SectionCreateEditDto object) {
        return Section.builder()
                .title(object.getTitle())
                .description(object.getDescription())
                .build();
    }

    @Override
    public Section map(SectionCreateEditDto fromObject, Section toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(SectionCreateEditDto object, Section section) {
        section.setTitle(object.getTitle());
        section.setDescription(object.getDescription());
    }
}
