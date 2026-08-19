package com.hei.school.endpoint.rest.controller;

import com.hei.school.model.Grade;
import com.hei.school.service.diploma.GradeService;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses/{courseId}/grades")
@RequiredArgsConstructor
public class GradeController {

  private final GradeService gradeService;

  @PutMapping("/{gradeId}")
  public Grade updateGrade(
      @PathVariable UUID courseId,
      @PathVariable UUID gradeId,
      @RequestParam BigDecimal newValue,
      @RequestParam String reason,
      @RequestParam UUID teacherId) {

    return gradeService.updateGrade(gradeId, newValue, reason, teacherId);
  }
}
