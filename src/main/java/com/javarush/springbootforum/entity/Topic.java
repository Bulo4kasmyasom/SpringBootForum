package com.javarush.springbootforum.entity;

import com.javarush.springbootforum.listener.TopicListener;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topics", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"category", "subCategory", "author"})
@EqualsAndHashCode(exclude = {"category", "subCategory", "author"}, callSuper = false)
@EntityListeners(TopicListener.class)
public class Topic extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Builder.Default
    @Column(name = "message_count")
    private Long messageCount = 0L;
}
