package com.flaco.skateschool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.flaco.skateschool.repository")
@EnableJpaAuditing
@EnableTransactionManagement
public class JpaConfig {
}

// This configuration class sets up JPA repositories,
// enables auditing for JPA entities
// and activates transaction management for this application.
