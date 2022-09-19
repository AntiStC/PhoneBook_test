package ru.spb.sspk.ssdmd.phonebook.model.mapper;

import org.mapstruct.Mapper;
import ru.spb.sspk.ssdmd.phonebook.model.dto.PersonDto;
import ru.spb.sspk.ssdmd.phonebook.model.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto toDto(Person person);

    Person toEntity(PersonDto personDto);
}
