package com.example.course.DTO;

import lombok.*;

import java.time.LocalDate;

//수강신청 작업을 위한
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {
    private Long enrollmentId;//수강신청번호
    private LocalDate enrollDate; //수강신청일
    private int score;//점수

    //부모테이블의 정보가 많으면 studentDTO로 한번에 읽어오기
    private Long studentId;//학생번호
    private String studentName;//학생이름

    private Long courseId;//과목번호
    private String courseTitle;//과목명
    private int credit;//학점
    private String professor;//담당교수

}
