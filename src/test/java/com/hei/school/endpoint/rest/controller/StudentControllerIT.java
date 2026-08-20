package com.hei.school.endpoint.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hei.school.service.diploma.StudentService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerIT {

  @Autowired MockMvc mockMvc;

  @MockBean StudentService studentService;

  @Test
  void student_controller_graduation_status_is_ok() throws Exception {
    when(studentService.isGraduated(any(), any())).thenReturn(true);

    mockMvc
        .perform(
            get("/students/" + UUID.randomUUID() + "/graduation-status")
                .param("trackId", UUID.randomUUID().toString()))
        .andExpect(status().isOk());
  }

  @Test
  void student_controller_year_validation_is_ok() throws Exception {
    when(studentService.isYearValidated(any(), any(), any(Integer.class))).thenReturn(true);

    mockMvc
        .perform(
            get("/students/" + UUID.randomUUID() + "/year-validation")
                .param("trackId", UUID.randomUUID().toString())
                .param("academicYear", "1"))
        .andExpect(status().isOk());
  }
}
