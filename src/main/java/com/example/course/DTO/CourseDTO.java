package com.example.course.DTO;

import lombok.*;

import java.util.List;

//수강과목 작업을 위한 DTO
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private int credit;
    private String professor;

    private List<String> studentNames;//수강한 명단
    private int enrolmentCount;//수강신청수

}
