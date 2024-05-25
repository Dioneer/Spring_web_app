package pegas.service.storageService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import pegas.dto.storage.OrderDTO;
import pegas.dto.storage.ProductFilter;
import pegas.dto.storage.ReadProductDTO;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class StorageApi {

    @Value("${app.path.storage}")
    private String storageApi;

    private final RestClient restClient;

    public ReadProductDTO[] getAll(){
        return restClient.get()
                .uri(storageApi)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(ReadProductDTO[].class);
    }

    public ReadProductDTO[] getAllByFilter(ProductFilter productFilter){
        return restClient.post()
                .uri(storageApi+"/filter")
                .accept(APPLICATION_JSON)
                .body(productFilter)
                .retrieve()
                .body(ReadProductDTO[].class);
    }

    public ReadProductDTO getById(Long id){
        return restClient.get()
                .uri(storageApi+"/"+id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(ReadProductDTO.class);
    }

    public ReadProductDTO reservation(OrderDTO reservation, Long id){
        return restClient.post()
                .uri(storageApi+"/"+id+"/reservation")
                .accept(APPLICATION_JSON)
                .body(reservation)
                .retrieve()
                .body(ReadProductDTO.class);
    }

    public ReadProductDTO unReservation(OrderDTO reservation, Long id){
        return restClient.post()
                .uri(storageApi+"/"+id+"/unreservation")
                .accept(APPLICATION_JSON)
                .body(reservation)
                .retrieve()
                .body(ReadProductDTO.class);
    }

    public ReadProductDTO sale(OrderDTO reservation, Long id){
        return restClient.post()
                .uri(storageApi+"/"+id+"/sale")
                .accept(APPLICATION_JSON)
                .body(reservation)
                .retrieve()
                .body(ReadProductDTO.class);
    }
}
