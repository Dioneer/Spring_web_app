package pegas.integrationTest;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import pegas.ApplicationRunner1;
import pegas.dto.CreateEditProductDTO;
import pegas.dto.ProductFilter;
import pegas.dto.ReadProductDTO;
import pegas.service.ProductService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner1.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
public class ProductServiceIT {
    private static final Long PRODUCT_ID=1L;
    @Autowired
    private ProductService productService;

    @Test
    void findAll() {
        List<ReadProductDTO> result = productService.findAll();
        assertThat(result).hasSize(4);
    }
    @Test
    void findAllByFilter() {
        ProductFilter filter = new ProductFilter("Материнская плата MSI PRO", null, null);
        var pageable = PageRequest.of(0,5);
        List<ReadProductDTO> result = productService.findAll(filter, pageable);
        assertThat(result).hasSize(1);
    }

    @Test
    void findById() {
        var result = productService.findById(PRODUCT_ID);
        assertTrue(result.isPresent());
        var expectedResult = new ReadProductDTO(1L,"Материнская плата MSI PRO", "H610M-E DDR4",
               "7990.00", 10, 0, "light_bulb_light_dark_226180_1200x1600.jpg");
        result.ifPresent(i->assertEquals(i, expectedResult));
    }
    @Test
    void create() throws IOException {
        FileInputStream fis = new FileInputStream("images/light_bulb_light_dark_226180_1200x1600.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);
        CreateEditProductDTO createDTO= new CreateEditProductDTO();
        createDTO.setProductModel("Материнская плата Gerber");
        createDTO.setProductMark("H610M-MMM");
        createDTO.setPrice("17990.00");
        createDTO.setAmount(18);
        createDTO.setReserved(0);
        createDTO.setProductImage(multipartFile);
        ReadProductDTO productDTO = productService.create(createDTO);
        assertEquals(createDTO.getProductMark(), productDTO.getProductMark());
        assertEquals(createDTO.getProductModel(), productDTO.getProductModel());
        assertEquals(createDTO.getPrice(), productDTO.getPrice());
        assertEquals(createDTO.getAmount(), productDTO.getAmount());
        assertEquals(createDTO.getReserved(), productDTO.getReserved());
    }
    @Test
    void update() throws IOException {
        FileInputStream fis = new FileInputStream("images/light_bulb_light_dark_226180_1200x1600.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);
        CreateEditProductDTO createDTO= new CreateEditProductDTO();
        createDTO.setProductMark("Материнская плата Gerber");
        createDTO.setProductMark("H610M-MMM");
        createDTO.setPrice("17990.00");
        createDTO.setAmount(18);
        createDTO.setReserved(0);
        createDTO.setProductImage(multipartFile);
        ReadProductDTO productDTO = productService.update(createDTO, 1L);
        assertEquals(createDTO.getProductMark(), productDTO.getProductMark());
        assertEquals(createDTO.getProductModel(), productDTO.getProductModel());
        assertEquals(createDTO.getPrice(), productDTO.getPrice());
        assertEquals(createDTO.getAmount(), productDTO.getAmount());
        assertEquals(createDTO.getReserved(), productDTO.getReserved());
    }
    @Test
    void delete(){
        assertFalse(productService.deleteProduct(12L));
        assertTrue(productService.deleteProduct(1L));
    }
    @Test
    void reduceAmount(){
        Integer amount = 5;
        ReadProductDTO productDTO = productService.reduceAmount(1L, 5);
        assertSame(productDTO.getAmount(), amount);
    }
    @Test
    void reservation(){
        Integer amount = 5;
        Integer reservation = 5;
        ReadProductDTO product= productService.reservation(1L, amount);
        assertSame(product.getAmount(), amount);
        assertSame(product.getReserved(), reservation);
    }
    @Test
    void unReservation() throws IOException {
        Integer amount =18;
        Integer reservation = 0;
        FileInputStream fis = new FileInputStream("images/light_bulb_light_dark_226180_1200x1600.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);
        CreateEditProductDTO createDTO= new CreateEditProductDTO();
        createDTO.setProductModel("Материнская плата Gerber");
        createDTO.setProductMark("H610M-MMM");
        createDTO.setPrice("17990.00");
        createDTO.setAmount(18);
        createDTO.setReserved(0);
        createDTO.setProductImage(multipartFile);
        ReadProductDTO productDTO1 = productService.create(createDTO);
        ReadProductDTO productDTO2 = productService.deReservation(productDTO1.getId(), reservation);
        assertSame(productDTO2.getAmount(), amount);
        assertSame(productDTO2.getReserved(), 0);
    }


}
