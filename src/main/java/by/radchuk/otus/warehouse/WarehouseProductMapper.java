package by.radchuk.otus.warehouse;

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
public interface WarehouseProductMapper {

  WarehouseProductDto asWarehouseProductDto(WarehouseProduct warehouseProduct);
}
