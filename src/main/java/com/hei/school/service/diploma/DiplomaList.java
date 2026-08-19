package com.hei.school.service.diploma;

import java.util.List;

public record DiplomaList(String promotionName, List<Graduate> graduates) {
  public record Graduate(
      int rank, String studentId, String lastName, String firstName, double generalAverage) {}
}
