package be.kdg.sa.bakery.services;

import be.kdg.sa.bakery.controller.dto.NewProductDto;
import be.kdg.sa.bakery.controller.dto.ProductDto;
import be.kdg.sa.bakery.controller.dto.ProductIngredientDto;
import be.kdg.sa.bakery.controller.dto.RecipeStepDto;
import be.kdg.sa.bakery.domain.Enum.RecipeState;
import be.kdg.sa.bakery.domain.Ingredient;
import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.domain.Enum.ProductState;
import be.kdg.sa.bakery.domain.ProductIngredient;
import be.kdg.sa.bakery.domain.RecipeStep;
import be.kdg.sa.bakery.repositories.IngredientRepository;
import be.kdg.sa.bakery.repositories.ProductIngredientRepository;
import be.kdg.sa.bakery.repositories.ProductRepository;
import be.kdg.sa.bakery.repositories.RecipeStepRepository;
import be.kdg.sa.bakery.senders.RestSender;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private RestSender restSender;

    public ProductService(ProductRepository productRepository, IngredientRepository ingredientRepository, ProductIngredientRepository productIngredientRepository, RecipeStepRepository recipeStepRepository, RestSender restSender) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.productIngredientRepository = productIngredientRepository;
        this.recipeStepRepository = recipeStepRepository;
        this.restSender = restSender;
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

        List<ProductIngredientDto> ingredients = productDto.getIngredients();
        System.out.println("Ingredients:");
        System.out.println(product.getIngredients());
        if(ingredients != null){
            logger.info("Saving product ingredients: {}", ingredients);
            productIngredientRepository.saveAll(ingredients.stream().filter(i -> i.getId() != null).map(i -> new ProductIngredient(savedProduct, ingredientRepository.findById(i.getId()).orElseThrow(), i.getQuantity())).toList());

        }
        List<RecipeStepDto> steps = productDto.getRecipeSteps();
        System.out.println("Step value: " + productDto.getRecipeSteps().get(2));
        if(steps != null){
            logger.info("Saving product recipe steps: {}", steps);
            recipeStepRepository.saveAll(steps.stream().filter(step -> step.getDescription() != null && !step.getDescription().isEmpty()).map(step -> new RecipeStep(step.getId(), step.getStep(), step.getDescription(), savedProduct)).toList());
        }
        return savedProduct;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void changeProductState(UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            logger.warn("Product with id {} was not found", id);
            return;
        }
        product.setProductState(product.getProductState() == ProductState.ACTIVE ? ProductState.INACTIVE : ProductState.ACTIVE);
        restSender.sendChangeProductState(id);
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

        //TODO

        return productRepository.save(product);
    }

    @Transactional
    public void finalizeRecipe(UUID id) {
        logger.info("retrieve product");
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            logger.warn("Product with id={} was not found", id);
            return;
        }
        product.setRecipeState(RecipeState.FINALIZED);
        logger.info("RecipeState changed.");
        productRepository.save(product);
        restSender.sendNewProduct(product);
    }
}
