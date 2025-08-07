package com.adarsh.geosell.Repository;
import com.adarsh.geosell.Entity.Insight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InsightRepository extends JpaRepository<Insight, Long> {

    List<Insight> findByRegionId(Long regionId);

    List<Insight> findByProductId(Long productId);

    List<Insight> findByUserId(Long userId);

    @Query("SELECT i FROM Insight i WHERE i.region.id = :regionId AND i.product.id = :productId")
    List<Insight> findByRegionAndProduct(@Param("regionId") Long regionId,
                                         @Param("productId") Long productId);

    @Query("SELECT i FROM Insight i ORDER BY i.createdAt DESC")
    List<Insight> findRecentInsights();

    @Query("SELECT i FROM Insight i WHERE i.user.id = :userId ORDER BY i.createdAt DESC")
    List<Insight> findUserInsightsOrderByDate(@Param("userId") Long userId);
}
