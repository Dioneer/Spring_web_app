package pegas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pegas.dto.CreateEditProductDTO;
import pegas.dto.ProductFilter;
import pegas.dto.QPredicates;
import pegas.dto.ReadProductDTO;
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

    @Override
    public List<ReadProductDTO> findAll(){
        return repository.findAll().stream().map(read::map).toList();
    }

    @Override
    public List<ReadProductDTO> findAll(ProductFilter filter, Pageable pageable){
        var predicate = QPredicates.builder()
                .add(filter.productMark(), product.productMark::containsIgnoreCase)
                .add(filter.productModel(), product.productModel::containsIgnoreCase)
                .add(filter.price(), product.price::eq)
                .build();
        return repository.findAll(predicate, pageable).stream().map(read::map).toList();
    }

    @Override
    public Optional<ReadProductDTO> findById(Long id){
        return repository.findById(id).map(read::map);
    }

    @Override
    @Transactional
    public boolean deleteProduct(Long id){
        return repository.findById(id).map(i-> {
            repository.delete(i);
            repository.flush();
            return true;
        }).orElse(false);
    }

    @Override
    @Transactional
    public ReadProductDTO create(CreateEditProductDTO createEditProductDTO){
        return Optional.of(createEditProductDTO).map(createEdit::map).map(repository::save).map(read::map).orElseThrow();
    }

    @Override
    @Transactional
    public ReadProductDTO update(CreateEditProductDTO createEditProductDTO, Long id){
        return repository.findById(id).map(i->createEdit.map(createEditProductDTO, i))
                .map(repository::save).map(read::map).orElseThrow();
    }
}
