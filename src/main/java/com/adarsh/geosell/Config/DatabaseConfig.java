package com.adarsh.geosell.Config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.adarsh.geosell.repository")
public class DatabaseConfig {
    // Additional database configuration if needed
}
