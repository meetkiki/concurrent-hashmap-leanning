package com.meetkiki.conrrent.deepcopy;

import com.meetkiki.conrrent.deepcopy.Person.Child;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class PersonMapper {

    public static PersonMapper MAPPER = Mappers.getMapper(PersonMapper.class);

    public abstract List<Person> copy(List<Person> personList);

    public abstract Person toSelf(Person person);

    public abstract List<Child> copyChild(List<Child> personList);

    public abstract Child toSelf(Child child);

}
