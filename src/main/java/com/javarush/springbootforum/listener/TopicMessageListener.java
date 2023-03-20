package com.javarush.springbootforum.listener;

import com.javarush.springbootforum.entity.Topic;
import com.javarush.springbootforum.entity.TopicMessage;
import jakarta.persistence.PrePersist;

public class TopicMessageListener {

    @PrePersist
    public void setTopicMessageCount(TopicMessage topicMessage) {
        Topic topic = topicMessage.getTopic();
        topic.setMessageCount(topic.getMessageCount() + 1L);
    }
}