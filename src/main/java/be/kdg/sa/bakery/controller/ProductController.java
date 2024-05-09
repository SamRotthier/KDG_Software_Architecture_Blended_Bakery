package be.kdg.sa.bakery.controller;

import be.kdg.sa.bakery.controller.dto.ProductDto;
import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto){
        productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product was successfully created");
    }

    @GetMapping("/")
    public String showProducts(Model model){
        List<Product> products = productService.getAllProducts();

        if(products.isEmpty()){
            model.addAttribute("products", Collections.emptyList());
        }  else{
            List<ProductDto> productDtos = products.stream().map(this::convertToProductDto).toList();
            model.addAttribute("products", productDtos);
        }
        return "/overview";
    }

    @PostMapping("/{productid}/update-state")
    public String changeProductState(Model model, @PathVariable UUID productid){
        productService.changeProductState(productid);
        return "/"+productid;
    }

    private ProductDto convertToProductDto (Product product){
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getProductState()
        );
    }

}
