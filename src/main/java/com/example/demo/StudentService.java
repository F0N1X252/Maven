package com.example.demo;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    public StudentService() {
        // data dummy :)
        students.add(new Student("12345",
                "Budi Santoso",
                LocalDate.of(2002, 8, 17),
                "Jl. Merdeka No. 10"));
        students.add(new Student("67890",
                "Ani Yudhoyono",
                LocalDate.of(2001, 5, 20),
                "Jl. Pahlawan No. 5"));
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }
}