package by.radchuk.otus.profile;

import java.io.StringReader;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import by.radchuk.otus.system.Hostname;
import io.smallrye.reactive.messaging.annotations.Blocking;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@Transactional
public class UserEventListener {

  @Inject
  ProfileService profileService;

  @Hostname
  String hostname;

  @Incoming("user-events-in")
  @Blocking
  @SneakyThrows
  public void onMessage(String event) {
    if (!StringUtils.containsIgnoreCase(hostname, "profile"))
      return;

    log.info("Received an event: {}", event);

    JsonObject json = Json.createReader(new StringReader(event)).readObject();

    String userId = json.getString("userId");

    if (StringUtils.isBlank(userId)) {
      return;
    }
    if (StringUtils.equals(json.getString("operation"), "delete")) {
      profileService.deleteProfile(userId);
    }
  }
}
