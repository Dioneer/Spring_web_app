package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pegas.dto.*;
import pegas.service.ProductService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ClientProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ReadProductDTO>> getProducts(){
        return ResponseEntity.ok().body(productService.findAll());
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ReadProductDTO>> findAllByFilter(@RequestBody ProductFilter filter, Pageable pageable){
        return ResponseEntity.ok().body(productService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadProductDTO> findById(@PathVariable Long id){
        return productService.findById(id).map(i-> ResponseEntity.ok().body(i))
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "product was not found with id "
                    +id));
    }

    @PostMapping("/{id}/sale")
    public ResponseEntity<ReadProductDTO> reduceAmountForSale(@PathVariable("id") Long id,
                                                 @Validated @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok().body(productService.reduceAmount(id, orderDTO.getAmount()));
    }

    @PostMapping("/{id}/reservation")
    public ResponseEntity<ReadProductDTO> reservation(@PathVariable("id") Long id, @Validated @RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok().body(productService.reservation(id, orderDTO.getAmount()));
    }

    @PostMapping("/{id}/unreservation")
    public ResponseEntity<ReadProductDTO> deReservation(@PathVariable("id") Long id, @Validated @RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok().body(productService.deReservation(id, orderDTO.getAmount()));
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> findImage(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(productService.findImage(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "image was not found")));

    }
    @PostMapping(value = "/create")
    public ResponseEntity<ReadProductDTO> create (@RequestBody SendDTO sendDTO){
        return ResponseEntity.ok().body(productService.create(sendDTO));
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity<ReadProductDTO> update (@PathVariable("id") Long id,
                                                  @RequestBody SendDTO sendDTO){
        System.out.println(sendDTO);
        return ResponseEntity.ok().body(productService.update(sendDTO, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete (@PathVariable("id") Long id){
        return ResponseEntity.ok().body(productService.deleteProduct(id));
    }

    @GetMapping("/{id}/findImage")
    public byte[] findImage(Model model, @PathVariable("id") Long id){
        return productService.findImage(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
