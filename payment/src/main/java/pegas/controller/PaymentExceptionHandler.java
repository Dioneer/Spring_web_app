package pegas.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class PaymentExceptionHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler(Exception.class)
        public ResponsePaymentHandler handlerException(Exception e){
            log.error("Failed in Payment module"+"||"+LocalDateTime.now()+"||"+e.getMessage());
            return new ResponsePaymentHandler(LocalDateTime.now(), e.getMessage());
        }
}
