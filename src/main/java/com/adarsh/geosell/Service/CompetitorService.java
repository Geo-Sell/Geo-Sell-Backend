package com.adarsh.geosell.Service;
import com.adarsh.geosell.Entity.Competitor;
import com.adarsh.geosell.Repository.CompetitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompetitorService {

    @Autowired
    private CompetitorRepository competitorRepository;

    public List<Competitor> getAllCompetitors() {
        return competitorRepository.findAll();
    }

    public Optional<Competitor> getCompetitorById(Long id) {
        return competitorRepository.findById(id);
    }

    public Competitor saveCompetitor(Competitor competitor) {
        return competitorRepository.save(competitor);
    }

    public void deleteCompetitor(Long id) {
        competitorRepository.deleteById(id);
    }

    public List<Competitor> getCompetitorsByRegion(Long regionId) {
        return competitorRepository.findByRegionId(regionId);
    }

    public List<Competitor> getCompetitorsByCategory(String category) {
        return competitorRepository.findByProductCategory(category);
    }

    public List<Competitor> getCompetitorsByRegionAndCategory(Long regionId, String category) {
        return competitorRepository.findByRegionAndCategory(regionId, category);
    }

    public Long getCompetitorCountByRegion(Long regionId) {
        return competitorRepository.countCompetitorsByRegion(regionId);
    }

    public List<Competitor> getCompetitorsByPricing(Double minPrice, Double maxPrice) {
        return competitorRepository.findByPricingRange(minPrice, maxPrice);
    }

    public List<Competitor> searchCompetitorsByName(String name) {
        return competitorRepository.findByNameContaining(name);
    }
}