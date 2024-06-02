package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pegas.dto.CreateEditProductDTO;
import pegas.dto.ReadProductDTO;
import pegas.service.StorageService;
import pegas.service.exceptions.RequestErrorException;
import pegas.service.exceptions.ServerErrorException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

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
