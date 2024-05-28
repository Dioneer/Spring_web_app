package pegas.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import pegas.ApplicationRunner1;
import pegas.dto.OrderDTO;
import pegas.dto.ProductFilter;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner1.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@AutoConfigureMockMvc
public class ProductControllerAPIIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void findAll() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void findById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/{id}",1L))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void findByFilter() throws Exception{
        ProductFilter productFilter = new ProductFilter("Материнская плата MSI PRO","H610M-E DDR4",
                new BigDecimal("7990.00"));
        String worker = mapper.writeValueAsString(productFilter);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(worker))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
    @Test
    void sale() throws Exception{
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAmount(5);
        String order = mapper.writeValueAsString(orderDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/{id}/sale", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(order))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value("1"));
    }
    @Test
    void reservation() throws Exception{
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAmount(5);
        String order = mapper.writeValueAsString(orderDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/{id}/reservation", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(order))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value("1"));
    }
    @Test
    void unreservation() throws Exception{
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAmount(1);
        String order = mapper.writeValueAsString(orderDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/2/unreservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(order))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
