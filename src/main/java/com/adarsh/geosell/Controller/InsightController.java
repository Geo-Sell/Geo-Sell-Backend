package com.adarsh.geosell.Controller;
import com.adarsh.geosell.Entity.Insight;
import com.adarsh.geosell.Service.InsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/insights")
@CrossOrigin(origins = "*")
public class InsightController {

    @Autowired
    private InsightService insightService;

    @GetMapping
    public ResponseEntity<List<Insight>> getAllInsights() {
        List<Insight> insights = insightService.getAllInsights();
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insight> getInsightById(@PathVariable Long id) {
        Optional<Insight> insight = insightService.getInsightById(id);
        return insight.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Insight> createInsight(@RequestBody Insight insight) {
        Insight savedInsight = insightService.saveInsight(insight);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInsight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Insight> updateInsight(@PathVariable Long id, @RequestBody Insight insight) {
        Optional<Insight> existingInsight = insightService.getInsightById(id);
        if (existingInsight.isPresent()) {
            insight.setId(id);
            Insight updatedInsight = insightService.saveInsight(insight);
            return ResponseEntity.ok(updatedInsight);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsight(@PathVariable Long id) {
        insightService.deleteInsight(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<Insight>> getInsightsByRegion(@PathVariable Long regionId) {
        List<Insight> insights = insightService.getInsightsByRegion(regionId);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Insight>> getInsightsByProduct(@PathVariable Long productId) {
        List<Insight> insights = insightService.getInsightsByProduct(productId);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Insight>> getInsightsByUser(@PathVariable Long userId) {
        List<Insight> insights = insightService.getInsightsByUser(userId);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/region/{regionId}/product/{productId}")
    public ResponseEntity<List<Insight>> getInsightsByRegionAndProduct(
            @PathVariable Long regionId,
            @PathVariable Long productId) {
        List<Insight> insights = insightService.getInsightsByRegionAndProduct(regionId, productId);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Insight>> getRecentInsights() {
        List<Insight> insights = insightService.getRecentInsights();
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/user/{userId}/ordered")
    public ResponseEntity<List<Insight>> getUserInsightsOrderByDate(@PathVariable Long userId) {
        List<Insight> insights = insightService.getUserInsightsOrderByDate(userId);
        return ResponseEntity.ok(insights);
    }
}
