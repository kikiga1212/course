package com.example.course.Service;

import com.example.course.DTO.EnrollmentDTO;
import com.example.course.Entity.CourseEntity;
import com.example.course.Entity.EnrollmentEntity;
import com.example.course.Entity.StudentEntity;
import com.example.course.Repository.CourseRepository;
import com.example.course.Repository.EnrollmentRepository;
import com.example.course.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//수강신청(자식테이블)->저장,수정할때ㅑ 부모테이블 정보가 필요
//학생과 수강과목(부모테이블)
@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    //개별조회(수강신청한)
    public EnrollmentDTO getEnrollment(Long id){
        EnrollmentEntity enrollment = enrollmentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("수강 정보 없음"));
        return mapToDTO(enrollment);
    }

    //학생별 조회(학생이 수강신청한)
    public List<EnrollmentDTO> getEnrollmentsByStudent(Long studentId){
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(()->new IllegalArgumentException("학생 정보 없음"));
        return enrollmentRepository.findByStudent(student)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //수강신청 등록
    public EnrollmentDTO enroll(EnrollmentDTO dto){
        //수강신청에 필요한 신청학생, 신청수강과목조회
        StudentEntity student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("학생 없음"));

        CourseEntity course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("수강 과목 없음"));

        //기존 수강상태 확인
        enrollmentRepository.findByStudentAndCourse(student, course)
                .ifPresent(e->{
                    throw  new IllegalArgumentException("이미 수강신청한 과목");

                });
        EnrollmentEntity save = enrollmentRepository.save(
                EnrollmentEntity.builder()//여러개의 필드를 한꺼번에 모아서 저장
                        .student(student)
                        .course(course)
                        .enrollDate(LocalDate.now())
                        .score(dto.getScore())
                        .build()
        );
        return mapToDTO(save);
    }

    //수정
    public EnrollmentDTO updateEnrollment(Long enrollmentId, Integer score){
        //학생Entity, 수강과목Entity존재
        EnrollmentEntity enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("수강정보가 없음"));
        enrollment.setScore(score);
        return mapToDTO(enrollmentRepository.save(enrollment));
    }

    //삭제
    public boolean deleteEnrollment(Long enrollmentId){
        if(enrollmentRepository.existsById(enrollmentId)){
            enrollmentRepository.deleteById(enrollmentId);
            return true;//삭제성공
        }
        return false;
    }








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
