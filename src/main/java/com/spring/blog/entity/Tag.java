package com.spring.blog.entity;

import com.spring.blog.entity.common.LocalDate;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    @ToString.Exclude
    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new LinkedHashSet<>();

    @Embedded
    private LocalDate date;

}
