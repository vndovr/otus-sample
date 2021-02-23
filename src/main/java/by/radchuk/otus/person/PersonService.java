package by.radchuk.otus.person;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import by.radchuk.otus.system.jaxrs.ObjectNotFoundException;
import io.quarkus.hibernate.orm.panache.Panache;

@ApplicationScoped
@Transactional
public class PersonService {

  @Inject
  PersonMapper personMapper;

  List<PersonDtoWithId> getPersons() {
    List<Person> persons = Person.findAll().list();
    return persons.stream().map(personMapper::asPersonDtoWithId).collect(Collectors.toList());
  }

  PersonDtoWithId getPerson(long id) {
    Person person = Person.findById(id);
    if (Objects.isNull(person)) {
      throw new ObjectNotFoundException();
    }
    return personMapper.asPersonDtoWithId(person);
  }

  PersonDtoWithId createPerson(PersonDto dto) {
    Person person = personMapper.asPerson(dto);
    Person.persist(person);
    Panache.getEntityManager().flush();
    return personMapper.asPersonDtoWithId(person);
  }

  PersonDtoWithId updatePerson(PersonDtoWithId dto) {
    Person person = Person.findById(dto.getId());
    if (Objects.isNull(person)) {
      throw new ObjectNotFoundException();
    }
    personMapper.asPerson(person, dto);
    Person.persist(person);
    Panache.getEntityManager().flush();
    return personMapper.asPersonDtoWithId(person);
  }

  void deletePerson(long id) {
    if (!Person.deleteById(id)) {
      throw new ObjectNotFoundException();
    }
  }
}
