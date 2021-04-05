package by.radchuk.otus.orderevent;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EventDto {
  String xReqId;
  
  String externalId;

  String entity;

  String type;

  String data;

  String userId;

  LocalDateTime creationDate;
}
