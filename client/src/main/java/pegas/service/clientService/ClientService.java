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
import pegas.dto.storage.ReadProductDTO;
import pegas.dto.userdto.*;
import pegas.entity.BuyProduct;
import pegas.entity.ReserveProduct;
import pegas.entity.User;
import pegas.mapper.*;
import pegas.repository.BuyProductRepository;
import pegas.repository.ReserveProductRepository;
import pegas.repository.UserRepository;
import pegas.service.storageService.StorageApi;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements CRUDService, UserDetailsService {
    private final CreateUpdateUserMapper createUpdate;
    private final ReadUserMapper readUserMapper;
    private final UserRepository repository;
    private final ImageClientService imageClientService;
    private final ReserveProductRepository reserveRepository;
    private final BuyProductRepository buyRepository;
    private final CreateBuyMapper createBuyMapper;
    private final ReadBuyMapper buyMapper;
    private final CreateReservedMapper createReservedMapper;
    private final ReadReservedMapper readReservedMapper;
    private final StorageApi storageApi;


    @Override
    public Optional<ReadUserDTO> findById(Long id) {
        return repository.findById(id).map(readUserMapper::map);
    }

    public void createBuy(Long id, int amount, Long userId) {
        BuyProduct buyProduct = buyRepository.findByUserIdAndProductId(userId, id).orElse(null);
        if(buyProduct==null) {
            ReadProductDTO productDTO = storageApi.getById(id);
            CreateBuyDTO create = new CreateBuyDTO(userId, productDTO.getId(), productDTO.getProductMark(),
                    productDTO.getProductModel(), new BigDecimal(productDTO.getPrice()), amount);
            Optional.of(create).map(createBuyMapper::map).map(buyRepository::save).map(buyMapper::map)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product was not bought"));
        }else{
            buyProduct.setAmount(buyProduct.getAmount()+amount);
            BuyProduct buy = buyRepository.save(buyProduct);
        }
    }
    public void createRes(Long id, int amount, Long userId) {
        ReserveProduct reserveProduct= reserveRepository.findByUserIdAndProductId(userId, id).orElse(null);
        System.out.println(reserveProduct);
        if(reserveProduct==null) {
            ReadProductDTO productDTO = storageApi.getById(id);
            CreateReservedDTO create = new CreateReservedDTO(userId, productDTO.getId(), productDTO.getProductMark()
                    , productDTO.getProductModel(), new BigDecimal(productDTO.getPrice()), amount);
            Optional.of(create).map(createReservedMapper::map).map(reserveRepository::save).map(readReservedMapper::map)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product was not reserved bought"));
        }else{
            reserveProduct.setAmount(reserveProduct.getAmount()+amount);
            ReserveProduct reserve = reserveRepository.save(reserveProduct);
        }
    }

    public void unreserved(Long id, int amount, Long userId) {
        ReserveProduct reserveProduct = reserveRepository.findByUserIdAndProductId(userId, id).orElse(null);
        assert reserveProduct != null;
        if(reserveProduct.getAmount()>=amount){
            reserveProduct.setAmount(reserveProduct.getAmount()-amount);
            reserveRepository.save(reserveProduct);
            if(reserveProduct.getAmount()==0){
                if(!delete(id, userId)){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product was not delete from reserved");
                }
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not enough amount");
        }
    }
    public boolean delete(Long id, Long userId){
        return reserveRepository.findByUserIdAndProductId(userId, id).map(i->{
                    reserveRepository.delete(i);
                    reserveRepository.flush();
                    return true;
                }).orElse(false);
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
