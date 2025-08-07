package com.adarsh.geosell.Repository;
import com.adarsh.geosell.Entity.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompetitorRepository extends JpaRepository<Competitor, Long> {

    List<Competitor> findByRegionId(Long regionId);

    List<Competitor> findByProductCategory(String productCategory);

    @Query("SELECT c FROM Competitor c WHERE c.region.id = :regionId AND c.productCategory = :category")
    List<Competitor> findByRegionAndCategory(@Param("regionId") Long regionId,
                                             @Param("category") String category);

    @Query("SELECT COUNT(c) FROM Competitor c WHERE c.region.id = :regionId")
    Long countCompetitorsByRegion(@Param("regionId") Long regionId);

    @Query("SELECT c FROM Competitor c WHERE c.avgPricing BETWEEN :minPrice AND :maxPrice")
    List<Competitor> findByPricingRange(@Param("minPrice") Double minPrice,
                                        @Param("maxPrice") Double maxPrice);

    @Query("SELECT c FROM Competitor c WHERE c.name LIKE %:name%")
    List<Competitor> findByNameContaining(@Param("name") String name);
}
