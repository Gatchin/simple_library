package com.polozov.spring.controllers;

import com.polozov.spring.models.Book;
import com.polozov.spring.models.Person;
import com.polozov.spring.services.BookService;
import com.polozov.spring.services.PeopleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final PeopleService peopleService;

    public BooksController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }


    @GetMapping
    public String index(Model model) {
        model.addAttribute("listOfBooks", bookService.index());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String save(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/books";
    }


    @GetMapping("/search")
    public String search (@RequestParam(value = "name",required = false) String name, Model model){
        model.addAttribute("foundBooks",bookService.search(name));
        return "books/search";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book,
                         @PathVariable("id") int id) {
        bookService.update(id, book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.show(id));
        model.addAttribute("people", peopleService.index());
        return "books/show";
    }

    @PatchMapping("/{id}/add")
    public String addOwner(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        bookService.add(id, person);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/delete")
    public String deleteOwner(@PathVariable("id") int id) {
        bookService.deleteOwner(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.show(id));
        return "books/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";

    }
}

