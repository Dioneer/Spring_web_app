package pegas.mapper;

import lombok.Value;
import pegas.dto.ReadUserDTO;
import pegas.entity.User;

@Value
public class ReadUserMapper implements Mapper<User, ReadUserDTO>{
    @Override
    public ReadUserDTO map(User user) {
        return new ReadUserDTO(user.getId(), user.getUsername(), user.getBirthdayDate(),
                user.getFirstname(), user.getLastname(), user.getRole());
    }
}
