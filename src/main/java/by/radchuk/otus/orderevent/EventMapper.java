package by.radchuk.otus.orderevent;

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
public interface EventMapper {

  OrderEventDto asOrderEventDto(Event event);

}
