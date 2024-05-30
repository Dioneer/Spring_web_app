package pegas.service.storageService;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import pegas.aspect.TrackUserAction;
import pegas.dto.storage.OrderDTO;
import pegas.dto.storage.ProductFilter;
import pegas.dto.storage.ReadProductDTO;
import pegas.service.exceptions.RequestErrorException;
import pegas.service.exceptions.ServerErrorException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.util.MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE;

@Service
@RequiredArgsConstructor
public class StorageApi {

    private final DiscoveryClient discoveryClient;

    private final RestClient restClient;

    private String serviceURL() {
        ServiceInstance instance = discoveryClient.getInstances("appStorage")
                .stream().findAny()
                .orElseThrow(() -> new IllegalStateException("appStorage service unavailable"));
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(instance.getUri().toString() + "/api/v1");
        return uriComponentsBuilder.toUriString();
    }

    public ReadProductDTO[] getAll(){
        return restClient.get()
                .uri(serviceURL())
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                    throw new RequestErrorException("Error! Products was not found: "
                            + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                    throw new ServerErrorException("Error! Some problems with path for all payments: "
                            + res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO[].class);
    }

    public byte[] findImage(Long id){
        return restClient.get()
                .uri(serviceURL()+"/"+id+"/image")
                .accept(MediaType.valueOf(APPLICATION_OCTET_STREAM_VALUE))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error! Image was not found: "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error! Some problems with path for image: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(byte[].class);
    }

    public ReadProductDTO[] getAllByFilter(ProductFilter productFilter){
        return restClient.post()
                .uri(serviceURL() +"/filter")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(productFilter)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error! Filtering priducts was not found: "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error! Some problems with path for filter: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO[].class);
    }

    public ReadProductDTO getById(Long id){
        return restClient.get()
                .uri(serviceURL() +"/"+id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error!Product was not found by id: "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error!Some problems with path for find product by id: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO.class);
    }
    @TrackUserAction
    public ReadProductDTO reservation(OrderDTO reservation, Long id){
        return restClient.post()
                .uri(serviceURL() +"/"+id+"/reservation")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(reservation)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error!Product was not found for reservation: "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error!Some problems with path for or reservation: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO.class);
    }
    @TrackUserAction
    public ReadProductDTO unReservation(OrderDTO reservation, Long id){
        return restClient.post()
                .uri(serviceURL() +"/"+id+"/unreservation")
                .accept(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(reservation)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error!Product was not found for unreservation: "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error!Some problems with path for or unreservation: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO.class);
    }
    @TrackUserAction
    public ReadProductDTO sale(OrderDTO reservation, Long id){
        return restClient.post()
                .uri(serviceURL()+"/"+id+"/sale")
                .accept(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(reservation)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error!Product was not found for sale: "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error!Some problems with sale: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO.class);
    }
}
