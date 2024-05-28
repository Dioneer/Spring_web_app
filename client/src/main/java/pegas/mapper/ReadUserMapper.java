package pegas.mapper;

import lombok.Value;
import org.springframework.stereotype.Component;
import pegas.dto.userdto.ReadUserDTO;
import pegas.entity.User;

@Value
@Component
public class ReadUserMapper implements Mapper<User, ReadUserDTO>{
    @Override
    public ReadUserDTO map(User user) {
        return new ReadUserDTO(user.getId(), user.getUsername(), user.getBirthdayDate(),
                user.getFirstname(), user.getLastname(), user.getRole(), user.getImage());
    }
}
