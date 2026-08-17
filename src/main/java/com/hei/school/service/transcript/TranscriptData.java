package com.hei.school.service.transcript;

import java.util.List;

public record TranscriptData(
    String studentId,
    String studentFullName,
    int year,
    boolean isComplete,
    List<CourseGrade> grades,
    double generalAverage) {
  public record CourseGrade(String courseName, int credits, double grade) {}
}
