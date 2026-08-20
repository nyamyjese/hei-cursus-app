package com.hei.school.endpoint.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hei.school.conf.TestcontainersInitializer;
import com.hei.school.model.Grade;
import com.hei.school.service.diploma.GradeService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(initializers = TestcontainersInitializer.class)
public class GradeControllerIT {

    @Autowired MockMvc mockMvc;
    @MockBean GradeService gradeService;

    @Test
    void grade_controller_updateGrade_is_ok() throws Exception {
        when(gradeService.updateGrade(any(), any(), any(), any())).thenReturn(new Grade());

        mockMvc.perform(put("/courses/" + UUID.randomUUID() + "/grades/" + UUID.randomUUID())
                        .param("newValue", "15.00")
                        .param("reason", "reclamation")
                        .param("teacherId", UUID.randomUUID().toString()))
                .andExpect(status().isOk());
    }
}