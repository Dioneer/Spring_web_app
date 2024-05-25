package pegas.service.clientService;

import pegas.dto.userdto.CreateUpdateUserDTO;
import pegas.dto.userdto.ReadUserDTO;

import java.util.Optional;

public interface CRUDService {
    Optional<ReadUserDTO> findById(Long id);
    boolean deleteUser(Long id);
    ReadUserDTO create(CreateUpdateUserDTO create);
    ReadUserDTO update(CreateUpdateUserDTO update, Long id);
}
