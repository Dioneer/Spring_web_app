package pegas.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import pegas.ApplicationRunner;
import pegas.dto.userdto.CreateUpdateUserDTO;
import pegas.dto.userdto.ReadUserDTO;
import pegas.entity.Role;
import pegas.service.clientService.ClientService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
public class ClientServiceIT {
    private static final Long USER_ID=1L;
    @Autowired
    private ClientService clientService;

    @Test
    void findById(){
        var actualResult = clientService.findById(USER_ID);
        assertTrue(actualResult.isPresent());

    }
    @Test
    void create() throws IOException {
        FileInputStream fis = new FileInputStream("images/san1.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);
        CreateUpdateUserDTO createDTO = new CreateUpdateUserDTO("reat@mail.ru", LocalDate.of(1998, 02, 25),
                "Ivan", "Pentrov", Role.valueOf("SILVER"),multipartFile);
        ReadUserDTO readUser = clientService.create(createDTO);
        assertEquals(createDTO.getUsername(), readUser.getUsername());
        assertEquals(createDTO.getBirthdayDate(), readUser.getBirthdayDate());
        assertEquals(createDTO.getFirstname(), readUser.getFirstname());
        assertEquals(createDTO.getLastname(), readUser.getLastname());
        assertSame(createDTO.getRole(), readUser.getRole());
        assertEquals(createDTO.getMultipartFile().getOriginalFilename(), createDTO.getMultipartFile().getOriginalFilename());
    }
    @Test
    void update() throws IOException {
        FileInputStream fis = new FileInputStream("images/san1.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);
        CreateUpdateUserDTO createDTO = new CreateUpdateUserDTO("reat@mail.ru", LocalDate.of(1998, 02, 25),
                "Ivan", "Chesnokov", Role.valueOf("SILVER"), multipartFile);
        ReadUserDTO readUser = clientService.update(createDTO, 1L);
        assertEquals(createDTO.getUsername(), readUser.getUsername());
        assertEquals(createDTO.getBirthdayDate(), readUser.getBirthdayDate());
        assertEquals(createDTO.getFirstname(), readUser.getFirstname());
        assertEquals(createDTO.getLastname(), readUser.getLastname());
        assertSame(createDTO.getRole(), readUser.getRole());
    }
    @Test
    void delete(){
        assertFalse(clientService.deleteUser(12L));
        assertTrue(clientService.deleteUser(1L));
    }

}
