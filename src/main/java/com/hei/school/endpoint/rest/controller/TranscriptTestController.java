package com.hei.school.endpoint.rest.controller;

import com.hei.school.service.transcript.TranscriptData;
import com.hei.school.service.transcript.TranscriptData.CourseGrade;
import com.hei.school.service.transcript.TranscriptService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TranscriptTestController {
  private final TranscriptService transcriptService;

  @GetMapping("/test/transcript/send")
  public String testSendTranscript(@RequestParam String to) {
    var data = new TranscriptData(
            "STD001", "Rakoto Jean", 2026, false,
            List.of(
                    new CourseGrade("Prog4", 4, 14.5),
                    new CourseGrade("Sys3", 4, 11.0)),
            12.75);
    transcriptService.sendTranscriptByEmail(data, to);
    return "Relevé envoyé à " + to;
  }
}