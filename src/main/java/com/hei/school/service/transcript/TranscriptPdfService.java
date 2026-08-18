package com.hei.school.service.transcript;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class TranscriptPdfService {

  @SneakyThrows
  public File generate(TranscriptData data) {
    var file = File.createTempFile("releve-" + data.studentId(), ".pdf");
    var document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(file));
    document.open();

    var titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
    var normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

    document.add(new Paragraph("Relevé de notes — HEI", titleFont));
    document.add(
        new Paragraph(
            "Étudiant : " + data.studentFullName() + " (" + data.studentId() + ")", normalFont));
    document.add(new Paragraph("Année : " + data.year(), normalFont));
    document.add(
        new Paragraph(
            data.isComplete() ? "Statut : COMPLET" : "Statut : PROVISOIRE (incomplet)",
            normalFont));
    document.add(new Paragraph(" "));

    var table = new PdfPTable(3);
    table.addCell(headerCell("Cours"));
    table.addCell(headerCell("Crédits"));
    table.addCell(headerCell("Note"));

    for (var grade : data.grades()) {
      table.addCell(new PdfPCell(new Paragraph(grade.courseName(), normalFont)));
      table.addCell(new PdfPCell(new Paragraph(String.valueOf(grade.credits()), normalFont)));
      table.addCell(
          new PdfPCell(new Paragraph(String.format("%.2f/20", grade.grade()), normalFont)));
    }
    document.add(table);

    document.add(new Paragraph(" "));
    document.add(
        new Paragraph(
            String.format("Moyenne générale : %.2f/20", data.generalAverage()),
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));

    document.close();
    return file;
  }

  private PdfPCell headerCell(String text) {
    var cell =
        new PdfPCell(new Paragraph(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    return cell;
  }
}
