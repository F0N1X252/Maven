package com.example.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.StudentService;
import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRequest;

import org.springframework.http.ResponseEntity;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Saya menghapus @ResponseStatus karena sudah ada ResponseEntity
    @PostMapping
    public ResponseEntity<String> createStudent(@Valid @RequestBody StudentRequest studentRequest) {
        studentService.addStudent(studentRequest);
        String successMessage = "Student has been successfully added.";
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }

    @DeleteMapping("/{nim}")
    public ResponseEntity<String> deleteStudent(@PathVariable String nim) {
        studentService.deleteStudent(nim);
        String successMessage = "Student data has been successfully deleted.";
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    // --- PERUBAHAN DI SINI ---
    @PutMapping("/{nim}")
    public ResponseEntity<String> updateStudent(@PathVariable String nim, @Valid @RequestBody StudentRequest updatedStudentRequest) {
        studentService.updateStudent(nim, updatedStudentRequest);
        String successMessage = "Student data has been successfully updated.";
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    @GetMapping("/{nim}")
    public ResponseEntity<Student> getStudentByNim(@PathVariable String nim) {
        var student = studentService.findStudent(nim);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}