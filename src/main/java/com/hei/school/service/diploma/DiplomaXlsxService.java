package com.hei.school.service.diploma;

import java.io.File;
import java.io.FileOutputStream;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class DiplomaXlsxService {

  @SneakyThrows
  public File generate(DiplomaList data) {
    var file = File.createTempFile("diplomes-" + data.promotionName(), ".xlsx");

    try (var workbook = new XSSFWorkbook();
        var out = new FileOutputStream(file)) {
      var sheet = workbook.createSheet("Diplômés " + data.promotionName());

      var headerStyle = workbook.createCellStyle();
      var headerFont = workbook.createFont();
      headerFont.setBold(true);
      headerStyle.setFont(headerFont);
      headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
      headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

      var headerRow = sheet.createRow(0);
      String[] headers = {"Rang", "STD", "Nom", "Prénom", "Moyenne générale"};
      for (int i = 0; i < headers.length; i++) {
        var cell = headerRow.createCell(i);
        cell.setCellValue(headers[i]);
        cell.setCellStyle(headerStyle);
      }

      int rowIdx = 1;
      for (var graduate : data.graduates()) {
        var row = sheet.createRow(rowIdx++);
        row.createCell(0).setCellValue(graduate.rank());
        row.createCell(1).setCellValue(graduate.studentId());
        row.createCell(2).setCellValue(graduate.lastName());
        row.createCell(3).setCellValue(graduate.firstName());
        row.createCell(4).setCellValue(graduate.generalAverage());
      }

      for (int i = 0; i < headers.length; i++) {
        sheet.autoSizeColumn(i);
      }

      workbook.write(out);
    }
    return file;
  }
}
