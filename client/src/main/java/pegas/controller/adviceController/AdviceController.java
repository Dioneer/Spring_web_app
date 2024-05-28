package pegas.controller.adviceController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import pegas.service.exceptions.RequestErrorException;
import pegas.service.exceptions.ServerErrorException;

@Slf4j
@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(RequestErrorException.class)
    public String handlerException1(Model model, Exception exception, RequestErrorException e){
        model.addAttribute("error", e);
        model.addAttribute("trace", exception);
        log.error(e.getMessage() + "not found", exception.getMessage());
        return "error";
    }
    @ExceptionHandler(ServerErrorException.class)
    public String handlerException2(Model model, Exception exception, ServerErrorException e){
        model.addAttribute("error", e);
        model.addAttribute("trace", exception);
        log.error(e.getMessage() + "failed request", exception.getMessage());
        return "error";
    }
    @ExceptionHandler(ResponseStatusException.class)
    public String handlerException3(Model model, Exception exception, ResponseStatusException e){
        model.addAttribute("error", e);
        model.addAttribute("trace", exception);
        log.error(e.getMessage() + "failed request", exception.getMessage());
        return "error";
    }
}
