package pegas.service.clientService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pegas.dto.CreateUpdateUserDTO;
import pegas.dto.ReadUserDTO;
import pegas.mapper.CreateUpdateUserMapper;
import pegas.mapper.ReadUserMapper;
import pegas.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements CRUDService{
   private final CreateUpdateUserMapper createUpdate;
   private final ReadUserMapper readUserMapper;
   private final UserRepository repository;


    @Override
    public Optional<ReadUserDTO> findById(Long id) {
        return repository.findById(id).map(readUserMapper::map);
    }

    @Override
    public boolean deleteUser(Long id) {
        return repository.findById(id).map(i->{
            repository.delete(i);
            repository.flush();
            return true;
        }).orElse(false);
    }

    @Override
    public ReadUserDTO create(CreateUpdateUserDTO create) {
        return Optional.of(create).map(createUpdate::map).map(repository::save).map(readUserMapper::map)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user was not create"));
    }

    @Override
    public ReadUserDTO update(CreateUpdateUserDTO update, Long id) {
        return repository.findById(id).map(i->createUpdate.map(update, i)).map(repository::save).map(readUserMapper::map)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user was not update"));
    }
}
