package by.radchuk.otus.order;

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
public interface OrderMapper {

  OrderDto asOrderDto(Order order);

  OrderOverviewDto asOrderOverviewDto(OrderOverview orderOverview);
}
