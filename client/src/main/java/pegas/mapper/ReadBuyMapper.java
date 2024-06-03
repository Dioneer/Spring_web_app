package pegas.mapper;

import lombok.Value;
import org.springframework.stereotype.Component;
import pegas.dto.userdto.ReadBuyDTO;
import pegas.entity.BuyProduct;

@Value
@Component
public class ReadBuyMapper implements Mapper<BuyProduct, ReadBuyDTO>{
    @Override
    public ReadBuyDTO map(BuyProduct buy) {
        return new ReadBuyDTO(buy.getId(), buy.getProductId(), buy.getProductMark(),buy.getProductModel(),
                buy.getPrice(),buy.getAmount());
    }
}
