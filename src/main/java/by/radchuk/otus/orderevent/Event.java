package by.radchuk.otus.orderevent;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "event")
@EqualsAndHashCode(callSuper = true)
public class Event extends PanacheEntity {

  String xReqId;

  String externalId;
  
  String entity;

  String type;

  String data;

  String userId;

  LocalDateTime creationDate;
}
