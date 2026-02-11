package com.example.course.Repository;

import com.example.course.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//학생데이터베스 처리
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    //findById, findByIds(리스트로 처리), save, saves, deleteById, deleteByIds
    //기본제공을 제외하고 필요한 메소드들만 지정
    //Entity의 필드명으로 메소드를 구성하는 것을 JPA메소드


    //이메일로 학생조회(비교할 값의 변수명)
    Optional<StudentEntity> findByEmail(String email);

    //이름으로 학생검색
    Optional<StudentEntity> findByName(String name);
}
