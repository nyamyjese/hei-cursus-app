package com.hei.school.endpoint.web.controller;

import com.hei.school.repository.PromotionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class PromotionViewController {
  private final PromotionRepository promotionRepository;

  @GetMapping("/promotions")
  public String listPromotions(Model model) {
    model.addAttribute("promotions", promotionRepository.findAll());
    return "promotions";
  }
}
