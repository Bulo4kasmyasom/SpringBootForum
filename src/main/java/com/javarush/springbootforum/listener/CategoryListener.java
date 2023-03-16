package com.javarush.springbootforum.listener;

import com.javarush.springbootforum.entity.Category;
import jakarta.persistence.PrePersist;

public class CategoryListener {

    @PrePersist
    public void setCount(Category category) {
        Long topicCount = category.getTopicCount();

        if (topicCount == null) topicCount = 0L; // todo преобразовать в long чтобы избавиться от null??

        category.setTopicCount(topicCount);
    }

}