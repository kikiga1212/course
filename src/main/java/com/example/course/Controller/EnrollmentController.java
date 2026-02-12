package com.example.course.Controller;

import com.example.course.DTO.CourseDTO;
import com.example.course.DTO.EnrollmentDTO;
import com.example.course.DTO.StudentDTO;
import com.example.course.Service.CourseService;
import com.example.course.Service.EnrollmentService;
import com.example.course.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    //전체목록
    @GetMapping("/list")
    public String ListEnrollments(Model model){
        List<StudentDTO> allStudents = studentService.getAllStudents();
        model.addAttribute("students", allStudents);
        return "enrollment/list";
    }

    //지정학생의 수강신청목록
    @GetMapping("/student/{studentId}")
    public String ListByStudent(@PathVariable Long studentId, Model model){
        List<EnrollmentDTO> list = enrollmentService.getEnrollmentsByStudent(studentId);
        model.addAttribute("enrollments", list);
        return "enrollment/student_enrollments";
    }

    //수강신청폼
    @GetMapping("/new")
    public String createForm(Model model){
        //콤보 상자로 수장생과 수강과목을 선택해서 사용
        List<StudentDTO> students = studentService.getAllStudents();
        List<CourseDTO> courses = courseService.getAllCourses();

        model.addAttribute("students", students);
        model.addAttribute("courses", courses);
        model.addAttribute("enrollment", new EnrollmentDTO());
        return "enrollment/create";
    }

    //수강신청 처리
    @PostMapping("/new")
    public String createProc( EnrollmentDTO dto){
        enrollmentService.enroll(dto);
        return "redirect:/enrollments/list";
    }

    //수강신청 취소
    @PostMapping("/delete/{id}")
    public String cancel(@PathVariable Long id){
        enrollmentService.cancelEnrollment(id);
        return "redirect:/enrollments/list";
    }


}
