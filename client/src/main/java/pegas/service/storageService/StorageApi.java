package pegas.service.storageService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pegas.aspect.TrackUserAction;
import pegas.dto.storage.OrderDTO;
import pegas.dto.storage.ProductFilter;
import pegas.dto.storage.ReadProductDTO;
import pegas.service.exceptions.RequestErrorException;
import pegas.service.exceptions.ServerErrorException;

import javax.sql.rowset.serial.SerialException;

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
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                    throw new RequestErrorException("Error 4xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                    throw new ServerErrorException("Error 5xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO[].class);
    }

    public ReadProductDTO[] getAllByFilter(ProductFilter productFilter){
        return restClient.post()
                .uri(storageApi+"/filter")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(productFilter)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error 4xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error 5xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO[].class);
    }

    public ReadProductDTO getById(Long id){
        return restClient.get()
                .uri(storageApi+"/"+id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error 4xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error 5xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO.class);
    }
    @TrackUserAction
    public ReadProductDTO reservation(OrderDTO reservation, Long id){
        return restClient.post()
                .uri(storageApi+"/"+id+"/reservation")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(reservation)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error 4xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error 5xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO.class);
    }
    @TrackUserAction
    public ReadProductDTO unReservation(OrderDTO reservation, Long id){
        return restClient.post()
                .uri(storageApi+"/"+id+"/unreservation")
                .accept(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(reservation)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error 4xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error 5xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO.class);
    }
    @TrackUserAction
    public ReadProductDTO sale(OrderDTO reservation, Long id){
        return restClient.post()
                .uri(storageApi+"/"+id+"/sale")
                .accept(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(reservation)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error 4xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error 5xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO.class);
    }
}
