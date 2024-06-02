package pegas.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import pegas.dto.*;
import pegas.service.exceptions.RequestErrorException;
import pegas.service.exceptions.ServerErrorException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.springframework.http.MediaType.*;

@Service
@RequiredArgsConstructor
public class StorageService {
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

    public ReadProductDTO[] getAllByFilter(ProductFilter productFilter){
        return restClient.post()
                .uri(serviceURL() +"/filter")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(productFilter)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error! Filtering products was not found: "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error! Some problems with path for filter: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO[].class);
    }
    public ReadProductDTO create(SendDTO create) throws IOException {
        return restClient.post()
                .uri(serviceURL() +"/create")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(create)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error! Product was not create: "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error! Some problems with path for create: "
                                    + res.getStatusCode() + res.getStatusText() + req.getURI()+" ");})
                .body(ReadProductDTO.class);
    }
    public ReadProductDTO update(Long id, SendDTO create){
        return restClient.put()
                .uri(serviceURL()+"/"+id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(create)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error! Products was not found: "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error! Some problems with path for update: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(ReadProductDTO.class);
    }
    public Boolean delete(Long id){
        return restClient.delete()
                .uri(serviceURL() + "/"+id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error! Products was not found: "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error! Some problems with path for delete: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(Boolean.class);
    }
    public byte[] findImage(Long id){
        return restClient.get()
                .uri(serviceURL() + "/"+id+"/image")
                .accept(APPLICATION_OCTET_STREAM)
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

    @SneakyThrows
    public void upload(String imagePath, InputStream inputStream){
        Path fullPath = Path.of("D:\\backup\\4\\Spring_web_shop\\storage\\images", imagePath);
        try(inputStream){
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, inputStream.readAllBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        }
    }


}