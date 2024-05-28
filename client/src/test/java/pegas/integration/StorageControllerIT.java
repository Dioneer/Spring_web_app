package pegas.integration;

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
import org.springframework.transaction.annotation.Transactional;
import pegas.ApplicationRunner;
import pegas.dto.storage.OrderDTO;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@AutoConfigureMockMvc
public class StorageControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/storage?id={userId}",1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("products"));

    }
    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/storage/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("product"))
                .andExpect(model().attributeExists("products"));
    }
    @Test
    void reduce() throws Exception {
        OrderDTO orderDTO = new OrderDTO(1);
        String obj = mapper.writeValueAsString(orderDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/storage/{id}/sale", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void reservation() throws Exception {
        OrderDTO orderDTO = new OrderDTO(2);
        String obj = mapper.writeValueAsString(orderDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/storage/{id}/reservation", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(obj))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void unreservation() throws Exception {
        OrderDTO orderDTO = new OrderDTO(2);
        String obj = mapper.writeValueAsString(orderDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/storage/{id}/unreservation",4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(obj))
                .andExpect(status().is4xxClientError());
    }
}
