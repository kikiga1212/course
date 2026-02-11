package com.example.course.DTO;

import lombok.*;

import java.util.List;

//학생작업을 위한 DTO
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private String email;

    private List<String> courseTitles;//수강과목 목록
    private int enrollmentCount;    //수강신청수
}
