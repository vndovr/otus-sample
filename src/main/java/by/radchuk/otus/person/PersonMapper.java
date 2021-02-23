package by.radchuk.otus.person;

import javax.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for entity to dto mappings
 * 
 * @author Vladimir Radchuk
 *
 */
@Mapper
@ApplicationScoped
public interface PersonMapper {

  PersonDto asPersonDto(Person person);

  PersonDtoWithId asPersonDtoWithId(Person person);

  void asPerson(@MappingTarget Person person, PersonDtoWithId dto);

  Person asPerson(PersonDto dto);
}
