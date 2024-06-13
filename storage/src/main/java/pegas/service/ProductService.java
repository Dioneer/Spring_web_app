package pegas.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pegas.dto.ProductFilter;
import pegas.dto.QPredicates;
import pegas.dto.ReadProductDTO;
import pegas.dto.SendDTO;
import pegas.entity.Product;
import pegas.mapper.CreateEditProductMapper;
import pegas.mapper.ReadProductMapper;
import pegas.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static pegas.entity.QProduct.product;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService implements CRUDService{
    private final ProductRepository repository;
    private final CreateEditProductMapper createEdit;
    private final ReadProductMapper read;
    private final ImageService imageService;

    /**
     * CRUD operation
     * @return List<ReadProductDTO>
     */
    @Override
    public List<ReadProductDTO> findAll(){
        return Optional.of(repository.findAll().stream().map(read::map).toList())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "no products in this shop"));
    }

    /**
     * find by filter
     * @param filter
     * @param pageable for pagination
     * @return List<ReadProductDTO>
     */
    @Override
    public List<ReadProductDTO> findAll(ProductFilter filter,Pageable pageable){
        var predicate = QPredicates.builder()
                .add(filter.productMark(), product.productMark::containsIgnoreCase)
                .add(filter.productModel(), product.productModel::containsIgnoreCase)
                .add(filter.price(), product.price::eq)
                .build();
        return Optional.of(repository.findAll(predicate,pageable).stream().map(read::map).toList())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "no products with custom filter"));
    }

    /**
     * CRUD find by id
     * @param id of product
     * @return Optional<ReadProductDTO>
     */
    @Override
    public Optional<ReadProductDTO> findById(Long id){
        return repository.findById(id).map(i-> read.map(i));
    }

    /**
     * CRUD delete
     * @param id of product
     * @return Boolean
     */
    @Override
    @Transactional
    public Boolean deleteProduct(Long id){
        return repository.findById(id).map(i-> {
            repository.delete(i);
            repository.flush();
            return Boolean.TRUE;
        }).orElse(Boolean.FALSE);
    }

    /**
     * CRUD crate
     * @param sendDTO from front
     * @return ReadProductDTO
     */
    @Override
    @Transactional
    public ReadProductDTO create(SendDTO sendDTO){
        return Optional.of(sendDTO)
                .map(createEdit::map).map(repository::save).map(read::map).orElseThrow(()->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "user was not created"));
    }

    /**
     * CRUD update
     * @param sendDTO from front
     * @param id of product
     * @return ReadProductDTO
     */
    @Override
    @Transactional
    public ReadProductDTO update(SendDTO sendDTO, Long id){
        return repository.findById(id).map(i-> createEdit.map(sendDTO, i)).map(repository::save)
                .map(read::map).orElseThrow(()->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "user was not update"));
    }

    /**
     * save image
     * @param productImage multipart from form
     */
    @SneakyThrows
    private void uploadImage(MultipartFile productImage) {
        if(!productImage.isEmpty()) {
            imageService.upload(productImage.getOriginalFilename(), productImage.getInputStream());
        }
    }

    /**
     * if product was sold
     * @param id of product
     * @param amount
     * @return ReadProductDTO
     */
    @Transactional
    public ReadProductDTO reduceAmount(@PathVariable Long id, Integer amount){
       return repository.findById(id).filter(i-> {
                   if(i.getAmount() >= amount){
                       return true;
                   }
                   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "we have only "+i.getAmount()+" items");
               }).map(i->{
                i.setAmount(i.getAmount()-amount);
                return repository.save(i);})
               .map(read::map)
               .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "not found user with id " +
                       id));
    }

    /**
     * for reservation product
     * @param id of product
     * @param amount
     * @return ReadProductDTO
     */
    @Transactional
    public ReadProductDTO reservation(@PathVariable Long id, Integer amount){
        return repository.findById(id).filter(i-> {
                    if(i.getAmount() >= amount){
                        return true;
                    }
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "we have only "+i.getAmount()+" items");
                }).map(i->{
                i.setAmount(i.getAmount()-amount);
                i.setReserved(amount+i.getReserved());
            return repository.save(i);}).map(read::map)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "not found user with id " +id));
    }

    /**
     * unreservation of product
     * @param id of product
     * @param amount
     * @return
     */
    @Transactional
    public ReadProductDTO deReservation(@PathVariable Long id, Integer amount){
        return repository.findById(id).filter(i-> {
            if(i.getReserved() >= amount){
                return true;
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "we have only "+i.getAmount()+" items");
        }).map(i->{
                i.setAmount(i.getAmount()+amount);
                i.setReserved(i.getReserved()-amount);
                return repository.save(i);
        }).map(read::map).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "not found product with id "
                +id));
    }

    /**
     * get image of product
     * @param id of product
     * @return
     */
    @SneakyThrows
    public Optional<byte[]> findImage(Long id) {
        return repository.findById(id).map(Product::getProductImage).filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
