package com.polozov.spring.services;

import com.polozov.spring.models.Person;
import com.polozov.spring.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> index() {
        return peopleRepository.findAll();
    }

    public Person show(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        Person foundPerson = person.orElse(null);
        assert foundPerson != null;
        Hibernate.initialize(foundPerson.getBooks());
        return foundPerson;
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        Person personFromDB = optionalPerson.orElse(null);
        personFromDB.setName(person.getName());
        personFromDB.setAge(person.getAge());
        peopleRepository.save(personFromDB);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
