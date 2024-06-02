package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
