package by.radchuk.otus.system;

import java.util.Arrays;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.ws.rs.core.Cookie;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class UserPrincipalProducer {

  @Produces
  @RequestScoped
  public UserPrincipal profileMapper() {
    HttpRequest request = ResteasyProviderFactory.getInstance().getContextData(HttpRequest.class);
    UserPrincipal userPrincipal = new UserPrincipal();
    log.info("Cookies: {}", request.getHttpHeaders().getCookies());
    Cookie cookie = request.getHttpHeaders().getCookies().get("session");
    log.info("Session cookie: {}", cookie);
    userPrincipal.setLogin(StringUtils
        .defaultString(request.getHttpHeaders().getHeaderString("X-User"), "anonymouse"));
    log.info("User is: {}", userPrincipal.getLogin());
    userPrincipal.setRoles(Arrays
        .stream(StringUtils
            .defaultString(request.getHttpHeaders().getHeaderString("X-Roles"), "user").split(","))
        .collect(Collectors.toSet()));
    log.info("Roles are: {}", userPrincipal.getRoles());
    return userPrincipal;
  }
}
