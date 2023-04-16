package com.javarush.springbootforum.repository;

import com.javarush.springbootforum.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> findByCategoryIdAndId(Long categoryId, Long id);

}
