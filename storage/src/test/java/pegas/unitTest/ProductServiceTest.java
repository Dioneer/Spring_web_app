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
import pegas.repository.ProductRepository;
import pegas.service.ProductService;


import java.util.ArrayList;
import java.util.List;

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
        ReadProductDTO product = new ReadProductDTO(1L,"Материнская плата MSI PRO", "H610M-E DDR4",
                "7990.00", 10, 0, null);
        List<ReadProductDTO> arr = new ArrayList<>();
        arr.add(product);

        Mockito.doReturn(arr).when(productRepository).findAll();

        List<ReadProductDTO> assertResult = productService.findAll();
        assertFalse(assertResult.isEmpty());
        ReadProductDTO expectResult = new ReadProductDTO(1L,"Материнская плата MSI PRO", "H610M-E DDR4",
                "7990.00", 10, 0, null);
        assertEquals(expectResult, assertResult);
    }

}
