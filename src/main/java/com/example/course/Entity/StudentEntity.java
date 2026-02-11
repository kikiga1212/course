package com.example.course.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//학생테이블
@Entity
@Table(name = "student")
@Getter @Setter
@Builder @ToString(exclude = "enrollments")//ToString 2개있으면 무한루프에 빠지므로 둘중에 한군데에서 제외처리함
@AllArgsConstructor @NoArgsConstructor
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//생성전략
    private Long id; //번호
    @Column(length = 50, nullable = false)
    private String name; //학생이름
    @Column(length = 100, nullable = false, unique = true)
    private String email; //이메일(생략불가능, 유일한값)

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnrollmentEntity> enrollments = new ArrayList<>(); //초기값 지정

}
