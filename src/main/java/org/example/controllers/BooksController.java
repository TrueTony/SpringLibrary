package org.example.controllers;

import org.example.dao.BookDao;
import org.example.dao.PersonDao;
import org.example.models.Book;
import org.example.models.Person;
import org.example.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDao bookDao;
    private final BooksService booksService;
    private final PersonDao personDao;

    @Autowired
    public BooksController(BookDao bookDao, BooksService booksService, PersonDao personDao) {
        this.bookDao = bookDao;
        this.booksService = booksService;
        this.personDao = personDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") int id, Model model,
                          @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findOne(id));

        Optional<Person> owner = bookDao.owner(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", personDao.index());
        }
        return "books/profile";
    }

    @PatchMapping("/{id}/release")
    public String deleteOwner(@PathVariable("id") int id) {
        bookDao.deleteOwner(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/setOwner")
    public String setOwner(@PathVariable("id") int id,
                           @ModelAttribute("person") Person person) {
        bookDao.setOwner(id, person);
        return "redirect:/books/" + id;
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
}
