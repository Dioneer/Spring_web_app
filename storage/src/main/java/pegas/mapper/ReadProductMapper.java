package pegas.mapper;

import org.springframework.stereotype.Component;
import pegas.dto.ReadProductDTO;
import pegas.entity.Product;

@Component
public class ReadProductMapper implements Mapper<Product, ReadProductDTO>{
    @Override
    public ReadProductDTO map(Product from) {
        return new ReadProductDTO(from.getId(), from.getProductMark(), from.getProductModel(),
                from.getPrice().toString(), from.getAmount(),from.getReserved(), from.getProductImage());
    }
}
