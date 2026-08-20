package com.hei.school.service.transcript;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hei.school.service.transcript.TranscriptData.CourseGrade;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;

class TranscriptPdfServiceTest {

  @Test
  void generates_pdf_successfully() {
    TranscriptPdfService service = new TranscriptPdfService();

    TranscriptData data =
        new TranscriptData(
            "STD21001",
            "Jesse",
            1,
            true,
            List.of(new CourseGrade("Programmation", 6, 15.0), new CourseGrade("Math", 4, 8.0)),
            12.2);

    File pdf = service.generate(data);

    assertTrue(pdf.exists());
    assertTrue(pdf.length() > 0);

    pdf.delete();
  }
}
