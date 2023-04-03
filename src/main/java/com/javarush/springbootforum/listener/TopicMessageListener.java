package com.javarush.springbootforum.listener;

import com.javarush.springbootforum.entity.Topic;
import com.javarush.springbootforum.entity.TopicMessage;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PrePersist;

public class TopicMessageListener {
    @PrePersist
    public void setTopicMessageCountWhenPersist(TopicMessage topicMessage) {
        Topic topic = topicMessage.getTopic();
        topic.setMessageCount(topic.getMessageCount() + 1L);
    }

    @PostRemove
    public void setTopicMessageCountWhenRemove(TopicMessage topicMessage) {
        Topic topic = topicMessage.getTopic();
        if (topic.getMessageCount() > 1)
            topic.setMessageCount(topic.getMessageCount() - 1L);
        else
            topic.setMessageCount(0L);
    }
}