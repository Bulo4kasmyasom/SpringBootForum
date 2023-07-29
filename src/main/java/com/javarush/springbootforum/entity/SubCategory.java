package com.javarush.springbootforum.entity;

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
public class SubCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;
    private String description;

    @Builder.Default
    @Column(name = "topic_count")
    private Long topicCount = 0L;
}