package pegas.integrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import pegas.ApplicationRunner1;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner1.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@AutoConfigureMockMvc
public class ProductControllerAPIIT {
    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void findAll() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1"))
//                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
//    }
}
