package pegas.service.paymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import pegas.aspect.TrackUserAction;
import pegas.dto.payment.PaymentDTO;
import pegas.dto.payment.TransferDTO;
import pegas.dto.payment.UserCartDto;
import pegas.dto.storage.ReadProductDTO;
import pegas.service.exceptions.RequestErrorException;
import pegas.service.exceptions.ServerErrorException;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class PaymentApi {
    @Value("${app.path.payment}")
    private String paymentApi;

    private final RestClient restClient;

    public PaymentDTO[] allPayments(){
        return restClient.post()
                .uri(paymentApi+"/all")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error 4xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error 5xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .body(PaymentDTO[].class);
    }

    public PaymentDTO findById(Long id){
        return restClient.post()
                .uri(paymentApi+"/"+id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error 4xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error 5xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .body(PaymentDTO.class);
    }

    public PaymentDTO cart(UserCartDto userCartDto){
        return restClient.post()
                .uri(paymentApi+"/personalInfo")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(userCartDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error 4xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error 5xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .body(PaymentDTO.class);
    }

    @TrackUserAction
    public String pay(TransferDTO transferDTO){
        return restClient.post()
                .uri(paymentApi)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(transferDTO)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error 4xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error 5xx restClinet: "+ res.getStatusCode() + res.getStatusText());})
                .body(String.class);
    }

}
