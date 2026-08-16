package com.hei.school.service.transcript;

import com.hei.school.file.bucket.BucketComponent;
import com.hei.school.mail.Email;
import com.hei.school.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TranscriptService {

  private final TranscriptPdfService transcriptPdfService;
  private final BucketComponent bucketComponent;
  private final Mailer mailer;

  @SneakyThrows
  public void sendTranscriptByEmail(TranscriptData data, String recipientEmail) {
    var pdfFile = transcriptPdfService.generate(data);

    var bucketKey = "transcripts/" + data.studentId() + "-" + data.year() + ".pdf";
    bucketComponent.upload(pdfFile, bucketKey);

    var email =
        new Email(
            new InternetAddress(recipientEmail),
            List.of(),
            List.of(),
            "Votre relevé de notes " + data.year() + (data.isComplete() ? "" : " (provisoire)"),
            buildHtmlBody(data),
            List.of(pdfFile));

    mailer.accept(email);
  }

  private String buildHtmlBody(TranscriptData data) {
    return """
           <p>Bonjour %s,</p>
           <p>Veuillez trouver ci-joint votre relevé de notes pour l'année %d.</p>
           <p>Statut : <strong>%s</strong></p>
           <p>Moyenne générale : <strong>%.2f/20</strong></p>
           <p>Cordialement,<br>HEI</p>
           """
        .formatted(
            data.studentFullName(),
            data.year(),
            data.isComplete() ? "Complet" : "Provisoire",
            data.generalAverage());
  }
}
