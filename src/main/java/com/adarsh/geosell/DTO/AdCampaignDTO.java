package com.adarsh.geosell.DTO;
import com.adarsh.geosell.Enum.Platform;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCampaignDTO {
    private Long id;
    private Platform platform;
    private String adText;
    private String targetAudience;
    private Double engagement;

    // Competitor info without circular reference
    private Long competitorId;
    private String competitorName;
}