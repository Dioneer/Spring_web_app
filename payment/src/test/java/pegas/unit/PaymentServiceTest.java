package pegas.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import pegas.ApplicationRunner2;
import pegas.entity.Payment;
import pegas.repository.PaymentRepository;
import pegas.service.PaymentService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationRunner2.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private PaymentRepository paymentRepository;

    @Test
    void findById() {
        Payment payment1 = new Payment(1L, 11111L, new BigDecimal(0),
                new BigDecimal(100000), 1L);

        Mockito.doReturn(payment1).when(paymentRepository).findByUserId(1L);

        Optional<Payment> assertResult = paymentService.findByUserId(1L);
        assertTrue(assertResult.isPresent());
        Payment expectResult = payment1;
        assertResult.ifPresent(actual -> assertEquals(expectResult, actual));
    }
}