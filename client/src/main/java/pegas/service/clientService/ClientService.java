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

    /**
     * CRUD operation find 1 by id
     * @param id of user
     * @return Optional<ReadUserDTO>
     */
    @Override
    public Optional<ReadUserDTO> findById(Long id) {
        return repository.findById(id).map(readUserMapper::map);
    }

    /**
     * a service for adding an item for purchase to the client database
     * @param id of user
     * @param amount of selling products
     * @param userId query parameter of user identification
     */
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

    /**
     * a service for adding an item for reservation to the client database
     * @param id of user
     * @param amount of selling products
     * @param userId query parameter of user identification
     */
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

    /**
     * a service for unreserved an item from database
     * @param id of user
     * @param amount of selling products
     * @param userId query parameter of user identification
     */
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

    /**
     * CRUD delete operation of reserved product
     * @param id of product
     * @param userId of user
     * @return true if ok or false if error
     */
    public boolean delete(Long id, Long userId){
        return reserveRepository.findByUserIdAndProductId(userId, id).map(i->{
                    reserveRepository.delete(i);
                    reserveRepository.flush();
                    return true;
                }).orElse(false);
    }

    /**
     * CRUD delete operation of user
     * @param id of user
     * @return true if ok or false if error
     */
    @Override
    public boolean deleteUser(Long id) {
        return repository.findById(id).map(i->{
            repository.delete(i);
            repository.flush();
            return true;
        }).orElse(false);
    }

    /**
     * CRUD create operation of user
     * @param create dto for user
     * @return ReadUserDto if successfully
     */
    @Override
    public ReadUserDTO create(CreateUpdateUserDTO create) {
        return Optional.of(create).map(i->{
                uploadImage(i.getMultipartFile());
                return createUpdate.map(i);
                }).map(repository::save).map(readUserMapper::map)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user was not create"));
    }

    /**
     * save image from form
     * @param multipartFile from form
     */
    @SneakyThrows
    private void uploadImage(MultipartFile multipartFile) {
        if(!multipartFile.isEmpty()) {
            imageClientService.upload(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        }
    }

    /**
     * find user image
     * @param id of user
     * @return Optional<byte[]> if successfully
     */
    public Optional<byte[]> findUserImage(Long id){
        return repository.findById(id).map(User::getImage).filter(StringUtils::hasText)
                .flatMap(imageClientService::get);
    }

    /**
     * CRUD update operation of user
     * @param update dto
     * @param id of user
     * @return ReadUserDTO if successfully
     */
    @Override
    public ReadUserDTO update(CreateUpdateUserDTO update, Long id) {
        return repository.findById(id).map(i-> {
                    uploadImage(update.getMultipartFile());
                    return createUpdate.map(update, i);
                }).map(repository::save).map(readUserMapper::map)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user was not update"));
    }

    /**
     * method for find users for form login
     * @param username unique field form db
     * @return Optional<User>
     * @throws UsernameNotFoundException
     */
    public Optional<User> findByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }

    /**
     * method for find users for form login @Override UserDetailsService
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username).map(i-> new org.springframework.security.core.userdetails.User(
                i.getUsername(),
                i.getPassword(), Collections.singleton(i.getRole())
        )).orElseThrow(()-> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}
