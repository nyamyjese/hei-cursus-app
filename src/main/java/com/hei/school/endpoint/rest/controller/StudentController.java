package com.hei.school.endpoint.rest.controller;

import com.hei.school.service.diploma.StudentService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @GetMapping("/{studentId}/graduation-status")
  public boolean checkGraduationStatus(@PathVariable UUID studentId, @RequestParam UUID trackId) {

    return studentService.isGraduated(studentId, trackId);
  }

  @GetMapping("/{studentId}/year-validation")
  public boolean checkYearValidation(
      @PathVariable UUID studentId, @RequestParam UUID trackId, @RequestParam int academicYear) {

    return studentService.isYearValidated(studentId, trackId, academicYear);
  }
}
