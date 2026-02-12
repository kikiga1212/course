package com.example.course.Controller;

import com.example.course.DTO.StudentDTO;
import com.example.course.Service.StudentService;
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
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    //전체조회
    @GetMapping
    public String ListStudents(Model model){
        List<StudentDTO> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "student/list";
    }
    //상세조회
    @GetMapping("/{id}")
    public String viewStudent( @PathVariable Long id, Model model){
        StudentDTO student = studentService.getStudent(id);
        model.addAttribute("student", student);
        return "student/view";
    }

    //삽입(등록)
    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("student", new StudentDTO());
        return "student/create";
    }

    //삽입처리
 @PostMapping("/new")
    public String createProc(StudentDTO dto){
        studentService.createStudent(dto);
        return "redirect:/students";//학생목록페이지로 이동
    }

    //수정
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        StudentDTO student = studentService.getStudent(id);
        model.addAttribute("student", student);
        return "student/edit";
    }

    //수정처리
    @PostMapping("/edit/{id}")
    public String editProc(@PathVariable Long id, StudentDTO dto){
        studentService.updateStudent(id, dto);
        return "redirect:/students";
    }

    //삭제
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
