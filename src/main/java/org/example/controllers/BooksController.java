package org.example.controllers;

import org.example.models.Book;
import org.example.models.Person;
import org.example.services.BooksService;
import org.example.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int books_per_page,
            @RequestParam(defaultValue = "false") String sort_by_year,
            Model model) {

        List<Book> booksByPag = booksService.findAllWithPage(page, books_per_page, sort_by_year);
        if (booksByPag.isEmpty()) {
        model.addAttribute("books", booksService.findAll(sort_by_year));
        } else {
            model.addAttribute("books", booksByPag);
        }
        return "books/index";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") int id, Model model,
                          @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findOne(id));

        Optional<Person> owner = booksService.findOwnerById(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/profile";
    }

    @PatchMapping("/{id}/release")
    public String deleteOwner(@PathVariable("id") int id) {
        booksService.deleteOwner(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/setOwner")
    public String setOwner(@PathVariable("id") int id,
                           @ModelAttribute("person") Person person) {
        booksService.setOwner(id, person);
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

    @GetMapping("/search")
    public String searchBook() {
        return "books/search";
    }

    @PostMapping("/search")
    public String searchedBook(@RequestParam(value = "title") String title,
                               Model model) {
        if (title.length() > 0) {
            model.addAttribute("books", booksService.findByName(title));
        }
        return "books/search";
    }
}
