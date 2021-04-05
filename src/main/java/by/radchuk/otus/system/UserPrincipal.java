package by.radchuk.otus.system;

import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import lombok.Data;

@Data
public class UserPrincipal {

  String login;

  Set<String> roles;

  public boolean anonymouse() {
    return StringUtils.isBlank(login);
  }

  public boolean hasRole(String role) {
    return roles.contains(role);
  }
}
