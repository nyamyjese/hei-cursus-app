package com.hei.school.endpoint.rest.controller;

import com.hei.school.model.Student;
import com.hei.school.repository.PromotionRepository;
import com.hei.school.repository.StudentRepository;
import com.hei.school.service.diploma.*;
import com.hei.school.service.diploma.DiplomaList.Graduate;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class DiplomaController {

  private final DiplomaService diplomaService;
  private final PromotionRepository promotionRepository;
  private final StudentRepository studentRepository;
  private final StudentService studentService;

  @GetMapping("/promotions/{promotionRef}/diplomes")
  public ResponseEntity<FileSystemResource> downloadDiplomaList(@PathVariable String promotionRef) {
    var promotion =
        promotionRepository
            .findByRef(promotionRef)
            .orElseThrow(
                () -> new IllegalArgumentException("Promotion not found: " + promotionRef));

    List<Student> students = studentRepository.findCurrentlyInPromotion(promotion.getId());

    var graduates =
        students.stream()
            .filter(s -> studentService.isGraduated(s.getId(), s.getTrack().getId()))
            .map(
                s ->
                    new Graduate(
                        0,
                        s.getStd(),
                        s.getUser().getLastName(),
                        s.getUser().getFirstName(),
                        studentService
                            .calculateGeneralAverage(s.getId(), s.getTrack().getId())
                            .doubleValue()))
            .sorted(Comparator.comparingDouble(Graduate::generalAverage).reversed())
            .toList();

    var ranked =
        java.util.stream.IntStream.range(0, graduates.size())
            .mapToObj(
                i ->
                    new Graduate(
                        i + 1,
                        graduates.get(i).studentId(),
                        graduates.get(i).lastName(),
                        graduates.get(i).firstName(),
                        graduates.get(i).generalAverage()))
            .toList();

    var data = new DiplomaList(promotionRef, ranked);
    var file = diplomaService.generateAndArchive(data);

    return ResponseEntity.ok()
        .contentType(
            MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=diplomes-" + promotionRef + ".xlsx")
        .body(new FileSystemResource(file));
  }
}
