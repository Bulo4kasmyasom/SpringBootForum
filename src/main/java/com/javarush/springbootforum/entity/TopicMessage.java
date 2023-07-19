package com.javarush.springbootforum.entity;

import com.javarush.springbootforum.listener.TopicMessageListener;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topic_messages", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"topic", "author"})
@EqualsAndHashCode(exclude = {"topic", "author"}, callSuper = false)
@EntityListeners(TopicMessageListener.class)
public class TopicMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
}
