package by.radchuk.otus.delivery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import by.radchuk.otus.system.exception.ObjectNotFoundException;
import io.quarkus.panache.common.Sort;

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
    List<DeliveryCounter> result = DeliveryCounter.listAll(Sort.by("id"));
    return result.stream().map(obj -> deliveryCounterMapper.asDeliveryCounterDto(obj))
        .collect(Collectors.toList());
  }

  /**
   * Reserves the time slot for delivery
   * 
   * @param date
   * @param xReqId
   */
  public void reserve(LocalDateTime date, String xReqId) {
    if (!Optional.ofNullable((DeliveryEvent) DeliveryEvent.findById(xReqId)).isPresent()) {

      DeliveryCounter deliveryCounter =
          Optional.ofNullable((DeliveryCounter) DeliveryCounter.findById(date))
              .orElseGet(() -> new DeliveryCounter(date, 0, 0));
      deliveryCounter.setCounter(deliveryCounter.getCounter() + 1);
      if (deliveryCounter.getCounter() > 1 || StringUtils.isBlank(xReqId)) {
        throw new IllegalStateException();
      } else {
        deliveryCounter.persist();
        DeliveryEvent deliveryEvent = new DeliveryEvent();
        deliveryEvent.setId(xReqId);
        deliveryEvent.persist();
      }
    }
  }

  /**
   * Releases the time slot for delivery
   * 
   * @param date
   * @param xReqId
   */
  public void release(LocalDateTime date, String xReqId) {
    Optional.ofNullable((DeliveryEvent) DeliveryEvent.findById(xReqId)).ifPresent(deliveryEvent -> {
      DeliveryCounter deliveryCounter =
          Optional.ofNullable((DeliveryCounter) DeliveryCounter.findById(date))
              .orElseThrow(ObjectNotFoundException::new);
      deliveryCounter.setCounter(deliveryCounter.getCounter() - 1);
      if (deliveryCounter.getCounter() < 0) {
        throw new IllegalStateException();
      } else {
        if (deliveryCounter.getCounter() == 0)
          deliveryCounter.delete();
        else
          deliveryCounter.persist();
        deliveryEvent.delete();
      }
    });
  }
}
