package pegas.service;

import org.springframework.data.domain.Pageable;
import pegas.dto.ProductFilter;
import pegas.dto.ReadProductDTO;
import pegas.dto.SendDTO;

import java.util.List;
import java.util.Optional;

public interface CRUDService {
    List<ReadProductDTO> findAll();
    List<ReadProductDTO> findAll(ProductFilter productFilter, Pageable pageable);
    Optional<ReadProductDTO> findById(Long id);
    Boolean deleteProduct(Long id);
    ReadProductDTO create(SendDTO sendDTO);
    ReadProductDTO update(SendDTO sendDTO, Long id);
}
