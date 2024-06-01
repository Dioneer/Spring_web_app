package pegas.service.clientService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pegas.dto.userdto.CreateUpdateUserDTO;
import pegas.dto.userdto.ReadUserDTO;
import pegas.entity.User;
import pegas.mapper.CreateUpdateUserMapper;
import pegas.mapper.ReadUserMapper;
import pegas.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements CRUDService, UserDetailsService {
   private final CreateUpdateUserMapper createUpdate;
   private final ReadUserMapper readUserMapper;
   private final UserRepository repository;
    private final ImageClientService imageClientService;


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
        return Optional.of(create).map(i->{
                uploadImage(i.getMultipartFile());
                return createUpdate.map(i);
                }).map(repository::save).map(readUserMapper::map)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user was not create"));
    }

    @SneakyThrows
    private void uploadImage(MultipartFile multipartFile) {
        if(!multipartFile.isEmpty()) {
            imageClientService.upload(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        }
    }
    public Optional<byte[]> findUserImage(Long id){
        return repository.findById(id).map(User::getImage).filter(StringUtils::hasText)
                .flatMap(imageClientService::get);
    }

    @Override
    public ReadUserDTO update(CreateUpdateUserDTO update, Long id) {
        return repository.findById(id).map(i-> {
                    uploadImage(update.getMultipartFile());
                    return createUpdate.map(update, i);
                }).map(repository::save).map(readUserMapper::map)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user was not update"));
    }

    public Optional<User> findByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username).map(i-> new org.springframework.security.core.userdetails.User(
                i.getUsername(),
                i.getPassword(), Collections.singleton(i.getRole())
        )).orElseThrow(()-> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}
