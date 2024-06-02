package pegas.mapper;

import org.springframework.stereotype.Component;
import pegas.dto.SendDTO;
import pegas.entity.Product;

import java.math.BigDecimal;

@Component
public class CreateEditProductMapper implements Mapper<SendDTO, Product>{
    @Override
    public Product map(SendDTO create) {
        Product product = new Product();
        product.setProductModel(create.getProductModel());
        product.setProductMark(create.getProductMark());
        product.setPrice(new BigDecimal(create.getPrice()));
        product.setAmount(create.getAmount());
        product.setReserved(create.getReserved()==null ? 0 : create.getReserved());
        product.setProductImage(create.getProductImage());
        return product;
    }

    @Override
    public Product map(SendDTO update, Product product) {
        product.setProductModel(update.getProductModel());
        product.setProductMark(update.getProductMark());
        product.setPrice(new BigDecimal(update.getPrice()));
        product.setAmount(update.getAmount());
        product.setReserved(update.getReserved()==null ? 0 : update.getReserved());
        product.setProductImage(update.getProductImage());
        return product;
    }
}
