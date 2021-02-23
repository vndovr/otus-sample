package by.radchuk.otus.system;

import org.apache.commons.lang3.StringUtils;
import lombok.Data;

@Data
public class UserPrincipal {
  String login;

  public boolean anonymouse() {
    return StringUtils.isBlank(login);
  }
}
