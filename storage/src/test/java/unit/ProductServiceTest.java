package unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pegas.dto.CreateEditProductDTO;
import pegas.dto.ReadProductDTO;
import pegas.entity.Product;
import pegas.mapper.CreateEditProductMapper;
import pegas.mapper.ReadProductMapper;
import pegas.repository.ProductRepository;
import pegas.service.ImageService;
import pegas.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CreateEditProductMapper createEdit;
    @Mock
    private ReadProductMapper read;
    @Mock
    private ImageService imageService;

    @Test
    void findById(){
        Product product = Mockito.mock(Product.class);
        ReadProductDTO readProductDTO = Mockito.mock(ReadProductDTO.class);
        Optional<ReadProductDTO> expect = Optional.of(readProductDTO);

        Mockito.doReturn(Optional.of(product)).when(productRepository).findById(1L);
        Mockito.doReturn(readProductDTO).when(read).map(product);
        Optional<ReadProductDTO> result = productService.findById(1L);
        assertNotNull(result);
        assertEquals(expect, result);
    }
    @Test
    void findAll(){
        Product product = Mockito.mock(Product.class);
        List<Product> back = new ArrayList<>();
        back.add(product);

        when(productRepository.findAll()).thenReturn(back);
        List<Product> result = productRepository.findAll();
        assertNotNull(result);
        assertEquals(back, result);

    }

    @Test
    void delete(){
        Product product = Mockito.mock(Product.class);
        ReadProductDTO readProductDTO = Mockito.mock(ReadProductDTO.class);

        Mockito.doReturn(Optional.of(product)).when(productRepository).findById(1L);
        Mockito.doReturn(readProductDTO).when(read).map(product);
        Optional<ReadProductDTO> result = productService.findById(1L);
        assertAll(()->productService.deleteProduct(1L));
    }

}
