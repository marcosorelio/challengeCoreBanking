package org.orelio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the ChallengeCoreBanking system.
 * 
 * <p>This is the entry point for the Spring Boot application that provides
 * a RESTful API for core banking operations including deposits, withdrawals,
 * and transfers between accounts.</p>
 * 
 * <p>The application runs on port 8000 by default and provides the following endpoints:</p>
 * <ul>
 *   <li>{@code POST /reset} - Reset all accounts</li>
 *   <li>{@code GET /balance?account_id={id}} - Get account balance</li>
 *   <li>{@code POST /event} - Perform banking operations</li>
 * </ul>
 * 
 * <p>The application uses an in-memory storage system and includes an H2 database
 * console accessible at {@code http://localhost:8000/h2-console}.</p>
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 1.0
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 */
@SpringBootApplication
public class Application {
    
    /**
     * Main method to start the Spring Boot application.
     * 
     * <p>This method initializes the Spring Boot application context and starts
     * the embedded web server. The application will be accessible at
     * {@code http://localhost:8000} by default.</p>
     * 
     * @param args command line arguments passed to the application
     * @see org.springframework.boot.SpringApplication#run(Class, String...)
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
