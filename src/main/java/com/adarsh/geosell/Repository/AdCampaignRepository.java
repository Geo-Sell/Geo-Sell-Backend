package com.adarsh.geosell.Repository;
import com.adarsh.geosell.Entity.AdCampaign;
import com.adarsh.geosell.Enum.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdCampaignRepository extends JpaRepository<AdCampaign, Long> {

    List<AdCampaign> findByCompetitorId(Long competitorId);

    List<AdCampaign> findByPlatform(Platform platform);

    @Query("SELECT a FROM AdCampaign a WHERE a.engagement > :minEngagement ORDER BY a.engagement DESC")
    List<AdCampaign> findHighEngagementCampaigns(@Param("minEngagement") Double minEngagement);

    @Query("SELECT a FROM AdCampaign a WHERE a.competitor.id = :competitorId AND a.platform = :platform")
    List<AdCampaign> findByCompetitorAndPlatform(@Param("competitorId") Long competitorId,
                                                 @Param("platform") Platform platform);
}