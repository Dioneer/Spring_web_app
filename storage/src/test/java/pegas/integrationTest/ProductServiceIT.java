package pegas.integrationTest;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import pegas.ApplicationRunner1;
import pegas.dto.ReadProductDTO;
import pegas.service.ProductService;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner1.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductServiceIT {

    @Autowired
    private ProductService productService;

    @Test
    void findAll() {
        List<ReadProductDTO> result = productService.findAll();
        assertThat(result).hasSize(4);
    }

}
