package pegas.mapper;

import org.springframework.stereotype.Component;
import pegas.dto.CreateEditProductDTO;
import pegas.entity.Product;

import java.math.BigDecimal;

@Component
public class CreateEditProductMapper implements Mapper<CreateEditProductDTO, Product>{
    @Override
    public Product map(CreateEditProductDTO create) {
        return Product.builder()
                .productMark(create.getProductMark())
                .productModel(create.getProductModel())
                .price(new BigDecimal(create.getPrice()))
                .amount(create.getAmount())
                .reserved(create.getReserved()==null ? 0 : create.getReserved())
                .productImage(create.getProductImage())
                .build();
    }

    @Override
    public Product map(CreateEditProductDTO update, Product product) {
        product.setProductModel(update.getProductModel());
        product.setProductMark(update.getProductMark());
        product.setPrice(new BigDecimal(update.getPrice()));
        product.setAmount(update.getAmount());
        product.setReserved(update.getReserved()==null ? 0 : update.getReserved());
        product.setProductImage(update.getProductImage());
        return product;
    }
}
