package pegas.service.paymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import pegas.aspect.TrackUserAction;
import pegas.dto.payment.PaymentDTO;
import pegas.dto.payment.TransferDTO;
import pegas.dto.payment.UserCartDto;
import pegas.service.exceptions.RequestErrorException;
import pegas.service.exceptions.ServerErrorException;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class PaymentApi {

    private final RestClient restClient;

    private final DiscoveryClient discoveryClient;

    /**
     * method for create path for implementation service name from eureka
     * @return url for eureka
     */
    private String serviceURL() {
        ServiceInstance instance = discoveryClient.getInstances("appPayment")
                .stream().findAny()
                .orElseThrow(() -> new IllegalStateException("appStorage service unavailable"));
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(instance.getUri().toString() + "/api/v2");
        return uriComponentsBuilder.toUriString();
    }

    /**
     * find all payments. This method not used yet
     * @return
     */
    public PaymentDTO[] allPayments(){
        return restClient.post()
                .uri(serviceURL()+"/all")
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error! No payments was found:" +
                                    " "+ res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error! Some problems with path for all payments: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(PaymentDTO[].class);
    }

    /**
     * CRUD find payment
     * @param id of user
     * @return PaymentDTO
     */
    public PaymentDTO findById(Long id){
        return restClient.post()
                .uri(serviceURL()+"/"+id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error! Payment was not found: "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error! Some problems with path for payment: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(PaymentDTO.class);
    }

    /**
     * CRUD find payment
     * @param userCartDto
     * @return
     */
    public PaymentDTO cart(UserCartDto userCartDto){
        return restClient.post()
                .uri(serviceURL()+"/personalInfo")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(userCartDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error! Cart was not found:  "+
                                    res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error! Some problems with path for cart: "+
                                    res.getStatusCode() + res.getStatusText());})
                .body(PaymentDTO.class);
    }

    /**
     * the method for carrying out the payment
     * @param transferDTO for transfer payment
     * @return
     */
    @TrackUserAction
    public String pay(TransferDTO transferDTO){
        return restClient.post()
                .uri(serviceURL())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(transferDTO)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RequestErrorException("Error! Cart was not found:  "
                                    + res.getStatusCode() + res.getStatusText());})
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> {
                            throw new ServerErrorException("Error! Some problems with path for cart: "
                                    + res.getStatusCode() + res.getStatusText());})
                .body(String.class);
    }

}
