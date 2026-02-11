package com.example.course.Repository;

import com.example.course.Entity.CourseEntity;
import com.example.course.Entity.EnrollmentEntity;
import com.example.course.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//수강신청 데이터베이스 처리
@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    //해당학생의 수강목록
    //Entity의 특정필드로 조회할때 Entity명 필드명 순으로 작성
    //findByStudentId(Long Id)
    List<EnrollmentEntity> findByStudent(StudentEntity student);

    //해당과목의 수강생목록
    List<EnrollmentEntity> findByCourse(CourseEntity course);

    //해당학생이 수강시 이미 수강중인지 확인(여러 필드를 사용할 때는 And 또는 Or
    Optional<EnrollmentEntity> findByStudentAndCourse(StudentEntity student, CourseEntity course);
    // And 또는 Or를 이용해서 여러개의 필드를 사용할때는 필드수만큼 변수로 값을 전달


}
