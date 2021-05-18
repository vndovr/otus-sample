package by.radchuk.otus.profile;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import by.radchuk.otus.system.exception.ObjectNotFoundException;
import io.quarkus.hibernate.orm.panache.Panache;

@ApplicationScoped
@Transactional
public class ProfileService {

  @Inject
  ProfileMapper profileMapper;

  /**
   * Retrieves the profile for user
   * 
   * @param id
   * @return
   */
  ProfileDto getProfile(String id) {
    Profile user = Profile.findById(id);
    return profileMapper
        .asProfileDto(Optional.ofNullable(user).orElseThrow(ObjectNotFoundException::new));
  }

  /**
   * Updates the profile for user
   * 
   * @param dto
   * @return
   */
  ProfileDto updateProfile(ProfileDto dto) {
    Profile profile = Optional.ofNullable((Profile) Profile.findById(dto.getUserId())).map(obj -> {
      profileMapper.asProfile(obj, dto);
      return obj;
    }).orElseGet(() -> profileMapper.asProfile(dto));
    Profile.persist(profile);
    Panache.getEntityManager().flush();
    return profileMapper.asProfileDto(profile);
  }

  /**
   * Updates the profile for user
   * 
   * @param dto
   * @return
   */
  void deleteProfile(String userid) {
    Profile.deleteById(userid);
  }

}
