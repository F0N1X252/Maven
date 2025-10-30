package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    private static final int LENGTH_NIM = 5;

    public static int getLengthNim() {
        return LENGTH_NIM;
    }

    public StudentService() {

    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student addStudent(StudentRequest student) {
        String nim = (student.getNim() == null || student.getNim().isBlank()) ? generateNim() : student.getNim();
        Student savedStudent = new Student(
                nim,
                student.getFullName(),
                student.getDob(),
                student.getAddress());
        students.add(savedStudent);
        return savedStudent;
    }

    private String generateNim() {
        int maxNim = 0;
        for (Student student : students) {
            String s = student.getNim();
            if (s == null || s.isBlank()) {
                continue;
            }
            try {
                int nimNumber = Integer.parseInt(s);
                if (nimNumber > maxNim) {
                    maxNim = nimNumber;
                }
            } catch (

            NumberFormatException e) {
                // ignore non-numeric NIM values
            }
        }
        int next = maxNim + 1;
        String padded = String.format("%0" + LENGTH_NIM + "d", next);
        return padded;
    }

    public void updateStudent(String nim, Student updatedStudent) {
        Student existingStudent = students.stream()
                .filter(student -> student.getNim().equals(nim))
                .findFirst()
                .orElse(null);
        if (existingStudent != null) {
            existingStudent.setFullName(updatedStudent.getFullName());
            existingStudent.setDob(updatedStudent.getDob());
            existingStudent.setAddress(updatedStudent.getAddress());
        }
    }

    public void deleteStudent(String nim) {
        Optional<Student> studentOptional = students.stream()
                .filter(student -> student.getNim().equals(nim))
                .findFirst();
        if (studentOptional.isPresent()) {
            Student studentToBeDeleted = studentOptional.get();
            students.remove(studentToBeDeleted);
        } else {
            throw new RuntimeException("Student with NIM " + nim + " not found.");
        }
    }

    public Student findStudent(String nim) {
        return students.stream()
                .filter(student -> student.getNim().equals(nim))
                .findFirst()
                .orElse(null);
    }
}