package pegas.service.storageService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import pegas.entity.storage.OrderDTO;
import pegas.entity.storage.ProductFilter;
import pegas.entity.storage.ReadProductDTO;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageApi {

    @Value("${app.path.storage}")
    private String storageApi;

    private final RestTemplate template;
    private final RestClient client;

    public List<ReadProductDTO> getAll(){
        ResponseEntity<List<ReadProductDTO>> response = template.exchange(storageApi,
                HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        return response.getBody();
    }

    public List<ReadProductDTO> getAllByFilter(ProductFilter productFilter){
        var result = template.postForObject(storageApi+"/filter",
                productFilter, ReadProductDTO[].class);
        return Arrays.stream(result).toList();
    }

    public ReadProductDTO getById(Long id){
        return template.getForObject(storageApi+"/"+id, ReadProductDTO.class);
    }

    public ReadProductDTO reservation(OrderDTO reservation, Long id){
        var result = template.postForEntity(storageApi+"/"+id+"/reservation",reservation, ReadProductDTO.class);
        return result.getBody();
    }

    public ReadProductDTO unReservation(OrderDTO reservation, Long id){
        var result = template.postForEntity(storageApi+"/"+id+"/unreservation",reservation, ReadProductDTO.class);
        return result.getBody();
    }

    public ReadProductDTO sale(OrderDTO reservation, Long id){
        var result = template.postForEntity(storageApi+"/"+id+"/sale",reservation, ReadProductDTO.class);
        return result.getBody();
    }
}
