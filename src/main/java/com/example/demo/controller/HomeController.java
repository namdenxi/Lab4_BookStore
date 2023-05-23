package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public String Home(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "home/index";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("title", "Add Book");
        model.addAttribute("categories", categoryService.getAllCategories());
        return "home/add";
    }

    @PostMapping("/add")
    public String postBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult result,
                           Model model,
                           @RequestParam("cover") MultipartFile file) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("title", "Add Book");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "home/add";
        } else {
            if (!file.isEmpty()) {
                File saveFile = new ClassPathResource("static/images").getFile();
                String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
                Files.copy(file.getInputStream(), path);
                book.setCover(fileName);
                bookService.add(book);
            }
            return "redirect:/";
        }
    }

    @GetMapping("edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
            model.addAttribute("book", bookService.getBookById(id));
            model.addAttribute("title", "Edit Book");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "home/edit";
    }
    @PostMapping("edit")
    public String updateBook(@Valid @ModelAttribute("book") Book book,
                             BindingResult result,
                             Model model,
                             @RequestParam("cover") MultipartFile file) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("title", "Edit Book");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "home/edit";
        } else {
            if (!file.isEmpty()) {
                File saveFile = new ClassPathResource("static/images").getFile();
                String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
                Files.copy(file.getInputStream(), path);
                book.setCover(fileName);
                bookService.update(book);
            }
            return "redirect:/";
        }
    }

    @RequestMapping("delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.delete(id);
        return "redirect:/";
    }
}
