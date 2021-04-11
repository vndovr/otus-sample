package by.radchuk.otus.order;

import lombok.Data;

@Data
public class EventDto {
  String xReqId;

  String externalId;

  String entity;

  String type;

  String data;

  String userId;
}
