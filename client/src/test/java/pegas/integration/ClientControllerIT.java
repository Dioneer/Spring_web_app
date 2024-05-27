package pegas.integration;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@AutoConfigureMockMvc
public class ClientControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/users/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user"))
                .andExpect(model().attributeExists("user"));
    }
    @Test
    void strat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login"));
    }
    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/users/{id}/update", 2)
                        .param("username", "Ivan@mail.ru")
                        .param("birthdayDate", "1990-02-15")
                        .param("firstname", "Ivan")
                        .param("lastname", "Petrov")
                        .param("role", "SILVER")
                )
                .andExpectAll(status().is3xxRedirection(), redirectedUrlPattern("/v3/users/{\\d+}"));
    }
    @Test
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/users/create")
                        .param("username", "Ivan@mail.ru")
                        .param("birthdayDate", "1990-02-15")
                        .param("firstname", "Ivan")
                        .param("lastname", "Petrov")
                        .param("role", "SILVER")
                )
                .andExpectAll(status().is3xxRedirection(),redirectedUrlPattern("/v3/users/{\\d+}"));
    }
    @Test
    void registration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/users/registration"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("user"));
    }
    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/users/{id}/delete",4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("404"));
    }
}
