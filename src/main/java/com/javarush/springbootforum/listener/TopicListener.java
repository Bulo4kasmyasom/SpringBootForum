package com.javarush.springbootforum.listener;

import com.javarush.springbootforum.entity.Category;
import com.javarush.springbootforum.entity.SubCategory;
import com.javarush.springbootforum.entity.Topic;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class TopicListener {

    @PrePersist
    public void setTopicCount(Topic topic) {
        Category category = topic.getCategory();
        SubCategory subCategory = topic.getSubCategory();

        if (subCategory != null) {
            setTopicCountInSubCategory(subCategory);
            setTopicCountInCategory(category);
        } else {
            setTopicCountInCategory(category);
        }
    }

    private void setTopicCountInSubCategory(SubCategory subCategory) {
        subCategory.setTopicCount(subCategory.getTopicCount() + 1L);
    }

    private void setTopicCountInCategory(Category category) {
        category.setTopicCount(category.getTopicCount() + 1L);
    }
}
