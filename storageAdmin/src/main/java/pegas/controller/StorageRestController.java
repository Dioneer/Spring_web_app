package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pegas.service.StorageService;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4")
public class StorageRestController {
    private final StorageService storageService;

    @GetMapping("/{id}/image")
    public byte[] findImage(@PathVariable Long id){
        return Optional.of(storageService.findImage(id)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
