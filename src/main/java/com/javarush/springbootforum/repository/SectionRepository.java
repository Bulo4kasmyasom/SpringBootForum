package com.javarush.springbootforum.repository;

import com.javarush.springbootforum.entity.Section;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    @Override
    @EntityGraph(attributePaths = {"categoryList"})
    @NonNull
    List<Section> findAll();
}
