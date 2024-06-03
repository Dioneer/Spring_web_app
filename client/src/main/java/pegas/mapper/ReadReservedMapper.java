package pegas.mapper;

import lombok.Value;
import org.springframework.stereotype.Component;
import pegas.dto.userdto.ReadReservedDTO;
import pegas.entity.ReserveProduct;

@Value
@Component
public class ReadReservedMapper implements Mapper<ReserveProduct, ReadReservedDTO> {
    @Override
    public ReadReservedDTO map(ReserveProduct reserve) {
        return new ReadReservedDTO(reserve.getId(), reserve.getProductId(), reserve.getProductMark()
                ,reserve.getProductModel(),reserve.getPrice(),reserve.getAmount());
    }
}
