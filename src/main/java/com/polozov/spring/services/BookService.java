package com.polozov.spring.services;

import com.polozov.spring.models.Book;
import com.polozov.spring.models.Person;
import com.polozov.spring.repositories.BookRepository;
import com.polozov.spring.repositories.PeopleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> index() {
        return bookRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book bookFromDB = bookOptional.orElse(null);
        bookFromDB.setName(book.getName());
        bookFromDB.setAuthor(book.getAuthor());
        bookFromDB.setYear(book.getYear());
    }

    public Book show(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        assert book != null;
        if(book.getPerson()!=null&&LocalDate.now().minusMonths(1).isAfter(book.getTakingTime())){
            book.setOverdue(true);
        }
        return book;
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void add(int id, Person person) {
        Person personFromDB = peopleRepository.findById(person.getId()).orElse(null);
        Book book = bookRepository.findById(id).orElse(null);
        book.setTakingTime(LocalDate.now());
        book.setPerson(personFromDB);
        bookRepository.save(book);
    }

    @Transactional
    public void deleteOwner(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        assert book != null;
        book.setPerson(null);
        book.setTakingTime(null);
        bookRepository.save(book);
    }

    public List<Book> search(String name) {
        return bookRepository.findByNameContainingIgnoreCase(name);
    }
}
