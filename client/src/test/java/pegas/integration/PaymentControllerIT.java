package pegas.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import pegas.ApplicationRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@AutoConfigureMockMvc
public class PaymentControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/payment"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("payment"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/payment/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("payment"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void findByCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/payment/cart")
                        .param("cartNumber", "11111"))
                .andExpectAll(status().is4xxClientError());
    }
    @Test
    void payment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/payment/pay")
                        .param("cartNumber", "11111")
                        .param("sum", "2000"))
                .andExpectAll(status().is4xxClientError());
    }
}
