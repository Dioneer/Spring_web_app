package pegas.mapper;

import org.springframework.stereotype.Component;
import pegas.dto.CreateUpdateUserDTO;
import pegas.entity.User;

@Component
public class CreateUpdateUserMapper implements Mapper<CreateUpdateUserDTO, User>{

    @Override
    public User map(CreateUpdateUserDTO create) {
        return User.builder()
                .username(create.getUsername())
                .firstname(create.getFirstname())
                .lastname(create.getLastname())
                .birthdayDate(create.getBirthdayDate())
                .role(create.getRole())
                .build();
    }

    @Override
    public User map(CreateUpdateUserDTO update, User user) {
        user.setUsername(update.getUsername());
        user.setFirstname(update.getFirstname());
        user.setLastname(update.getLastname());
        user.setBirthdayDate(update.getBirthdayDate());
        user.setRole(update.getRole());
        return user;
    }
}
