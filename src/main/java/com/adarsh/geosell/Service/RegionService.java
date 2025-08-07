package com.adarsh.geosell.Service;
import com.adarsh.geosell.Entity.Region;
import com.adarsh.geosell.Repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    public Optional<Region> getRegionById(Long id) {
        return regionRepository.findById(id);
    }

    public Region saveRegion(Region region) {
        return regionRepository.save(region);
    }

    public void deleteRegion(Long id) {
        regionRepository.deleteById(id);
    }

    public List<Region> getRegionsByState(String state) {
        return regionRepository.findByState(state);
    }

    public List<Region> getTopRegionsByDemand() {
        return regionRepository.findTopRegionsByDemandScore();
    }

    public List<Region> getRegionsByDemandRange(Double minScore, Double maxScore) {
        return regionRepository.findRegionsByDemandScoreRange(minScore, maxScore);
    }

    public Optional<Region> getRegionByPinCode(String pinCode) {
        return regionRepository.findByPinCode(pinCode);
    }

    public List<Region> searchRegionsByName(String name) {
        return regionRepository.findByNameContaining(name);
    }
}
