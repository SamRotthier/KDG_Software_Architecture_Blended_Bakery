package be.kdg.sa.bakery.services;

import be.kdg.sa.bakery.controller.ProductController;
import be.kdg.sa.bakery.controller.dto.ProductDto;
import be.kdg.sa.bakery.domain.Product;
import be.kdg.sa.bakery.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductDto productDto) {
        if (productDto == null){
            throw new IllegalArgumentException("Product data cannot be null");
        }
        if (productRepository.existsByName(productDto.getName())){
            throw new IllegalArgumentException("Product with the same already exists");
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
}
