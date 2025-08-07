package com.adarsh.geosell.Repository;
import com.adarsh.geosell.Entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    List<Region> findByState(String state);

    List<Region> findByBusinessDemandScoreGreaterThan(Double score);

    @Query("SELECT r FROM Region r ORDER BY r.businessDemandScore DESC")
    List<Region> findTopRegionsByDemandScore();

    @Query("SELECT r FROM Region r WHERE r.businessDemandScore BETWEEN :minScore AND :maxScore")
    List<Region> findRegionsByDemandScoreRange(@Param("minScore") Double minScore,
                                               @Param("maxScore") Double maxScore);

    Optional<Region> findByPinCode(String pinCode);

    @Query("SELECT r FROM Region r WHERE r.name LIKE %:name%")
    List<Region> findByNameContaining(@Param("name") String name);
}
