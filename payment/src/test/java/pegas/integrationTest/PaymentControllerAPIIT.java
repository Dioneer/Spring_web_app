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
import pegas.ApplicationRunner2;
import pegas.dto.Transfer;
import pegas.dto.UserDataFind;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner2.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@AutoConfigureMockMvc
public class PaymentControllerAPIIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void findAll() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
    @Test
    void findById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/{id}",1L))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value("1"));
    }
    @Test
    void findByCartNumber() throws Exception{
        UserDataFind userDataFind = new UserDataFind();
        userDataFind.setCartNumber(11111L);
        String order = mapper.writeValueAsString(userDataFind);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/personalInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(order))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }
    @Test
    void payment() throws Exception{
        Transfer transfer = new Transfer();
        transfer.setCartNumber(11111L);
        transfer.setSum(new BigDecimal(5000));
        String order = mapper.writeValueAsString(transfer);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(order))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }
}
