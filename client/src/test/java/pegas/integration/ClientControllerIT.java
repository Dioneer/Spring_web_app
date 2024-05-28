package pegas.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import pegas.ApplicationRunner;
import pegas.dto.userdto.CreateUpdateUserDTO;
import pegas.entity.Role;

import java.io.FileInputStream;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@AutoConfigureMockMvc
public class ClientControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/users/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user"))
                .andExpect(model().attributeExists("user"));
    }
    @Test
    void start() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login"));
    }
    @Test
    void update() throws Exception {
        CreateUpdateUserDTO update = new CreateUpdateUserDTO("Ivan@mail.ru",
                LocalDate.of(1990,02,15),"Ivan", "Petrov",
                Role.valueOf("SILVER"), null);
        String object = mapper.writeValueAsString(update);
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/users/{id}/update", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(object))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    void create() throws Exception {
        CreateUpdateUserDTO update = new CreateUpdateUserDTO("Ivaaan@mail.ru",
                LocalDate.of(1990,02,15),"Ivan", "Petrov",
                Role.valueOf("SILVER"), null);
        String object = mapper.writeValueAsString(update);
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(object))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    void registration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/users/registration"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("user"));
    }
    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v3/users/{id}/delete",4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }
}
