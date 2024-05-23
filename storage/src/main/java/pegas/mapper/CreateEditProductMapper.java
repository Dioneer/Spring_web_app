package pegas.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pegas.dto.CreateEditProductDTO;
import pegas.entity.Product;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class CreateEditProductMapper implements Mapper<CreateEditProductDTO, Product>{
    @Override
    public Product map(CreateEditProductDTO create) {
        Product product = new Product();
        product.setProductModel(create.getProductModel());
        product.setProductMark(create.getProductMark());
        product.setPrice(new BigDecimal(create.getPrice()));
        product.setAmount(create.getAmount());
        product.setReserved(create.getReserved()==null ? 0 : create.getReserved());
        Optional.ofNullable(create.getProductImage()).filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(i->product.setProductImage(i.getOriginalFilename()));
        return product;
    }

    @Override
    public Product map(CreateEditProductDTO update, Product product) {
        product.setProductModel(update.getProductModel());
        product.setProductMark(update.getProductMark());
        product.setPrice(new BigDecimal(update.getPrice()));
        product.setAmount(update.getAmount());
        product.setReserved(update.getReserved()==null ? 0 : update.getReserved());
        Optional.ofNullable(update.getProductImage()).filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(i->product.setProductImage(i.getOriginalFilename()));
        return product;
    }
}
