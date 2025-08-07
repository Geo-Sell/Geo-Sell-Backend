package com.adarsh.geosell.Service;
import com.adarsh.geosell.Entity.AdCampaign;
import com.adarsh.geosell.Enum.Platform;
import com.adarsh.geosell.Repository.AdCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdCampaignService {

    @Autowired
    private AdCampaignRepository adCampaignRepository;

    public List<AdCampaign> getAllCampaigns() {
        return adCampaignRepository.findAll();
    }

    public Optional<AdCampaign> getCampaignById(Long id) {
        return adCampaignRepository.findById(id);
    }

    public AdCampaign saveCampaign(AdCampaign campaign) {
        return adCampaignRepository.save(campaign);
    }

    public void deleteCampaign(Long id) {
        adCampaignRepository.deleteById(id);
    }

    public List<AdCampaign> getCampaignsByCompetitor(Long competitorId) {
        return adCampaignRepository.findByCompetitorId(competitorId);
    }

    public List<AdCampaign> getCampaignsByPlatform(Platform platform) {
        return adCampaignRepository.findByPlatform(platform);
    }

    public List<AdCampaign> getHighEngagementCampaigns(Double minEngagement) {
        return adCampaignRepository.findHighEngagementCampaigns(minEngagement);
    }

    public List<AdCampaign> getCampaignsByCompetitorAndPlatform(Long competitorId, Platform platform) {
        return adCampaignRepository.findByCompetitorAndPlatform(competitorId, platform);
    }
}