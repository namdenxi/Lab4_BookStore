package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/Category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public String Home(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/index";
    }
}
