package com.javarush.springbootforum.listener;

import com.javarush.springbootforum.entity.Category;
import com.javarush.springbootforum.entity.SubCategory;
import com.javarush.springbootforum.entity.Topic;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PrePersist;

public class TopicListener {

    @PrePersist
    public void setTopicCountPersist(Topic topic) {
        Category category = topic.getCategory();
        SubCategory subCategory = topic.getSubCategory();

        if (subCategory != null) {
            subCategory.setTopicCount(subCategory.getTopicCount() + 1L);
            category.setTopicCount(category.getTopicCount() + 1L);
        } else {
            category.setTopicCount(category.getTopicCount() + 1L);
        }
    }

    @PostRemove
    public void setTopicCountRemove(Topic topic) {
        Category category = topic.getCategory();
        SubCategory subCategory = topic.getSubCategory();

        if (subCategory != null) {
            subCategory.setTopicCount(subCategory.getTopicCount() - 1L);
            category.setTopicCount(category.getTopicCount() - 1L);
        } else {
            category.setTopicCount(category.getTopicCount() - 1L);
        }
    }

}
