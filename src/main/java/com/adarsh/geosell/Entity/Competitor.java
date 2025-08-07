package com.adarsh.geosell.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "competitors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Competitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    @JsonBackReference(value = "region-competitors") // Added proper back reference
    private Region region;

    @Column(nullable = false, length = 100)
    private String productCategory;

    @Column(nullable = false)
    private Double avgPricing;

    @Column(columnDefinition = "TEXT")
    private String strategyNotes;

    @Column(length = 100)
    private String source;

    @OneToMany(mappedBy = "competitor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Avoid deep nesting in JSON responses
    private List<AdCampaign> adCampaigns;
}