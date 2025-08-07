package com.adarsh.geosell.Service;
import com.adarsh.geosell.Entity.Product;
import com.adarsh.geosell.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsByRegion(Long regionId) {
        return productRepository.findByRegionId(regionId);
    }

    public List<Product> getTrendingProducts() {
        return productRepository.findTopTrendingProducts();
    }

    public List<Product> getProductsByRegionAndCategory(Long regionId, String category) {
        return productRepository.findByRegionAndCategory(regionId, category);
    }

    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    public List<String> getAllCategories() {
        return productRepository.findAllCategories();
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }
}
