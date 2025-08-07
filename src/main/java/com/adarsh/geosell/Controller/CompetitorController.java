package com.adarsh.geosell.Controller;
import com.adarsh.geosell.Entity.Competitor;
import com.adarsh.geosell.Service.CompetitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/competitors")
@CrossOrigin(origins = "*")
public class CompetitorController {

    @Autowired
    private CompetitorService competitorService;

    @GetMapping
    public ResponseEntity<List<Competitor>> getAllCompetitors() {
        List<Competitor> competitors = competitorService.getAllCompetitors();
        return ResponseEntity.ok(competitors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competitor> getCompetitorById(@PathVariable Long id) {
        Optional<Competitor> competitor = competitorService.getCompetitorById(id);
        return competitor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Competitor> createCompetitor(@RequestBody Competitor competitor) {
        Competitor savedCompetitor = competitorService.saveCompetitor(competitor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompetitor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competitor> updateCompetitor(@PathVariable Long id, @RequestBody Competitor competitor) {
        Optional<Competitor> existingCompetitor = competitorService.getCompetitorById(id);
        if (existingCompetitor.isPresent()) {
            competitor.setId(id);
            Competitor updatedCompetitor = competitorService.saveCompetitor(competitor);
            return ResponseEntity.ok(updatedCompetitor);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetitor(@PathVariable Long id) {
        competitorService.deleteCompetitor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<Competitor>> getCompetitorsByRegion(@PathVariable Long regionId) {
        List<Competitor> competitors = competitorService.getCompetitorsByRegion(regionId);
        return ResponseEntity.ok(competitors);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Competitor>> getCompetitorsByCategory(@PathVariable String category) {
        List<Competitor> competitors = competitorService.getCompetitorsByCategory(category);
        return ResponseEntity.ok(competitors);
    }

    @GetMapping("/region/{regionId}/category/{category}")
    public ResponseEntity<List<Competitor>> getCompetitorsByRegionAndCategory(
            @PathVariable Long regionId,
            @PathVariable String category) {
        List<Competitor> competitors = competitorService.getCompetitorsByRegionAndCategory(regionId, category);
        return ResponseEntity.ok(competitors);
    }

    @GetMapping("/count/region/{regionId}")
    public ResponseEntity<Long> getCompetitorCountByRegion(@PathVariable Long regionId) {
        Long count = competitorService.getCompetitorCountByRegion(regionId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/pricing-range")
    public ResponseEntity<List<Competitor>> getCompetitorsByPricing(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        List<Competitor> competitors = competitorService.getCompetitorsByPricing(minPrice, maxPrice);
        return ResponseEntity.ok(competitors);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Competitor>> searchCompetitorsByName(@RequestParam String name) {
        List<Competitor> competitors = competitorService.searchCompetitorsByName(name);
        return ResponseEntity.ok(competitors);
    }
}