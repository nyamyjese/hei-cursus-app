package com.hei.school.endpoint.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hei.school.model.Promotion;
import com.hei.school.model.Student;
import com.hei.school.model.Track;
import com.hei.school.model.User;
import com.hei.school.repository.PromotionRepository;
import com.hei.school.repository.StudentRepository;
import com.hei.school.service.diploma.DiplomaService;
import com.hei.school.service.diploma.StudentService;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DiplomaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DiplomaControllerIT {

  @Autowired MockMvc mockMvc;

  @MockBean DiplomaService diplomaService;
  @MockBean StudentService studentService;
  @MockBean PromotionRepository promotionRepository;
  @MockBean StudentRepository studentRepository;

  @Test
  void diploma_controller_downloadDiplomaList_is_ok() throws Exception {

    Promotion p = new Promotion();
    p.setId(UUID.randomUUID());
    p.setRef("PROMO_2024");
    when(promotionRepository.findByRef("PROMO_2024")).thenReturn(Optional.of(p));

    User u = new User();
    u.setId(UUID.randomUUID());
    u.setFirstName("Jese");
    u.setLastName("Pinkman");

    Track t = new Track();
    t.setId(UUID.randomUUID());

    Student s = new Student();
    s.setId(UUID.randomUUID());
    s.setStd("STD_TEST");
    s.setUser(u);
    s.setTrack(t);

    when(studentRepository.findCurrentlyInPromotion(p.getId())).thenReturn(List.of(s));
    when(studentService.isGraduated(s.getId(), t.getId())).thenReturn(true);
    when(studentService.calculateGeneralAverage(s.getId(), t.getId()))
        .thenReturn(new BigDecimal("14.50"));

    File tempExcelFile = File.createTempFile("test-diplomes", ".xlsx");
    tempExcelFile.deleteOnExit();
    when(diplomaService.generateAndArchive(any())).thenReturn(tempExcelFile);

    mockMvc.perform(get("/promotions/PROMO_2024/diplomes")).andExpect(status().isOk());
  }
}
