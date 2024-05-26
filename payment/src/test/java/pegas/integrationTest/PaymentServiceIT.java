package pegas.integrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import pegas.ApplicationRunner2;
import pegas.entity.Payment;
import pegas.service.PaymentService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner2.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class PaymentServiceIT {
        @Autowired
        private PaymentService paymentService;

        @Test
        void findAll() {
            List<Payment> result = paymentService.findAll();
            assertThat(result).hasSize(3);
        }
}
