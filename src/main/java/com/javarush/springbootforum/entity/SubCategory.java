package com.javarush.springbootforum.entity;

import com.javarush.springbootforum.listener.SubCategoryListener;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sub_categories", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "category")
@EqualsAndHashCode(exclude = "category", callSuper = false)
@EntityListeners(SubCategoryListener.class)
public class SubCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;
    private String description;

    @Column(name = "topic_count")
    private Long topicCount;
}