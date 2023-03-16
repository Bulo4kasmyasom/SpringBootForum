package com.javarush.springbootforum.repository;

import com.javarush.springbootforum.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findAllByCategoryIdAndSubCategoryIsNull(Long id, Pageable pageable);

    Page<Topic> findAllByCategoryIdAndSubCategoryId(Long category, Long subCategory, Pageable pageable);
}
