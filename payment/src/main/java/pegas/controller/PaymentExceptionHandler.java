package pegas.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class PaymentExceptionHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler(Exception.class)
        public ResponsePaymentHandler handlerException(Exception e){
            return new ResponsePaymentHandler(400, e.getMessage());
        }
}
