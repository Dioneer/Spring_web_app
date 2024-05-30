package unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pegas.dto.userdto.ReadUserDTO;
import pegas.entity.User;
import pegas.mapper.CreateUpdateUserMapper;
import pegas.mapper.ReadUserMapper;
import pegas.repository.UserRepository;
import pegas.service.clientService.ClientService;
import pegas.service.clientService.ImageClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientRepositoryTest {
    @InjectMocks
    private ClientService clientService;
    @Mock
    private CreateUpdateUserMapper createUpdate;
    @Mock
    private ReadUserMapper readUserMapper;
    @Mock
    private UserRepository repository;
    @Mock
    private ImageClientService imageClientService;

    @Test
    void findById(){
        User user = Mockito.mock(User.class);
        ReadUserDTO readUserDTO = Mockito.mock(ReadUserDTO.class);
        Optional<ReadUserDTO> expect = Optional.of(readUserDTO);

        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));
        Mockito.doReturn(readUserDTO).when(readUserMapper).map(user);

        Optional<ReadUserDTO> result = clientService.findById(1L);
        assertNotNull(result);
        assertEquals(expect, result);
    }
    @Test
    void findAll(){
        User user = Mockito.mock(User.class);
        List<User> back = new ArrayList<>();
        back.add(user);

        when(repository.findAll()).thenReturn(back);
        List<User> result = repository.findAll();
        assertNotNull(result);
        assertEquals(back, result);

    }

    @Test
    void delete(){
        User user = Mockito.mock(User.class);
        ReadUserDTO readUserDTOO = Mockito.mock(ReadUserDTO.class);

        Mockito.doReturn(Optional.of(user)).when(repository).findById(1L);
        Mockito.doReturn(readUserDTOO).when(readUserMapper).map(user);
        Optional<ReadUserDTO> result = clientService.findById(1L);
        assertAll(()->clientService.deleteUser(1L));
    }

}
