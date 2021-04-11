package by.radchuk.otus.orderevent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.bind.Jsonb;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import by.radchuk.otus.system.UserPrincipal;
import by.radchuk.otus.system.exception.UpdateConflictException;
import by.radchuk.otus.system.jaxrs.RefDto;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Transactional
@Slf4j
class EventService {

  @Inject
  UserPrincipal userPrincipal;

  @Inject
  Jsonb jsonb;

  @Inject
  @Channel("order-events-out")
  Emitter<String> emitter;

  @Inject
  EventMapper eventMapper;

  RefDto createEvent(String externalId, String entity, EventHolder data, String xReqId) {
    Event event = null;
    log.info("createEvent() with eId {} and xReqID {}", externalId, xReqId);
    if (externalId != null && xReqId != null) {
      event = (Event) Event.find("xReqId = ?1 and externalId = ?2", xReqId, externalId)
          .singleResultOptional().orElse(null);
      log.info("Event: {}", event);
    }
    if (event == null) {
      event = new Event();
      event.setXReqId(xReqId);
      event.setCreationDate(LocalDateTime.now());
      event.setData(jsonb.toJson(data));
      event.setEntity(entity);
      event.setUserId(userPrincipal.getLogin());
      event.setExternalId(
          StringUtils.isEmpty(externalId) ? UUID.randomUUID().toString() : externalId);
      event.setType(data.typeid());
      Event.persist(event);
      emitter.send(
          Json.createObjectBuilder().add("xReqId", StringUtils.defaultString(event.getXReqId()))
              .add("externalId", event.getExternalId()).add("entity", event.getEntity())
              .add("type", event.getType()).add("data", event.getData())
              .add("userId", event.getUserId()).build().toString());
      return RefDto.valueOf(event.getExternalId());
    }
    throw new UpdateConflictException();
  }

  <T> List<T> getEvents(String id, Function<Event, T> convertor) {
    List<Event> result = Event.list("externalId", id);
    return result.stream().map(convertor).collect(Collectors.toList());
  }

}
