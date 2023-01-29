package com.polozov.spring.dao;

import com.polozov.spring.models.Book;
import com.polozov.spring.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional(readOnly = true)
    public List<Book> index(){
        Session session= sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b", Book.class).getResultList();

    }
@Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }
@Transactional
    public void update(int id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        Book bookFromDB = session.get(Book.class,id);
        bookFromDB.setName(book.getName());
        bookFromDB.setAuthor(book.getAuthor());
        bookFromDB.setYear(book.getYear());
    }
    @Transactional
    public Book show(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class,id);
    }
    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Book book=session.get(Book.class,id);
        session.delete(book);
    }
@Transactional
    public void add(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person personFromDB = session.get(Person.class, person.getId());
        Book book = session.get(Book.class,id);
        book.setPerson(personFromDB);
    }
@Transactional
    public void deleteOwner(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class,id);
        book.setPerson(null);
    }
}
