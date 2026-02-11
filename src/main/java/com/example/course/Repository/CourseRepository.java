package com.example.course.Repository;

import com.example.course.Entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//수강과목 데이터베이스 처리
@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    // 과목명으로 검색
    //메소드명이 필드명으로 되어있으면 100%일치할때 조회
    //비슷하거나 내용이 포함된 경우에는 필드명뒤에 조건을 추가
    //select * from CourseEntity where title like %:Title%
    List<CourseEntity> findByTitleContaining(String title);

    //교수명으로 개설과목 조회
    //select * from CourseEntity where professor like :Professor
    List<CourseEntity>findByProfessor(String professor);

    //학점 기준 조회
    List<CourseEntity> findByCredit(int credit);
}
