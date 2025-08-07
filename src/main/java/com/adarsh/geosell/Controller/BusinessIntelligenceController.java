package com.adarsh.geosell.Controller;

import com.adarsh.geosell.Entity.*;
import com.adarsh.geosell.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/business-intelligence")
@CrossOrigin(origins = "*")
public class BusinessIntelligenceController {

    @Autowired
    private RegionService regionService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CompetitorService competitorService;

    @Autowired
    private InsightService insightService;

    @Autowired
    private AdCampaignService adCampaignService;

    // 🛒 WHAT TO SELL - Product Analysis
    @GetMapping(path = "/what-to-sell")
    public ResponseEntity<Map<String, Object>> getWhatToSellAnalysis(
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) String category) {

        Map<String, Object> analysis = new HashMap<>();

        // Get trending products
        List<Product> trendingProducts = productService.getTrendingProducts();
        analysis.put("trendingProducts", trendingProducts);

        // Get all categories
        List<String> categories = productService.getAllCategories();
        analysis.put("categories", categories);

        // Filter by region if provided
        if (regionId != null) {
            List<Product> regionProducts = productService.getProductsByRegion(regionId);
            analysis.put("regionProducts", regionProducts);
        }

        // Filter by category if provided
        if (category != null) {
            List<Product> categoryProducts = productService.getProductsByCategory(category);
            analysis.put("categoryProducts", categoryProducts);
        }

        // Combined filter
        if (regionId != null && category != null) {
            List<Product> filteredProducts = productService.getProductsByRegionAndCategory(regionId, category);
            analysis.put("filteredProducts", filteredProducts);
        }

        return ResponseEntity.ok(analysis);
    }

    // 📍 WHERE TO SELL - Region Analysis
    @GetMapping(path = "/where-to-sell")
    public ResponseEntity<Map<String, Object>> getWhereToSellAnalysis() {
        Map<String, Object> analysis = new HashMap<>();

        // Get top regions by demand
        List<Region> topRegions = regionService.getTopRegionsByDemand();
        analysis.put("topRegions", topRegions);

        // Get competitor counts by region
        Map<Long, Long> competitorCounts = new HashMap<>();
        for (Region region : topRegions) {
            Long count = competitorService.getCompetitorCountByRegion(region.getId());
            competitorCounts.put(region.getId(), count);
        }
        analysis.put("competitorCounts", competitorCounts);

        // Get regions with low competition (opportunity zones)
        List<Region> allRegions = regionService.getAllRegions();
        analysis.put("allRegions", allRegions);

        return ResponseEntity.ok(analysis);
    }

    // 🚀 HOW TO SELL - Strategy Analysis (General)
    @GetMapping(path = "/how-to-sell")
    public ResponseEntity<Map<String, Object>> getHowToSellAnalysisGeneral() {
        Map<String, Object> analysis = new HashMap<>();

        // Get high engagement campaigns for reference (using proper scale)
        List<AdCampaign> highEngagementCampaigns = adCampaignService.getHighEngagementCampaigns(7.0);
        analysis.put("successfulCampaigns", highEngagementCampaigns);

        // Get general insights
        analysis.put("generalInsights", insightService.getRecentInsights());
        analysis.put("allCompetitors", competitorService.getAllCompetitors());

        return ResponseEntity.ok(analysis);
    }

    // 🚀 HOW TO SELL - Strategy Analysis (Specific)
    @GetMapping(path = "/how-to-sell/specific")
    public ResponseEntity<Map<String, Object>> getHowToSellAnalysisSpecific(
            @RequestParam Long regionId,
            @RequestParam Long productId,
            @RequestParam(required = false) Long userId) {

        Map<String, Object> analysis = new HashMap<>();

        // Get AI insights for region and product
        List<Insight> insights = insightService.getInsightsByRegionAndProduct(regionId, productId);
        analysis.put("insights", insights);

        // Get competitor analysis
        List<Competitor> competitors = competitorService.getCompetitorsByRegion(regionId);
        analysis.put("competitors", competitors);

        // Get high engagement campaigns for reference
        List<AdCampaign> highEngagementCampaigns = adCampaignService.getHighEngagementCampaigns(7.0);
        analysis.put("successfulCampaigns", highEngagementCampaigns);

        // Get user-specific insights if userId provided
        if (userId != null) {
            List<Insight> userInsights = insightService.getUserInsightsOrderByDate(userId);
            analysis.put("userInsights", userInsights);
        }

        return ResponseEntity.ok(analysis);
    }

    // Dashboard Overview
    @GetMapping(path = "/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardOverview() {
        Map<String, Object> dashboard = new HashMap<>();

        // Key metrics
        long totalRegions = regionService.getAllRegions().size();
        long totalProducts = productService.getAllProducts().size();
        long totalCompetitors = competitorService.getAllCompetitors().size();
        long totalInsights = insightService.getAllInsights().size();

        Map<String, Long> metrics = new HashMap<>();
        metrics.put("totalRegions", totalRegions);
        metrics.put("totalProducts", totalProducts);
        metrics.put("totalCompetitors", totalCompetitors);
        metrics.put("totalInsights", totalInsights);

        dashboard.put("metrics", metrics);

        // Recent data
        dashboard.put("recentInsights", insightService.getRecentInsights());
        dashboard.put("trendingProducts", productService.getTrendingProducts());
        dashboard.put("topRegions", regionService.getTopRegionsByDemand());

        return ResponseEntity.ok(dashboard);
    }

    // Market Opportunity Analysis
    @GetMapping(path = "/market-opportunity")
    public ResponseEntity<Map<String, Object>> getMarketOpportunityAnalysis(
            @RequestParam String category) {

        Map<String, Object> analysis = new HashMap<>();

        // Get products in category
        List<Product> categoryProducts = productService.getProductsByCategory(category);
        analysis.put("categoryProducts", categoryProducts);

        // Get competitors in category
        List<Competitor> categoryCompetitors = competitorService.getCompetitorsByCategory(category);
        analysis.put("categoryCompetitors", categoryCompetitors);

        // Calculate opportunity score for each region
        Map<Long, Map<String, Object>> regionOpportunity = new HashMap<>();
        List<Region> allRegions = regionService.getAllRegions();

        for (Region region : allRegions) {
            Map<String, Object> opportunity = new HashMap<>();

            // Demand score
            opportunity.put("demandScore", region.getBusinessDemandScore());

            // Competition level
            Long competitorCount = competitorService.getCompetitorCountByRegion(region.getId());
            opportunity.put("competitorCount", competitorCount);

            // Opportunity score (high demand, low competition = high opportunity)
            Double opportunityScore = region.getBusinessDemandScore() - (competitorCount * 10);
            opportunity.put("opportunityScore", opportunityScore);

            regionOpportunity.put(region.getId(), opportunity);
        }

        analysis.put("regionOpportunity", regionOpportunity);

        return ResponseEntity.ok(analysis);
    }

    // Add a test endpoint to verify the controller is working
    @GetMapping(path = "/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("BusinessIntelligenceController is working!");
    }
}