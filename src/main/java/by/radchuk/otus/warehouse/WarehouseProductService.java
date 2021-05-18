package by.radchuk.otus.warehouse;

import java.util.Arrays;
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
public class WarehouseProductService {

  @Inject
  WarehouseProductMapper warehouseProductMapper;

  /**
   * Returns list of products is warehouse
   * 
   * @return
   */
  public List<WarehouseProductDto> getAll() {
    List<WarehouseProduct> result = WarehouseProduct.listAll();
    return result.stream().map(obj -> warehouseProductMapper.asWarehouseProductDto(obj))
        .collect(Collectors.toList());
  }

  /**
   * Reserves the product in the warehouse for shipment
   * 
   * @param itemId
   * @param quantity
   */
  public void reserve(String xReqId, ReservationDto[] dto) {
    if (StringUtils.isBlank(xReqId)) {
      throw new IllegalStateException();
    }
    if (!Optional.ofNullable((WarehouseEvent) WarehouseEvent.findById(xReqId)).isPresent()) {
      Arrays.stream(dto).forEach(x -> {
        WarehouseProduct warehouseProduct =
            Optional.ofNullable((WarehouseProduct) WarehouseProduct.findById(x.getItemId()))
                .orElseThrow(ObjectNotFoundException::new);
        warehouseProduct.setReserved(warehouseProduct.getReserved() + x.getQuantity());
        if (warehouseProduct.getReserved() > warehouseProduct.getAvailable()
            || warehouseProduct.getReserved() < 0 || x.getQuantity() <= 0) {
          throw new IllegalStateException();
        } else {
          warehouseProduct.persist();
        }
      });
      WarehouseEvent warehouseEvent = new WarehouseEvent();
      warehouseEvent.setId(xReqId);
      WarehouseProduct.persist(warehouseEvent);
    }
  }

  /**
   * Releases the products for the shipment
   * 
   * @param itemId
   * @param quantity
   */
  public void release(String xReqId, ReservationDto[] dto) {
    Optional.ofNullable((WarehouseEvent) WarehouseEvent.findById(xReqId))
        .ifPresent(warehouseEvent -> {
          Arrays.stream(dto).forEach(x -> {
            WarehouseProduct warehouseProduct =
                Optional.ofNullable((WarehouseProduct) WarehouseProduct.findById(x.getItemId()))
                    .orElseThrow(ObjectNotFoundException::new);
            warehouseProduct.setReserved(warehouseProduct.getReserved() - x.getQuantity());
            if (warehouseProduct.getReserved() < 0 || warehouseProduct.getAvailable() < 0) {
              throw new IllegalStateException();
            } else {
              warehouseProduct.persist();
            }
          });
          warehouseEvent.delete();
        });
  }
}
