package pegas.controller.userController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pegas.service.clientService.ClientService;

@Controller
@RequiredArgsConstructor
@RequestMapping("v3/users")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public String findByName(){
        return "asd";
    }

    @PostMapping("/update")
    public String updateUser(){
        return "asd";
    }

    @PostMapping("/create")
    public String createUser(){
        return "asd";
    }

    @PostMapping("/delete")
    public String deleteUser(){
        return "asd";
    }

}
