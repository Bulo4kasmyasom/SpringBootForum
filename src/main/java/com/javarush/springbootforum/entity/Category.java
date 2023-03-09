package com.javarush.springbootforum.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

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

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    private String title;
    private String description;

    @OneToMany(mappedBy = "category")
    @BatchSize(size = 30)// todo bad?
    List<SubCategory> subCategoryList;
}