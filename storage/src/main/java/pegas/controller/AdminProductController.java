package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pegas.dto.CreateEditProductDTO;
import pegas.dto.ReadProductDTO;
import pegas.service.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AdminProductController {
    private final ProductService productService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReadProductDTO> create(@ModelAttribute @Validated CreateEditProductDTO create){
        return ResponseEntity.status(201).body(productService.create(create));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadProductDTO> update(@ModelAttribute @Validated CreateEditProductDTO update, @PathVariable Long id){
        return ResponseEntity.ok().body(productService.update(update, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok().body(productService.deleteProduct(id));
    }
}
