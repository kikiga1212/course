package com.example.course.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//강의테이블
@Entity
@Table(name = "course")
@Getter
@Setter
@Builder
@ToString(exclude = "enrollments")
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//번호
    @Column(length = 100, nullable = false)
    private String title;//과목명
    @Column(nullable = false)
    private int credit;//학점
    @Column(length = 50, nullable = false)
    private String professor;//담당교수

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnrollmentEntity> enrollments = new ArrayList<>();
}
