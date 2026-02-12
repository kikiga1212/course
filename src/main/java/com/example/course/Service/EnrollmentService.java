package com.example.course.Service;

import com.example.course.DTO.EnrollmentDTO;
import com.example.course.Entity.EnrollmentEntity;
import com.example.course.Repository.CourseRepository;
import com.example.course.Repository.EnrollmentRepository;
import com.example.course.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

//수강신청(자식테이블)->저장,수정할때ㅑ 부모테이블 정보가 필요
//학생과 수강과목(부모테이블)
@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    //변환메소드
    private EnrollmentDTO mapToDTO(EnrollmentEntity e){
        EnrollmentDTO dto = modelMapper.map(e, EnrollmentDTO.class);
        dto.setStudentName(e.getStudent().getName());
        dto.setCourseTitle(e.getCourse().getTitle());
        if(e.getCourse() !=null){
            dto.setCredit(e.getCourse().getCredit());
        }else{
            dto.setCredit(0);
        }
        dto.setProfessor(e.getCourse().getProfessor());
        return dto;
    }
}
