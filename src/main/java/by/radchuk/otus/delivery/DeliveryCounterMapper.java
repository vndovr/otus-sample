package by.radchuk.otus.delivery;

import javax.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;

/**
 * Mapper for entity to dto mappings
 * 
 * @author Vladimir Radchuk
 *
 */
@Mapper
@ApplicationScoped
public interface DeliveryCounterMapper {

  DeliveryCounterDto asDeliveryCounterDto(DeliveryCounter deliveryCounter);
}
