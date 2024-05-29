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
    public String handlerException1(Model model, RequestErrorException e){
        model.addAttribute("errors", e);
        log.error(e.getMessage() + "not found", e.fillInStackTrace());
        return "error";
    }
    @ExceptionHandler(ServerErrorException.class)
    public String handlerException2(Model model, ServerErrorException e){
        model.addAttribute("errors", e.getMessage());
        log.error(e.getMessage() + "failed request",e.fillInStackTrace());
        return "error";
    }
    @ExceptionHandler(ResponseStatusException.class)
    public String handlerException3(Model model, ResponseStatusException e){
        model.addAttribute("errors", e.getMessage());
        log.error(e.getMessage() + "failed request", e.fillInStackTrace());
        return "error";
    }
}
