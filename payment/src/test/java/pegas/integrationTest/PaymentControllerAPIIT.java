package pegas.integrationTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import pegas.ApplicationRunner2;

@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationRunner2.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
public class PaymentControllerAPIIT {
}
