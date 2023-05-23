package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/BMI")
public class BMIController {
    @GetMapping
    public String Home(Model model) {
        return "BMI/index";
    }
}
