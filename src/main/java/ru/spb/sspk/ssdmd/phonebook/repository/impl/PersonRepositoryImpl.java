package ru.spb.sspk.ssdmd.phonebook.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;
import ru.spb.sspk.ssdmd.phonebook.repository.PersonRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private JdbcTemplate jdbcTemplate;

    public PersonRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Person save(Person person) {
        jdbcTemplate.update(
                "insert into person (" +
                        "id, " +
                        "first_name, " +
                        "middle_name, " +
                        "last_name, " +
                        "department, " +
                        "phone) " +
                        "values (?, ?, ?, ?, ?, ?)",
                person.getId(),
                person.getFirstName(),
                person.getMiddleName(),
                person.getLastName(),
                person.getDepartment(),
                person.getPhone());

        return person;
    }

    @Override
    public Person update(Person person) {

        jdbcTemplate.update("update person " +
                        "set first_name = (?), " +
                        "middle_name = (?), " +
                        "last_name = (?), " +
                        "department = (?), " +
                        "phone = (?) " +
                        "where id = (?) " +
                        "returning id",
                person.getId(),
                person.getFirstName(),
                person.getMiddleName(),
                person.getLastName(),
                person.getDepartment(),
                person.getPhone());

        return person;
    }

    @Override
    public List<Person> findAll() {

        return jdbcTemplate.query(
                "select id, first_name, middle_name, last_name, department, phone " +
                        "from person",
                this::mapRowToPerson);
    }

    @Override
    public List<Person> findByAll(String answer) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate =
                new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("first_name", answer);
        paramMap.put("last_name", answer);
        paramMap.put("department", answer);

        List<Person> personList = namedParameterJdbcTemplate.query("select id, first_name, middle_name, last_name, department, phone " +
                        "from person where first_name = :first_name " +
                        "or last_name = :last_name " +
                        "or department = :department"
                , paramMap, this::mapRowToPerson).stream().collect(Collectors.toList());

        return personList;

    }


    @Override
    public void deleteById(Long id) {

        jdbcTemplate.update("delete from person where id = ?");
    }

    private Person mapRowToPerson(ResultSet row, int rowNum) throws SQLException {

        return new Person.Builder().
                setId(row.getLong(1)).
                setFirstName(row.getString(2)).
                setMiddleName(row.getString(3)).
                setLastName(row.getString(4)).
                setDepartment(row.getString(5)).
                setPhone(row.getInt(6)).
                build();

    }
}
