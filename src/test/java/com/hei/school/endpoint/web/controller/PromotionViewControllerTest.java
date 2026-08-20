package com.hei.school.endpoint.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hei.school.repository.PromotionRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
class PromotionViewControllerTest {

  @Mock PromotionRepository promotionRepository;
  @Mock Model model;

  @InjectMocks PromotionViewController controller;

  @Test
  void listPromotions_adds_to_model_and_returns_view_name() {

    when(promotionRepository.findAll()).thenReturn(List.of());

    String viewName = controller.listPromotions(model);

    assertEquals("promotions", viewName);

    verify(promotionRepository).findAll();
    verify(model).addAttribute("promotions", List.of());
  }
}
