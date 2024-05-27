package pegas.controller.clientController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pegas.service.storageService.StorageApi;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/v4/storage")
@RestController
public class ClientRestController {
    private final StorageApi storageApi;

    @GetMapping("/{id}/findImage")
    public byte[] findImage(Model model, @PathVariable("id") Long id){
        return Optional.of(storageApi.findImage(id)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
