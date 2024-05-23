package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pegas.dto.CreateEditProductDTO;
import pegas.dto.OrderDTO;
import pegas.dto.ProductFilter;
import pegas.dto.ReadProductDTO;
import pegas.service.ProductService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
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
            if(!products.isEmpty()) {
                return ResponseEntity.ok().body(products);
            }else{
                return new ResponseEntity<>(new ResponseError
                        (HttpStatus.CONTINUE.value(),"No suitable products"),
                        HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseError
                    (HttpStatus.NOT_FOUND.value(), "Some problems with fields"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
            return productService.findById(id).map(i-> ResponseEntity.ok().body(i))
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@ModelAttribute @Validated CreateEditProductDTO create){
        try{
            return ResponseEntity.status(201).body(productService.create(create));
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseError
                    (HttpStatus.BAD_REQUEST.value(), "Product was not save"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@ModelAttribute @Validated CreateEditProductDTO update, @PathVariable Long id){
        try{
            return ResponseEntity.ok().body(productService.update(update, id));
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseError
                    (HttpStatus.BAD_REQUEST.value(), "Product was not update"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            return ResponseEntity.ok().body(productService.deleteProduct(id));
        }catch(Exception e){
            return new ResponseEntity<>(new ResponseError
                    (HttpStatus.BAD_REQUEST.value(), "Product was not delete"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/sale")
    public ResponseEntity<?> reduceAmountForSale(@PathVariable("id") Long id,
                                                 @Validated @RequestBody OrderDTO orderDTO) {
        try{
            if(productService.reduceAmount(id, orderDTO.getAmount())) {
                return ResponseEntity.ok().body(true);
            }else {
                return ResponseEntity.badRequest()
                        .body(new ResponseEntity<>(new ResponseError
                                (HttpStatus.BAD_REQUEST.value(), "not enough amount"),
                        HttpStatus.BAD_REQUEST));
            }
        }catch(Exception e) {
            return new ResponseEntity<>(new ResponseError
                    (HttpStatus.BAD_REQUEST.value(), "Some problems with fields"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/reservation")
    public ResponseEntity<?> reservation(@PathVariable("id") Long id, @Validated @RequestBody OrderDTO orderDTO){
        try{
            if(productService.reservation(id, orderDTO.getAmount())) {
                return ResponseEntity.ok().body(true);
            }else{
                return new ResponseEntity<>(new ResponseError
                        (HttpStatus.BAD_REQUEST.value(), "Not enough amount"),
                    HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e) {
            return new ResponseEntity<>(new ResponseError
                    (HttpStatus.BAD_REQUEST.value(), "Some problems with fields"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/unreservation")
    public ResponseEntity<?> deReservation(@PathVariable("id") Long id, @Validated @RequestBody OrderDTO orderDTO){
        try{
            if(productService.deReservation(id, orderDTO.getAmount())) {
                return ResponseEntity.ok().body(true);
            }else{
                return new ResponseEntity<>(new ResponseError
                        (HttpStatus.BAD_REQUEST.value(), "Not enough amount"),
                    HttpStatus.BAD_REQUEST);
         }
        }catch(Exception e) {
            return new ResponseEntity<>(new ResponseError
                (HttpStatus.BAD_REQUEST.value(), "Some problems with fields"),
                HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> findAvatar(@PathVariable("id") Long id){
        byte[] bytes;
        try {
            bytes = productService.findAvatar(id).orElseThrow();
        } catch (RuntimeException e){
            return new ResponseEntity<>(new ResponseError
                    (HttpStatus.NOT_FOUND.value(), "image is absent today"),HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .contentLength(bytes.length).body(bytes);
    }


}
