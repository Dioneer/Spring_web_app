package pegas.mapper;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;
import pegas.dto.userdto.ReadUserDTO;
import pegas.entity.BuyProduct;
import pegas.entity.ReserveProduct;
import pegas.entity.User;
import pegas.repository.BuyProductRepository;
import pegas.repository.ReserveProductRepository;

import java.util.List;
import java.util.Optional;

@Value
@Component
@RequiredArgsConstructor
public class ReadUserMapper implements Mapper<User, ReadUserDTO>{

    private final ReserveProductRepository reserveRepository;
    private final BuyProductRepository buyRepository;

    @Override
    public ReadUserDTO map(User user) {

        return new ReadUserDTO(user.getId(), user.getUsername(), user.getBirthdayDate(),
                user.getFirstname(), user.getLastname(), user.getRole(), user.getImage(),getReserve(user.getId()),
                getBuy(user.getId()));
    }

    private List<BuyProduct> getBuy(Long userId){
        return Optional.ofNullable(userId).map(i-> buyRepository.findByUserId(userId)).orElse(null);
    }
    private List<ReserveProduct> getReserve(Long userId){
        return Optional.ofNullable(userId).map(reserveRepository::findByUserId).orElse(null);
    }

}
