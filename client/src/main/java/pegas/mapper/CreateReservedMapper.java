package pegas.mapper;

import lombok.Value;
import org.springframework.stereotype.Component;
import pegas.dto.userdto.CreateReservedDTO;
import pegas.entity.ReserveProduct;

@Value
@Component
public class CreateReservedMapper implements Mapper<CreateReservedDTO, ReserveProduct>{

    @Override
    public ReserveProduct map(CreateReservedDTO create) {
        return ReserveProduct.builder()
                .productId(create.getProductId())
                .userId(create.getUserId())
                .productMark(create.getProductMark())
                .productModel(create.getProductModel())
                .price(create.getPrice())
                .amount(create.getAmount())
                .build();
    }
}
