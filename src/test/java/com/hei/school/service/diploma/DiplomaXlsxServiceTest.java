package com.hei.school.service.diploma;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hei.school.service.diploma.DiplomaList.Graduate;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiplomaXlsxServiceTest {

  @Test
  void generates_excel_successfully() {
    DiplomaXlsxService service = new DiplomaXlsxService();

    DiplomaList data =
        new DiplomaList(
            "PROMO_2026",
            List.of(
                new Graduate(1, "STD21001", "Doe", "John", 16.5),
                new Graduate(2, "STD21002", "Pinkman", "Jese", 14.2)));

    File excelFile = service.generate(data);

    assertTrue(excelFile.exists());
    assertTrue(excelFile.length() > 0);

    excelFile.delete();
  }
}
