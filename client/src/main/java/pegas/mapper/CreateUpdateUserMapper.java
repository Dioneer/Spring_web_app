package pegas.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pegas.dto.userdto.CreateUpdateUserDTO;
import pegas.entity.User;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class CreateUpdateUserMapper implements Mapper<CreateUpdateUserDTO, User>{

    @Override
    public User map(CreateUpdateUserDTO create) {
        User user = new User();
        user.setUsername(create.getUsername());
        user.setFirstname(create.getFirstname());
        user.setLastname(create.getLastname());
        user.setBirthdayDate(create.getBirthdayDate());
        user.setRole(create.getRole());
        user.setPassword("{noop}123");
        Optional.ofNullable(create.getMultipartFile()).filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(i->user.setImage(i.getOriginalFilename()));
        return user;
    }

    @Override
    public User map(CreateUpdateUserDTO update, User user) {
        user.setUsername(update.getUsername());
        user.setFirstname(update.getFirstname());
        user.setLastname(update.getLastname());
        user.setBirthdayDate(update.getBirthdayDate());
        user.setRole(update.getRole());
        user.setPassword("{noop}123");
        Optional.ofNullable(update.getMultipartFile()).filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(i->user.setImage(i.getOriginalFilename()));
        return user;
    }


}
