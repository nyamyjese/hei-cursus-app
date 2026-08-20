package com.hei.school.service.transcript;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hei.school.file.bucket.BucketComponent;
import com.hei.school.mail.Email;
import com.hei.school.mail.Mailer;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TranscriptServiceTest {

  @Mock TranscriptPdfService pdfService;
  @Mock BucketComponent bucketComponent;
  @Mock Mailer mailer;

  @InjectMocks TranscriptService transcriptService;

  @Test
  void sends_email_and_uploads_to_bucket() throws Exception {
    TranscriptData data = new TranscriptData("STD21001", "Jesse", 1, false, List.of(), 10.0);
    File dummyFile = File.createTempFile("dummy", ".pdf");
    dummyFile.deleteOnExit();

    when(pdfService.generate(data)).thenReturn(dummyFile);

    transcriptService.sendTranscriptByEmail(data, "jese@hei.school");

    verify(bucketComponent).upload(dummyFile, "transcripts/STD21001-1.pdf");
    verify(mailer).accept(any(Email.class));
  }
}
