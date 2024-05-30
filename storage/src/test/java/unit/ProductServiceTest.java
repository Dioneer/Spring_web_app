package unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import pegas.dto.CreateEditProductDTO;
import pegas.dto.ReadProductDTO;
import pegas.entity.Product;
import pegas.mapper.CreateEditProductMapper;
import pegas.mapper.ReadProductMapper;
import pegas.repository.ProductRepository;
import pegas.service.ImageService;
import pegas.service.ProductService;

import java.nio.charset.StandardCharsets;
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
        assertThat(result).hasSize(1);
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

    @Test
    void create(){
        Product product = Mockito.mock(Product.class);
        ReadProductDTO readProductDTO = Mockito.mock(ReadProductDTO.class);
        MockMultipartFile file =
                new MockMultipartFile("file","test contract.pdf", MediaType.APPLICATION_OCTET_STREAM_VALUE,
                        "123456".getBytes(StandardCharsets.UTF_8));

        CreateEditProductDTO create = new CreateEditProductDTO();
        create.setProductModel("aaaa");create.setProductMark("aaaa");create.setReserved(1);create.setAmount(1);
        create.setPrice("12");create.setProductImage(file);

        Mockito.doReturn(product).when(createEdit).map(create);
        Mockito.doReturn(product).when(productRepository).save(product);
        Mockito.doReturn(readProductDTO).when(read).map(product);

        assertAll(()->productService.create(create));
    }

    @Test
    void update(){
        Product product = Mockito.mock(Product.class);
        ReadProductDTO readProductDTO = Mockito.mock(ReadProductDTO.class);

        MockMultipartFile file =
                new MockMultipartFile("file","test contract.pdf", MediaType.APPLICATION_OCTET_STREAM_VALUE,
                        "123456".getBytes(StandardCharsets.UTF_8));

        CreateEditProductDTO create = new CreateEditProductDTO();
        create.setProductModel("aaaa");create.setProductMark("aaaa");create.setReserved(1);create.setAmount(1);
        create.setPrice("12");create.setProductImage(file);

        Mockito.doReturn(Optional.of(product)).when(productRepository).findById(1L);
        Mockito.doReturn(product).when(createEdit).map(create, product);
        Mockito.doReturn(product).when(productRepository).save(product);
        Mockito.doReturn(readProductDTO).when(read).map(product);

        assertAll(()->productService.update(create, 1L));
    }

    @Test
    void reservation(){
        Product product = Mockito.mock(Product.class);
        ReadProductDTO readProductDTO = Mockito.mock(ReadProductDTO.class);

        Mockito.doReturn(Optional.of(product)).when(productRepository).findById(1L);
        Mockito.doReturn(product).when(productRepository).save(product);
        Mockito.doReturn(readProductDTO).when(read).map(product);

        ReadProductDTO result = productService.reservation(1L,0);
        assertNotNull(result);
        assertEquals(result, readProductDTO);

    }
    @Test
    void unreservation(){
        Product product = Mockito.mock(Product.class);
        ReadProductDTO readProductDTO = Mockito.mock(ReadProductDTO.class);

        Mockito.doReturn(Optional.of(product)).when(productRepository).findById(1L);
        Mockito.doReturn(product).when(productRepository).save(product);
        Mockito.doReturn(readProductDTO).when(read).map(product);

        ReadProductDTO result = productService.reservation(1L,0);

        assertNotNull(result);
        assertEquals(result, readProductDTO);
    }

    @Test
    void findImage(){
        Product product = Mockito.mock(Product.class);
        Mockito.mock(StringUtils.class);
        Optional<byte[]> bytes = Optional.of("qwe".getBytes());

        Mockito.doReturn(Optional.of(product)).when(productRepository).findById(1L);
        Mockito.doReturn("qwe").when(product).getProductImage();
        Mockito.doReturn(Optional.of(bytes)).when(imageService).get("qwe");
        Optional<byte[]> result = productService.findImage(1L);

        assertNotNull(result);
        assertEquals(bytes, result.get());
    }

}
