package by.radchuk.otus.order;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import io.quarkus.panache.common.Sort;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Transactional
@Slf4j
class OrderService {

  @Inject
  OrderMapper orderMapper;

  List<OrderOverviewDto> getOrders(String userId, Order.State state, Sort sort) {

    List<OrderOverview> result;

    if (userId == null && state == null) {
      result = OrderOverview.listAll(sort);
    } else if (userId == null) {
      result = OrderOverview.list("state", sort, state);
    } else if (state == null) {
      result = OrderOverview.list("userId", sort, userId);
    } else
      result = OrderOverview.list("state = ?1 and userId = ?2", sort, state, userId);
    return result.stream().map(orderMapper::asOrderOverviewDto).collect(Collectors.toList());
  }

  OrderDto getOrder(String id, String userId) {
    Order order = Order.findById(id);
    log.info("Order loaded: {}", order);
    return order != null && (userId == null || userId.equals(order.getUserId()))
        ? orderMapper.asOrderDto(order)
        : null;
  }
}
