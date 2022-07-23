package org.example.controllers;

import org.example.dao.BookDao;
import org.example.models.Book;
import org.example.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.profile(id));
        return "books/profile";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book,
                         BindingResult bindingResult) {
        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.profile(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book,
                         BindingResult bindingResult, BookValidator bookValidator) {
        bookDao.update(id, book);
        return "redirect:/books";
    }
}
