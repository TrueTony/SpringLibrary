package org.example.controllers;

import org.example.dao.BookDao;
import org.example.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDao bookDao;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookDao bookDao, BookValidator bookValidator) {
        this.bookDao = bookDao;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }
}
