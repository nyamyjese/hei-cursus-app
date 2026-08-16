package com.hei.school.endpoint.rest.controller;

import com.hei.school.service.transcript.TranscriptData;
import com.hei.school.service.transcript.TranscriptData.CourseGrade;
import com.hei.school.service.transcript.TranscriptPdfService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TranscriptTestController {
  private final TranscriptPdfService transcriptPdfService;

  @GetMapping("/test/transcript")
  public ResponseEntity<FileSystemResource> testTranscript() {
    var data =
        new TranscriptData(
            "STD001",
            "Rakoto Jean",
            2026,
            false,
            List.of(new CourseGrade("Prog4", 4, 14.5), new CourseGrade("Sys3", 4, 11.0)),
            12.75);
    var file = transcriptPdfService.generate(data);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=releve.pdf")
        .body(new FileSystemResource(file));
  }
}
