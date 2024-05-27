package pegas.integration;

import org.hamcrest.collection.IsArray;
import org.hamcrest.collection.IsCollectionWithSize;
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
import pegas.ApplicationRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@AutoConfigureMockMvc
public class StorageControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/storage"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("products"));

    }
    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/storage/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("products"));
    }
    @Test
    void reduce() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/storage/{id}/sale", 2)
                        .param("amount", "5")
                )
                .andExpectAll(status().is2xxSuccessful());
    }
    @Test
    void reservation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/storage/{id}/reservation", 2)
                        .param("amount", "5")

                )
                .andExpectAll(status().is2xxSuccessful());
    }
    @Test
    void unreservation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/storage/{id}/unreservation",4)
                .param("amount", "5")
                )
                .andExpectAll(status().is2xxSuccessful());
    }
}
