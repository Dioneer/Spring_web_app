package pegas.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentExceptionHandler {
        @ExceptionHandler(Exception.class)
        public ResponsePaymentHandler handlerException(Exception e){
            return new ResponsePaymentHandler(400, e.getMessage());
        }
}
