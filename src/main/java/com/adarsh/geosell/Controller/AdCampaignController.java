package com.adarsh.geosell.Controller;

import com.adarsh.geosell.Entity.AdCampaign;
import com.adarsh.geosell.Entity.Competitor;
import com.adarsh.geosell.DTO.AdCampaignDTO;
import com.adarsh.geosell.Enum.Platform;
import com.adarsh.geosell.Service.AdCampaignService;
import com.adarsh.geosell.Service.CompetitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "*")
public class AdCampaignController {

    @Autowired
    private AdCampaignService adCampaignService;

    @Autowired
    private CompetitorService competitorService; // Add this injection

    @GetMapping
    public ResponseEntity<List<AdCampaignDTO>> getAllCampaigns() {
        List<AdCampaign> campaigns = adCampaignService.getAllCampaigns();
        List<AdCampaignDTO> campaignDTOs = campaigns.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(campaignDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdCampaignDTO> getCampaignById(@PathVariable Long id) {
        Optional<AdCampaign> campaign = adCampaignService.getCampaignById(id);
        return campaign.map(c -> ResponseEntity.ok(convertToDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdCampaignDTO> createCampaign(@RequestBody AdCampaignDTO campaignDTO) {
        AdCampaign campaign = convertToEntity(campaignDTO);
        AdCampaign savedCampaign = adCampaignService.saveCampaign(campaign);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedCampaign));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdCampaignDTO> updateCampaign(@PathVariable Long id, @RequestBody AdCampaignDTO campaignDTO) {
        Optional<AdCampaign> existingCampaign = adCampaignService.getCampaignById(id);
        if (existingCampaign.isPresent()) {
            AdCampaign campaign = convertToEntity(campaignDTO);
            campaign.setId(id);
            AdCampaign updatedCampaign = adCampaignService.saveCampaign(campaign);
            return ResponseEntity.ok(convertToDTO(updatedCampaign));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        adCampaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/competitor/{competitorId}")
    public ResponseEntity<List<AdCampaignDTO>> getCampaignsByCompetitor(@PathVariable Long competitorId) {
        List<AdCampaign> campaigns = adCampaignService.getCampaignsByCompetitor(competitorId);
        List<AdCampaignDTO> campaignDTOs = campaigns.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(campaignDTOs);
    }

    @GetMapping("/platform/{platform}")
    public ResponseEntity<List<AdCampaignDTO>> getCampaignsByPlatform(@PathVariable Platform platform) {
        List<AdCampaign> campaigns = adCampaignService.getCampaignsByPlatform(platform);
        List<AdCampaignDTO> campaignDTOs = campaigns.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(campaignDTOs);
    }

    @GetMapping("/high-engagement")
    public ResponseEntity<List<AdCampaignDTO>> getHighEngagementCampaigns(@RequestParam Double minEngagement) {
        List<AdCampaign> campaigns = adCampaignService.getHighEngagementCampaigns(minEngagement);
        List<AdCampaignDTO> campaignDTOs = campaigns.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(campaignDTOs);
    }

    @GetMapping("/competitor/{competitorId}/platform/{platform}")
    public ResponseEntity<List<AdCampaignDTO>> getCampaignsByCompetitorAndPlatform(
            @PathVariable Long competitorId,
            @PathVariable Platform platform) {
        List<AdCampaign> campaigns = adCampaignService.getCampaignsByCompetitorAndPlatform(competitorId, platform);
        List<AdCampaignDTO> campaignDTOs = campaigns.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(campaignDTOs);
    }

    private AdCampaignDTO convertToDTO(AdCampaign campaign) {
        AdCampaignDTO dto = new AdCampaignDTO();
        dto.setId(campaign.getId());
        dto.setPlatform(campaign.getPlatform());
        dto.setAdText(campaign.getAdText());
        dto.setTargetAudience(campaign.getTargetAudience());
        dto.setEngagement(campaign.getEngagement());

        // Add competitor info without circular reference
        if (campaign.getCompetitor() != null) {
            dto.setCompetitorId(campaign.getCompetitor().getId());
            dto.setCompetitorName(campaign.getCompetitor().getName());
        }

        return dto;
    }

    private AdCampaign convertToEntity(AdCampaignDTO dto) {
        AdCampaign campaign = new AdCampaign();
        campaign.setAdText(dto.getAdText());
        campaign.setPlatform(dto.getPlatform());
        campaign.setTargetAudience(dto.getTargetAudience());
        campaign.setEngagement(dto.getEngagement());

        // Set competitor if provided - FIXED
        if (dto.getCompetitorId() != null) {
            Optional<Competitor> competitor = competitorService.getCompetitorById(dto.getCompetitorId());
            competitor.ifPresent(campaign::setCompetitor);
        }

        return campaign;
    }
}