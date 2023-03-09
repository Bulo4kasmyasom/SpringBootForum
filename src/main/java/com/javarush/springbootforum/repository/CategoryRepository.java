package com.javarush.springbootforum.repository;

import com.javarush.springbootforum.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    @EntityGraph(attributePaths = "subCategory")
    List<Category> findAll();
}
