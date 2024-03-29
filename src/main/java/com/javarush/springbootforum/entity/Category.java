package com.javarush.springbootforum.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "section")
@EqualsAndHashCode(exclude = "section", callSuper = false)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    private String title;
    private String description;

    @Builder.Default
    @Column(name = "topic_count")
    private Long topicCount = 0L;

    @Builder.Default
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @BatchSize(size = 30)
    List<SubCategory> subCategoryList = new ArrayList<>();
}