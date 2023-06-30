package com.spring.blog.entity;

import com.spring.blog.entity.common.LocalDate;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "certifications")
public class Certification {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String id;
    private LocalDateTime expirationDate;
    private String token;
    private boolean expired;
    private Long userId;
    private String type;
    private LocalDate date;

}
