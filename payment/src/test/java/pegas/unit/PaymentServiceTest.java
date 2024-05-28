package pegas.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import pegas.ApplicationRunner2;
import pegas.dto.Transfer;
import pegas.dto.UserDataFind;
import pegas.entity.Payment;
import pegas.repository.PaymentRepository;
import pegas.service.PaymentService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    @Test
    void findAll() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment(1L, 11111L, new BigDecimal(0),
                new BigDecimal(100000), 1L));
        Mockito.doReturn(payments).when(paymentRepository).findAll();
        List<Payment> actual = paymentService.findAll();
        List<Payment> expectResult = payments;
        assertEquals(expectResult, actual);
    }
    @Test
    void findByCartNumber() {
        Payment payment1 = new Payment(1L, 11111L, new BigDecimal(0),
                new BigDecimal(100000), 1L);
        UserDataFind userDataFind = new UserDataFind();
        userDataFind.setCartNumber(11111L);
        Mockito.doReturn(payment1).when(paymentRepository).findByCartNumber(11111L);
        Optional<Payment> actual = paymentService.findByCartNumber(userDataFind);
        assertTrue(actual.isPresent());
        Payment expectResult = payment1;
        actual.ifPresent(i -> assertEquals(expectResult, i));
    }
}