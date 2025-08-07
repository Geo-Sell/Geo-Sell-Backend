package com.adarsh.geosell.Controller;
import com.adarsh.geosell.Entity.Region;
import com.adarsh.geosell.Service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/regions")
@CrossOrigin(origins = "*")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> getAllRegions() {
        List<Region> regions = regionService.getAllRegions();
        return ResponseEntity.ok(regions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable Long id) {
        Optional<Region> region = regionService.getRegionById(id);
        return region.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Region> createRegion(@RequestBody Region region) {
        Region savedRegion = regionService.saveRegion(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable Long id, @RequestBody Region region) {
        Optional<Region> existingRegion = regionService.getRegionById(id);
        if (existingRegion.isPresent()) {
            region.setId(id);
            Region updatedRegion = regionService.saveRegion(region);
            return ResponseEntity.ok(updatedRegion);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        regionService.deleteRegion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Region>> getRegionsByState(@PathVariable String state) {
        List<Region> regions = regionService.getRegionsByState(state);
        return ResponseEntity.ok(regions);
    }

    @GetMapping("/top-demand")
    public ResponseEntity<List<Region>> getTopRegionsByDemand() {
        List<Region> regions = regionService.getTopRegionsByDemand();
        return ResponseEntity.ok(regions);
    }

    @GetMapping("/demand-range")
    public ResponseEntity<List<Region>> getRegionsByDemandRange(
            @RequestParam Double minScore,
            @RequestParam Double maxScore) {
        List<Region> regions = regionService.getRegionsByDemandRange(minScore, maxScore);
        return ResponseEntity.ok(regions);
    }

    @GetMapping("/pincode/{pinCode}")
    public ResponseEntity<Region> getRegionByPinCode(@PathVariable String pinCode) {
        Optional<Region> region = regionService.getRegionByPinCode(pinCode);
        return region.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Region>> searchRegionsByName(@RequestParam String name) {
        List<Region> regions = regionService.searchRegionsByName(name);
        return ResponseEntity.ok(regions);
    }
}

