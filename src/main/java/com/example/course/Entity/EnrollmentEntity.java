package com.example.course.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

//수강신청테이블
@Entity
@Table(name = "enrollment")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//번호
    @Column(nullable = false)
    private LocalDate enrollDate;//수강신청일
    @Column
    private Integer score; //성적

    //부모와 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)//이름 중복 불가능
    private StudentEntity student;//mappedBy 와 동일한 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;
}
