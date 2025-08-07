package com.adarsh.geosell.Repository;
import com.adarsh.geosell.Entity.User;

import com.adarsh.geosell.Enum.BusinessType;
import com.adarsh.geosell.Enum.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByBusinessType(BusinessType businessType);

    List<User> findBySubscriptionType(SubscriptionType subscriptionType);

    @Query("SELECT COUNT(u) FROM User u WHERE u.subscriptionType = :subscriptionType")
    Long countBySubscriptionType(SubscriptionType subscriptionType);

    boolean existsByEmail(String email);
}
