package be.kdg.sa.bakery.services;

import be.kdg.sa.bakery.controller.dto.NewProductDto;
import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.domain.Enum.ProductState;
import be.kdg.sa.bakery.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(NewProductDto productDto) {
        if (productDto == null){
            throw new IllegalArgumentException("Product data cannot be null");
        }
        if (productRepository.existsByName(productDto.getName())){
            throw new IllegalArgumentException("Product with the same name already exists");
        }

        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());

        return productRepository.save(product);
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
        return productRepository.findById(id);
    }
}
