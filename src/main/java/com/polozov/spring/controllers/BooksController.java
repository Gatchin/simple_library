package com.polozov.spring.controllers;

import com.polozov.spring.dao.BookDAO;
import com.polozov.spring.dao.PersonDAO;
import com.polozov.spring.models.Book;
import com.polozov.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {

    private BookDAO bookDAO;
    private PersonDAO personDAO;
    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("listOfBooks",bookDAO.index());
        return "books/index";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }
@PostMapping
    public String save(@ModelAttribute("book") Book book){
        bookDAO.save(book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book,
                         @PathVariable("id") int id){
        bookDAO.update(id,book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id,Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book",bookDAO.show(id));
        model.addAttribute("people",personDAO.index());
        return "books/show";
    }
    @PatchMapping("/{id}/add")
    public String addOwner(@ModelAttribute("person") Person person, @PathVariable("id")int id){
        bookDAO.add(id, person);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/delete")
    public String deleteOwner(@PathVariable("id")int id){
        bookDAO.deleteOwner(id);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";

    }
}

