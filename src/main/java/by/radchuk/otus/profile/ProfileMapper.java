package by.radchuk.otus.profile;

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
public interface ProfileMapper {

  ProfileDto asProfileDto(Profile user);

  Profile asProfile(ProfileDto userDto);

  void asProfile(@MappingTarget Profile profile, ProfileDto dto);
}
