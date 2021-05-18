package by.radchuk.otus.billing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import lombok.Data;

@Data
@ApplicationScoped
public class BillingStateMachineContext {
  
  @Inject
  Jsonb jsonb;

  @Inject
  @RestClient
  AccountClient accountClient;

  @Inject
  @RestClient
  DeliveryClient deliveryClient;

  @Inject
  @RestClient
  WarehouseClient warehouseClient;

  @Inject
  NotificationClient notificationClint;

}
