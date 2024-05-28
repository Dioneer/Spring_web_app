package pegas.controller.clientController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pegas.service.clientService.ClientService;
import pegas.service.storageService.StorageApi;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/v3/image")
@RestController
public class ClientRestController {
    private final StorageApi storageApi;
    private final ClientService clientService;

    @GetMapping("/{id}/findImage")
    public byte[] findImage(Model model, @PathVariable("id") Long id){
        return Optional.of(storageApi.findImage(id)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @GetMapping(value = "/{id}/userImage", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] findUserImage(Model model, @PathVariable("id") Long id){
        return clientService.findUserImage(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
