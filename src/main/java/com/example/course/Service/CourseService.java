package com.example.course.Service;

import com.example.course.DTO.CourseDTO;
import com.example.course.DTO.StudentDTO;
import com.example.course.Entity.CourseEntity;
import com.example.course.Entity.StudentEntity;
import com.example.course.Repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    //전체조회
    public List<CourseDTO> getAllCourses(){
        return courseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    //개별조회
    public CourseDTO getCourse(Long id){
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));
        return mapToDTO(course);
    }

    //삽입
    public CourseDTO createCourse(CourseDTO dto) {
        CourseEntity entity = modelMapper.map(dto, CourseEntity.class);
        return mapToDTO(courseRepository.save(entity));
    }
    //수정
    public CourseDTO updateCourse(Long id, CourseDTO dto) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));
        course.setTitle(dto.getTitle());
        course.setCredit(dto.getCredit());
        course.setProfessor(dto.getProfessor());
        return mapToDTO(courseRepository.save(course));
    }
    //삭제
    public boolean deleteCourse(Long id) {
        if(courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //변환
    private CourseDTO mapToDTO(CourseEntity course){
        //학생 정보
        CourseDTO dto = modelMapper.map(course, CourseDTO.class);
        //수강과목 목록
        if(course.getEnrollments() !=null){
            dto.setStudentNames(
                    course.getEnrollments()
                            .stream()
                            .map(e->e.getCourse().getTitle())
                            .collect(Collectors.toList())
            );
            //수강과목 수
            dto.setEnrolmentCount(course.getEnrollments().size());
        }else{
            dto.setEnrolmentCount(0);
        }
        return dto;
    }
}
