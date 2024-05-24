package pegas.service.paymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pegas.entity.payment.PaymentDTO;
import pegas.entity.payment.TransferDTO;
import pegas.entity.storage.ReadProductDTO;
import pegas.entity.payment.UserCartDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentApi {
    @Value("@{app.path.payment}")
    private String paymentApi;

    private final RestTemplate template;

    public List<PaymentDTO> allPayments(){
        ResponseEntity<List<PaymentDTO>> response = template.exchange(paymentApi+"/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        return response.getBody();
    }

    public PaymentDTO cart(UserCartDto userCartDto){
        ResponseEntity<PaymentDTO> response = template.postForEntity(paymentApi+"/personalInfo",userCartDto,
                PaymentDTO.class);
        return response.getBody();
    }

    public String pay(TransferDTO transferDTO){
        ResponseEntity<String> response = template.postForEntity(paymentApi,transferDTO,
                String.class);
        return response.getBody();
    }

}
