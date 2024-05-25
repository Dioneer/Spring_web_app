package pegas.controller.adviceController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import pegas.service.exceptions.RequestErrorException;
import pegas.service.exceptions.ServerErrorException;

@Slf4j
@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(RequestErrorException.class)
    public String handlerException1(Exception exception, RequestErrorException e){
        log.error(e.getMessage() + "not found", exception);
        return "error";
    }
    @ExceptionHandler(ServerErrorException.class)
    public String handlerException2(Exception exception, ServerErrorException e){
        log.error(e.getMessage() + "failed request", exception);
        return "error";
    }
    @ExceptionHandler(ResponseStatusException.class)
    public String handlerException3(Exception exception, ResponseStatusException e){
        log.error(e.getMessage() + "failed request", exception);
        return "error";
    }
}
