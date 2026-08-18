package com.hei.school.service.diploma;

import com.hei.school.file.bucket.BucketComponent;
import java.io.File;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DiplomaService {

  private final DiplomaXlsxService diplomaXlsxService;
  private final BucketComponent bucketComponent;

  public File generateAndArchive(DiplomaList data) {
    var file = diplomaXlsxService.generate(data);
    var bucketKey = "diplomas/" + data.promotionName() + ".xlsx";
    bucketComponent.upload(file, bucketKey); // archivage S3, comme demandé dans le sujet
    return file; // servi directement en téléchargement, pas d'email
  }
}
