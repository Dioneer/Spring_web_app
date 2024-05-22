package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pegas.dto.CreateEditProductDTO;
import pegas.dto.ProductFilter;
import pegas.dto.ReadProductDTO;
import pegas.service.ProductService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class RestProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        try{
            List<ReadProductDTO> products = productService.findAll();
            return ResponseEntity.ok().body(products);
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseError(HttpStatus.NOT_FOUND.value(), "Empty storage"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> findAllByFilter(@RequestBody ProductFilter filter, Pageable pageable){
        try{
            List<ReadProductDTO> products = productService.findAll(filter, pageable);
            return ResponseEntity.ok().body(products);
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseError(HttpStatus.NOT_FOUND.value(), "No suitable products"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
            return productService.findById(id).map(i-> ResponseEntity.ok().body(i))
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateEditProductDTO create){
        try{
            return ResponseEntity.ok().body(productService.create(create));
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseError(HttpStatus.BAD_REQUEST.value(), "Product was not save"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CreateEditProductDTO update, @PathVariable Long id){
        try{
            return ResponseEntity.ok().body(productService.update(update, id));
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseError(HttpStatus.BAD_REQUEST.value(), "Product was not update"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            return ResponseEntity.ok().body(productService.deleteProduct(id));
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseError(HttpStatus.BAD_REQUEST.value(), "Product was not delete"),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
