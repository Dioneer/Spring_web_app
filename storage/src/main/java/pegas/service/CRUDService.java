package pegas.service;

import org.springframework.data.domain.Pageable;
import pegas.dto.CreateEditProductDTO;
import pegas.dto.ProductFilter;
import pegas.dto.ReadProductDTO;

import java.util.List;
import java.util.Optional;

public interface CRUDService {
    List<ReadProductDTO> findAll();
    List<ReadProductDTO> findAll(ProductFilter productFilter, Pageable pageable);
    Optional<ReadProductDTO> findById(Long id);
    boolean deleteProduct(Long id);
    ReadProductDTO create(CreateEditProductDTO createEditProductDTO);
    ReadProductDTO update(CreateEditProductDTO createEditProductDTO, Long id);
}
