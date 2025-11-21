package com.example.demo.Service;

import org.springframework.stereotype.Service;
import com.example.demo.domain.Student;
import com.example.demo.entity.StudentEntity;
import com.example.demo.domain.StudentRequest;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    // --- Ambil semua data dari DATABASE ---
    public List<Student> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    // --- Simpan data ke DATABASE ---
    public Student addStudent(StudentRequest request) {
        StudentEntity entity = new StudentEntity();
        entity.setNim(request.getNim());
        entity.setName(request.getFullName());
        entity.setAddress(request.getAddress());
        entity.setDob(request.getDob());

        StudentEntity savedEntity = studentRepository.save(entity);
        return mapEntityToDomain(savedEntity);
    }

    // --- Update data di DATABASE ---
    public void updateStudent(String nim, StudentRequest request) {
        StudentEntity existingEntity = studentRepository.findByNim(nim)
                .orElseThrow(() -> new RuntimeException("Student with NIM " + nim + " not found."));

        existingEntity.setName(request.getFullName());
        existingEntity.setDob(request.getDob());
        existingEntity.setAddress(request.getAddress());

        studentRepository.save(existingEntity);
    }

    // --- Hapus data dari DATABASE ---
    public void deleteStudent(String nim) {
        StudentEntity entity = studentRepository.findByNim(nim)
                .orElseThrow(() -> new RuntimeException("Student with NIM " + nim + " not found."));
        studentRepository.delete(entity);
    }

    // --- Cari data di DATABASE ---
    public Student findStudent(String nim) {
        return studentRepository.findByNim(nim)
                .map(this::mapEntityToDomain)
                .orElse(null);
    }

    // Helper method untuk konversi Entity -> Domain
    private Student mapEntityToDomain(StudentEntity entity) {
        Student student = new Student();
        student.setNim(entity.getNim());
        student.setFullName(entity.getName());
        student.setAddress(entity.getAddress());
        student.setDob(entity.getDob()); // Tidak perlu parsing lagi!
        return student;
    }
}