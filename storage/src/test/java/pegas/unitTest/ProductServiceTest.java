package pegas.unitTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import pegas.ApplicationRunner1;
import pegas.dto.ReadProductDTO;
import pegas.entity.Product;
import pegas.repository.ProductRepository;
import pegas.service.ProductService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationRunner1.class)
public class ProductServiceTest {
    private static final Long PRODUCT_ID = 1L;
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @Test
    void findById(){
        Mockito.doReturn(Optional.of(new Product())).when(productRepository).findById(PRODUCT_ID);
        Optional<ReadProductDTO> assertResult = productService.findById(PRODUCT_ID);
        assertTrue(assertResult.isPresent());
        ReadProductDTO expectResult = new ReadProductDTO(1L,"Материнская плата MSI PRO", "H610M-E DDR4",
"7990.00", 10, 0, null);
        assertResult.ifPresent(actual-> assertEquals(expectResult, actual));
    }

}
