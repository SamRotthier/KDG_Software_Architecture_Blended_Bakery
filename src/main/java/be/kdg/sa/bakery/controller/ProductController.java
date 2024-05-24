package be.kdg.sa.bakery.controller;

import be.kdg.sa.bakery.controller.dto.*;
import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.services.IngredientService;
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

import java.net.PortUnreachableException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final IngredientService ingredientService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService, IngredientService ingredientService) {
        this.productService = productService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/createProduct")
    public String getNewProduct(Model model){
        NewProductDto newProductDto = new NewProductDto();

        //set step numbers of recipeSteps
        List<RecipeStepDto> recipeSteps = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            RecipeStepDto recipeStepDto = new RecipeStepDto();
            recipeStepDto.setStep(i);
            recipeSteps.add(recipeStepDto);
        }
        newProductDto.setRecipeSteps(recipeSteps);

        //List of ingredients fetched for option selection
        List<IngredientDto> ingredientsList= ingredientService.getIngredients().stream().map(i -> new IngredientDto(i.getId(), i.getName())).toList();

        model.addAttribute("newProductDto", newProductDto);
        model.addAttribute("ingredientList", ingredientsList);
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

    @GetMapping("/{id}/edit")
    public String getEditProduct(Model model, @PathVariable UUID id){
        Optional <Product> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            ProductDto productDto = convertToProductDto(product);
            model.addAttribute("editProduct", productDto);
            return "products/editProduct";
        } else{
            return "redirect:/"; // to check error catch
        }
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@Valid @ModelAttribute ("editProduct") ProductDto productDto, @PathVariable UUID id){
        Product product = productService.editProduct(productDto);
        //error handling

        return "redirect:/products/" +product.getProductId();
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

    @PostMapping("/{id}/recipeState")
    public String updateRecipeState(@ModelAttribute("product") ProductDto productDto, @PathVariable UUID id){
        logger.info("start finalize recipe");
        productService.finalizeRecipe(id);
        return "redirect:/products/"+id;
    }

    @PostMapping("/{id}/productState")
    public String changeProductState(@ModelAttribute("product") ProductDto productDto, @PathVariable UUID id){
        logger.info("start change product state");
        productService.changeProductState(id);
        return "redirect:/products/"+id;
    }

    private ProductDto convertToProductDto (Product product){
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getIngredients().stream().map(i -> new ProductIngredientDto(i.getIngredient().getId(), i.getIngredient().getName(), i.getQuantity())).toList(),
                product.getRecipeSteps().stream().map(i -> new RecipeStepDto(i.getId(), i.getStep(), i.getDescription())).toList(),
                product.getRecipeState(),
                product.getProductState()
        );
    }

}
