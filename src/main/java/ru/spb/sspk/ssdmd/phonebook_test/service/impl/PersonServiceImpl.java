package ru.spb.sspk.ssdmd.phonebook_test.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.jvnet.hk2.annotations.Service;
import org.springframework.stereotype.Component;
import ru.spb.sspk.ssdmd.phonebook_test.model.dto.PersonDto;
import ru.spb.sspk.ssdmd.phonebook_test.model.entity.Person;
import ru.spb.sspk.ssdmd.phonebook_test.model.mapper.PersonMapper;
import ru.spb.sspk.ssdmd.phonebook_test.repository.PersonRepository;
import ru.spb.sspk.ssdmd.phonebook_test.service.PersonService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Component
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }


    @Override
    public List<PersonDto> findPerson(String answer) {


        return null;
    }

    @Override
    public PersonDto save(String answer) {

        PersonDto person = null;

//        person = personMapper.toDto(personRepository.save(personMapper.toEntity(personDto)));

        return person;
    }

    @Override
    public PersonDto update(String answer) {

        PersonDto person = null;

//        person = personMapper.toDto(personRepository.update(personMapper.toEntity(personDto)));

        return person;
    }

    @Override
    public List<PersonDto> findAll() {

        List<PersonDto> personList = null;

        personList = personRepository.findAll().stream()
                .map(PersonMapper::toDto)
                .collect(Collectors.toList());

        return personList;
    }

    @Override
    public String findByAll(String answer) {

        List<PersonDto> personList = null;

        String firstName = StringUtils.capitalize(answer.toLowerCase());
        String lastName = StringUtils.capitalize(answer.toLowerCase());
        String department = answer.toUpperCase();
        Integer phone = null;
        if (StringUtils.isNumeric(answer)) {
            phone = Integer.valueOf(answer);
        }

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("first_name", firstName);
        paramMap.put("last_name", lastName);
        paramMap.put("department", department);
        paramMap.put("phone", phone);

        personList = personRepository.findByAll(paramMap).stream()
                .map(PersonMapper::toDto)
                .collect(Collectors.toList());

        return personList.toString().replace("[", "")
                .replace("]", "")
                .replace(",", ",\n\n");
    }

    @Override
    public String deleteById(Long id) {

        personRepository.deleteById(id);

        return "managed to complete";
    }
}
