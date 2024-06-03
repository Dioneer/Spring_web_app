package pegas.mapper;

import lombok.Value;
import org.springframework.stereotype.Component;
import pegas.dto.userdto.CreateBuyDTO;
import pegas.entity.BuyProduct;

@Value
@Component
public class CreateBuyMapper implements Mapper<CreateBuyDTO, BuyProduct>{
    @Override
    public BuyProduct map(CreateBuyDTO create) {
        return BuyProduct.builder()
                .productId(create.getProductId())
                .userId(create.getUserId())
                .productMark(create.getProductMark())
                .productModel(create.getProductModel())
                .price(create.getPrice())
                .amount(create.getAmount())
                .build();
    }
}
