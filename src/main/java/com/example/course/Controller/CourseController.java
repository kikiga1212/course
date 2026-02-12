package com.example.course.Controller;

import com.example.course.DTO.CourseDTO;
import com.example.course.Service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    //전체조회
    @GetMapping
    public String ListCourses(Model model){
        List<CourseDTO> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "course/list";
    }
    //상세조회
    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Long id, Model model){
        CourseDTO course = courseService.getCourse(id);
        model.addAttribute("course", course);
        return "course/view";
    }

    //삽입(등록)
    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("course", new CourseDTO());
        return "course/create";
    }

    //삽입처리
    @PostMapping("/new")
    public String createProc(CourseDTO dto){
        courseService.createCourse(dto);
        return "redirect:/courses";//학생목록페이지로 이동
    }

    //수정
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        CourseDTO course = courseService.getCourse(id);
        model.addAttribute("course", course);
        return "course/edit";
    }

    //수정처리
    @PostMapping("/edit/{id}")
    public String editProc(@PathVariable Long id, CourseDTO dto){
        courseService.updateCourse(id, dto);
        return "redirect:/courses";
    }

    //삭제
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
