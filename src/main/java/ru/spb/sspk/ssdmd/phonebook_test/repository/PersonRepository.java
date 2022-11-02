package ru.spb.sspk.ssdmd.phonebook_test.repository;

import org.springframework.stereotype.Repository;
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.Person;

import java.util.List;
import java.util.Map;

@Repository
public interface PersonRepository {


    Person save(Person person);

    Person update(Person person);

    List<Person> findAll();

    List<Person> findByAll(Map<String, Object> paramMap);

    void deleteById(Long id);
}
