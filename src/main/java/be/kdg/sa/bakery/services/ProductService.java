package be.kdg.sa.bakery.services;

import be.kdg.sa.bakery.controller.dto.NewProductDto;
import be.kdg.sa.bakery.controller.dto.ProductDto;
import be.kdg.sa.bakery.controller.dto.RecipeStepDto;
import be.kdg.sa.bakery.domain.Enum.RecipeState;
import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.domain.Enum.ProductState;
import be.kdg.sa.bakery.domain.ProductIngredient;
import be.kdg.sa.bakery.domain.RecipeStep;
import be.kdg.sa.bakery.repositories.IngredientRepository;
import be.kdg.sa.bakery.repositories.ProductIngredientRepository;
import be.kdg.sa.bakery.repositories.ProductRepository;
import be.kdg.sa.bakery.repositories.RecipeStepRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;
    private final ProductIngredientRepository productIngredientRepository;
    private final RecipeStepRepository recipeStepRepository;

    public ProductService(ProductRepository productRepository, IngredientRepository ingredientRepository, ProductIngredientRepository productIngredientRepository, RecipeStepRepository recipeStepRepository) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.productIngredientRepository = productIngredientRepository;
        this.recipeStepRepository = recipeStepRepository;
    }

    public Product createProduct(NewProductDto productDto) {
        logger.info("creating product: {}", productDto);
        if (productDto == null){
            logger.error("Product data is null");
            throw new IllegalArgumentException("Product data cannot be null");
        }
        if (productRepository.existsByName(productDto.getName())){
            logger.error("Product with the same name already exists: {}", productDto.getName());
            throw new IllegalArgumentException("Product with the same name already exists");
        }



        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setRecipeState(RecipeState.IN_PROGRESS);
        product.setProductState(ProductState.INACTIVE);
        Product savedProduct = productRepository.save(product);
        logger.info("Product saved with Id: {}", savedProduct.getProductId());

        Map<UUID, Integer> ingredients = productDto.getIngredients();
        if(ingredients != null){
            logger.info("Saving product ingredients: {}", ingredients);
            productIngredientRepository.saveAll(ingredients.entrySet().stream().map(entry -> new ProductIngredient(savedProduct, ingredientRepository.findById(entry.getKey()).orElseThrow(), entry.getValue())).toList());

        }
        List<RecipeStepDto> steps = productDto.getRecipeSteps();
        if(steps != null){
            logger.info("Saving product recipe steps: {}", steps);
            recipeStepRepository.saveAll(steps.stream().map(step -> new RecipeStep(step.getId(), step.getStep(), step.getDescription(), savedProduct)).toList());
        }
        return savedProduct;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void changeProductState(UUID uuid){
        Product product = productRepository.findById(uuid).orElse(null);
        //add errorHandling
        if(product == null){
            return;
        }

        product.setProductState(product.getProductState() == ProductState.ACTIVE ? ProductState.INACTIVE : ProductState.ACTIVE);
        productRepository.save(product);
    }

    public Optional<Product> getProductById(UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isPresent()){
            Product product = productOptional.get();
            List<RecipeStep> steps = recipeStepRepository.findByProductId(id);
            List<ProductIngredient> ingredients = productIngredientRepository.findByProductId(id);

            System.out.println("Ingredients:");
            int count = 0;
            for (ProductIngredient ingredient : product.getIngredients()) {
                System.out.println(count+"), Quantity: " + ingredient.getQuantity());
                count++;
            }

            product.setRecipeSteps(steps);
            product.setIngredients(ingredients);
            return Optional.of(product);
        } else{
            return Optional.empty();
        }
    }

    public Product editProduct(ProductDto productDto) {
        //error handling
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());

        return productRepository.save(product);
    }
}
