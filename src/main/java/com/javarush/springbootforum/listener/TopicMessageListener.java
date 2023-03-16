package com.javarush.springbootforum.listener;

import com.javarush.springbootforum.entity.Topic;
import com.javarush.springbootforum.entity.TopicMessage;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class TopicMessageListener {

    @PrePersist
    @PreUpdate
    public void setTopicMessageCount(TopicMessage topicMessage) {
        Topic topic = topicMessage.getTopic();
        if (topic.getMessageCount() == null)
            topic.setMessageCount(1L);
        else
            topic.setMessageCount(topic.getMessageCount() + 1L);
    }

}