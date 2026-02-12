package com.example.course.Service;

import com.example.course.DTO.StudentDTO;
import com.example.course.Entity.StudentEntity;
import com.example.course.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    //메소드체이닝 : 메소드와 메소드를 .으로 연결해서 연속적인 처리
    //.앞에 메소드를 결과를 .뒤에 메소드에 전달
    //전체조회
    public List<StudentDTO> getStudents(){
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //개별조회
    public StudentDTO getStudent(Long id){
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("학생이 존재하지 않습니다."));

        return mapToDTO(student);
    }

    //삽입
    public StudentDTO createStudent(StudentDTO dto){
        Optional<StudentEntity> byEmail = studentRepository.findByEmail(dto.getEmail());

        if(byEmail.isPresent()){//이메일 중복확인
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }
        StudentEntity entity = modelMapper.map(dto, StudentEntity.class);

        return mapToDTO(studentRepository.save(entity));
    }

    //수정
    public StudentDTO updateStudent(Long id, StudentDTO dto){
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("학생이 존재하지 않습니다."));
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());

        return mapToDTO(studentRepository.save(student));
    }

    //삭제
    public boolean deleteStudent(Long id){
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    //map으로 받은 값을 DTO변환하는 메소드
    private StudentDTO mapToDTO(StudentEntity student){
        //학생 정보
        StudentDTO dto = modelMapper.map(student, StudentDTO.class);
        //수강과목 목록
        if(student.getEnrollments() !=null){
            dto.setCourseTitles(
                    student.getEnrollments()
                            .stream()
                            .map(e->e.getCourse().getTitle())
                            .collect(Collectors.toList())
            );
            //수강과목 수
            dto.setEnrollmentCount(student.getEnrollments().size());
        }else{
            dto.setEnrollmentCount(0);
        }
        return dto;
    }
}
