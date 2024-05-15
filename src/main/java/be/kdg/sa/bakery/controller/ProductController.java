package be.kdg.sa.bakery.controller;

import be.kdg.sa.bakery.controller.dto.NewProductDto;
import be.kdg.sa.bakery.controller.dto.ProductDto;
import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


   @GetMapping("/createProduct")
    public String getNewProduct(Model model){
        NewProductDto newProductDto = new NewProductDto();
        model.addAttribute("newProductDto", newProductDto);
        return "products/createProduct";
    }

    @PostMapping("/createProduct")
    public String createProduct(@Valid @ModelAttribute("newProductDto") NewProductDto productDto){
        logger.debug("Received request to create product: {}", productDto);
        Product product = productService.createProduct(productDto);
        if(product == null){
            logger.warn("Product creation failed.");
            return "redirect:/products";
        }
        logger.info("Product created successfully with ID: {}", product.getProductId());
        return "redirect:/products/" +product.getProductId();
    }

    @GetMapping("/{id}")
    public String getProduct(Model model, @PathVariable UUID id){
        Optional <Product> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            ProductDto productDto = convertToProductDto(product);
            model.addAttribute("product", productDto);
            return "products/product";
        } else{
            return "/"; // to check error catch
        }
    }

    @GetMapping("")
    public String showProducts(Model model){
        List<Product> products = productService.getAllProducts();

        if(products.isEmpty()){
            model.addAttribute("products", Collections.emptyList());
        }  else{
            List<ProductDto> productDtos = products.stream().map(this::convertToProductDto).toList();
            model.addAttribute("products", productDtos);
        }
        return "products/products";
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
