package com.hei.school.endpoint.rest.controller;

import com.hei.school.service.diploma.DiplomaList;
import com.hei.school.service.diploma.DiplomaList.Graduate;
import com.hei.school.service.diploma.DiplomaService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DiplomaController {

  private final DiplomaService diplomaService;

  @GetMapping("/promotions/{promotionName}/diplomes")
  public ResponseEntity<FileSystemResource> downloadDiplomaList(
      @PathVariable String promotionName) {
    // Données factices en attendant le vrai calcul de Jesse (isGraduated + tri par moyenne)
    var data =
        new DiplomaList(
            promotionName,
            List.of(
                new Graduate(1, "STD24905", "Rabe", "Lucas", 15.8),
                new Graduate(2, "STD24901", "Rakoto", "Jean", 14.2),
                new Graduate(3, "STD24908", "Ratsimba", "Léa", 13.5)));

    var file = diplomaService.generateAndArchive(data);

    return ResponseEntity.ok()
        .contentType(
            MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=diplomes-" + promotionName + ".xlsx")
        .body(new FileSystemResource(file));
  }
}
