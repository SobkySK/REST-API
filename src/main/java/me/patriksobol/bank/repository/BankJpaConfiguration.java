package me.patriksobol.bank.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(
        dateTimeProviderRef = "databaseDateTimeProvider"
)
public class BankJpaConfiguration {

    @Bean
    public DateTimeProvider databaseDateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now());
    }
}
