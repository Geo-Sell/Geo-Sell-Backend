package com.adarsh.geosell.Service;
import com.adarsh.geosell.Entity.Insight;
import com.adarsh.geosell.Repository.InsightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InsightService {

    @Autowired
    private InsightRepository insightRepository;

    public List<Insight> getAllInsights() {
        return insightRepository.findAll();
    }

    public Optional<Insight> getInsightById(Long id) {
        return insightRepository.findById(id);
    }

    public Insight saveInsight(Insight insight) {
        return insightRepository.save(insight);
    }

    public void deleteInsight(Long id) {
        insightRepository.deleteById(id);
    }

    public List<Insight> getInsightsByRegion(Long regionId) {
        return insightRepository.findByRegionId(regionId);
    }

    public List<Insight> getInsightsByProduct(Long productId) {
        return insightRepository.findByProductId(productId);
    }

    public List<Insight> getInsightsByUser(Long userId) {
        return insightRepository.findByUserId(userId);
    }

    public List<Insight> getInsightsByRegionAndProduct(Long regionId, Long productId) {
        return insightRepository.findByRegionAndProduct(regionId, productId);
    }

    public List<Insight> getRecentInsights() {
        return insightRepository.findRecentInsights();
    }

    public List<Insight> getUserInsightsOrderByDate(Long userId) {
        return insightRepository.findUserInsightsOrderByDate(userId);
    }
}