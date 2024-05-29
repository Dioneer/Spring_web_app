package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pegas.dto.CreateEditProductDTO;
import pegas.dto.ProductFilter;
import pegas.service.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AdminProductController {
    private final ProductService productService;

    @GetMapping
    public String findAll(Model model){
        model.addAttribute("products", productService.findAll());
        return "admin";
    }
    @GetMapping("/{id}/update")
    public String findById(Model model, @PathVariable Long id){
        productService.findById(id).map(i-> model.addAttribute("product", i))
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return "update";
    }

    @GetMapping("/create")
    public String create(Model model,@ModelAttribute CreateEditProductDTO create){
        model.addAttribute("product", create);
        return "create";
    }

    @PostMapping("/filter")
    public String findAll(Model model, @ModelAttribute ProductFilter productFilter){
        var pages = PageRequest.of(0, 5, Sort.by("id"));
        model.addAttribute("products", productService.findAll(productFilter, pages));
        return "admin";
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String create(@ModelAttribute @Validated CreateEditProductDTO create,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("product", create);
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/v1";
        }
        productService.create(create);
        return "redirect:/v1";
    }

    @PostMapping(value = "/{id}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String update(@ModelAttribute @Validated CreateEditProductDTO update, @PathVariable Long id,
                         BindingResult bindingResult,RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("product", update);
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            return "redirect:/v1";
        }
        productService.update(update,id);
        return "redirect:/v1";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        if(!productService.deleteProduct(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/v1";
    }

    @ExceptionHandler
    public String exceptions(Model model, Exception e){
        model.addAttribute("errors", e.getMessage());
        return "error";
    }
}
