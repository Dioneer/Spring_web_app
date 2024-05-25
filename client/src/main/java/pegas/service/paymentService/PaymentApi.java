package pegas.service.paymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import pegas.dto.payment.PaymentDTO;
import pegas.dto.payment.TransferDTO;
import pegas.dto.payment.UserCartDto;
import pegas.dto.storage.ReadProductDTO;

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
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(PaymentDTO[].class);
    }

    public PaymentDTO cart(UserCartDto userCartDto){
        return restClient.post()
                .uri(paymentApi+"/personalInfo")
                .accept(APPLICATION_JSON)
                .body(userCartDto)
                .retrieve()
                .body(PaymentDTO.class);
    }

    public String pay(TransferDTO transferDTO){
        return restClient.post()
                .uri(paymentApi)
                .accept(APPLICATION_JSON)
                .body(transferDTO)
                .retrieve()
                .body(String.class);
    }

}
