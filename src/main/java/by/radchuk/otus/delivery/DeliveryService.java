package by.radchuk.otus.delivery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import by.radchuk.otus.system.exception.ObjectNotFoundException;

@ApplicationScoped
@Transactional
public class DeliveryService {

  @Inject
  DeliveryCounterMapper deliveryCounterMapper;

  /**
   * Returns list of deliveries
   * 
   * @return
   */
  public List<DeliveryCounterDto> getAll() {
    List<DeliveryCounter> result = DeliveryCounter.listAll();
    return result.stream().map(obj -> deliveryCounterMapper.asDeliveryCounterDto(obj))
        .collect(Collectors.toList());
  }

  /**
   * Reserves the time slot for delivery
   * 
   * @param date
   * @param xReqId
   */
  public void reserve(LocalDate date, String xReqId) {
    if (!Optional.ofNullable((DeliveryEvent) DeliveryEvent.findById(xReqId)).isPresent()) {

      DeliveryCounter deliveryCounter =
          Optional.ofNullable((DeliveryCounter) DeliveryCounter.findById(date))
              .orElseGet(() -> new DeliveryCounter(date, 0, 0));
      deliveryCounter.setCounter(deliveryCounter.getCounter() + 1);
      if (deliveryCounter.getCounter() > 10 || StringUtils.isBlank(xReqId)) {
        throw new IllegalStateException();
      } else {
        DeliveryEvent deliveryEvent = new DeliveryEvent();
        deliveryEvent.setId(xReqId);
        DeliveryCounter.persist(deliveryCounter, deliveryEvent);
      }
    }
  }

  /**
   * Releases the time slot for delivery
   * 
   * @param date
   * @param xReqId
   */
  public void release(LocalDate date, String xReqId) {
    Optional.ofNullable((DeliveryEvent) DeliveryEvent.findById(xReqId)).ifPresent(deliveryEvent -> {
      DeliveryCounter deliveryCounter =
          Optional.ofNullable((DeliveryCounter) DeliveryCounter.findById(date))
              .orElseThrow(ObjectNotFoundException::new);
      deliveryCounter.setCounter(deliveryCounter.getCounter() - 1);
      if (deliveryCounter.getCounter() < 0) {
        throw new IllegalStateException();
      } else {
        deliveryCounter.persist();
        deliveryEvent.delete();
      }
    });
  }
}
