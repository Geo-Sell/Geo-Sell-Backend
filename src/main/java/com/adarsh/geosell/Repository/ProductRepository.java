package com.adarsh.geosell.Repository;

import com.adarsh.geosell.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    List<Product> findByRegionId(Long regionId);

    List<Product> findByTrendScoreGreaterThan(Double trendScore);

    @Query("SELECT p FROM Product p ORDER BY p.trendScore DESC LIMIT 10")
    List<Product> findTopTrendingProducts();

    @Query("SELECT p FROM Product p WHERE p.region.id = :regionId AND p.category = :category")
    List<Product> findByRegionAndCategory(@Param("regionId") Long regionId,
                                          @Param("category") String category);

    @Query("SELECT p FROM Product p WHERE p.avgPrice BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") Double minPrice,
                                   @Param("maxPrice") Double maxPrice);

    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findAllCategories();

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findByNameContaining(@Param("name") String name);
}