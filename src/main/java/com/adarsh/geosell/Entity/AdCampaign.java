package com.adarsh.geosell.Entity;
import com.adarsh.geosell.Enum.Platform;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ad_campaigns")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AdCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competitor_id", nullable = false)
    @JsonBackReference(value = "competitor-campaigns")
    private Competitor competitor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String adText;

    @Column(nullable = false, length = 100)
    private String targetAudience;

    @Column(nullable = false)
    private Double engagement; // Engagement rate percentage
}

