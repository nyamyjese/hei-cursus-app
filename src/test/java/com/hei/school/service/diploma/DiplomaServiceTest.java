package com.hei.school.service.diploma;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hei.school.file.bucket.BucketComponent;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiplomaServiceTest {

  @Mock DiplomaXlsxService xlsxService;
  @Mock BucketComponent bucketComponent;

  @InjectMocks DiplomaService diplomaService;

  @Test
  void generates_and_archives_diploma_list() throws Exception {
    DiplomaList data = new DiplomaList("PROMO_2026", List.of());
    File dummyFile = File.createTempFile("dummy", ".xlsx");
    dummyFile.deleteOnExit();

    when(xlsxService.generate(data)).thenReturn(dummyFile);

    diplomaService.generateAndArchive(data);

    verify(xlsxService).generate(data);
    verify(bucketComponent).upload(dummyFile, "diplomas/PROMO_2026.xlsx");
  }
}
