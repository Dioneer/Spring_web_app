package pegas.integrationTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import pegas.ApplicationRunner2;
import pegas.dto.Transfer;
import pegas.dto.UserDataFind;
import pegas.entity.Payment;
import pegas.service.PaymentService;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner2.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class PaymentServiceIT {
    private static final Long PAYMENT_ID=1L;
    private static final Long CART_ID=11111L;

    @Autowired
    private PaymentService paymentService;

    @Test
    void findAll() {
        List<Payment> result = paymentService.findAll();
        assertThat(result).hasSize(3);
    }
    @Test
    void findByUserId() {
        var result = paymentService.findByUserId(PAYMENT_ID);
        assertTrue(result.isPresent());
        var expectedResult = new Payment(1L, 11111L, new BigDecimal("0.00"),
                new BigDecimal("100000.00"), 1L);
        result.ifPresent(i->assertEquals(i, expectedResult));
    }
    @Test
    void findByCartNumber(){
        UserDataFind userDataFind = new UserDataFind();
        userDataFind.setCartNumber(CART_ID);
        var result = paymentService.findByCartNumber(userDataFind);
        assertTrue(result.isPresent());
        var expectedResult = new Payment(1L, 11111L, new BigDecimal("0.00"),
                new BigDecimal("100000.00"), 1L);
        result.ifPresent(i->assertEquals(i, expectedResult));
    }
    @Test
    void create() throws IOException {
        Transfer transfer = new Transfer();
        transfer.setCartNumber(33333L);
        transfer.setSum(new BigDecimal("50000"));
        String result = paymentService.transaction(transfer);
        assertTrue(result.equals("the payment was completed successfully"));
        var expectResult = paymentService.findAll();
        assertThat(expectResult).hasSize(4);
    }
}
