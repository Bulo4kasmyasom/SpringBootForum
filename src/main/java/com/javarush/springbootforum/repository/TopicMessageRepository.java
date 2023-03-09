package com.javarush.springbootforum.repository;

import com.javarush.springbootforum.entity.TopicMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicMessageRepository extends JpaRepository<TopicMessage, Long> {
    Page<TopicMessage> findAllByTopicId(Long id, Pageable pageable);
}
