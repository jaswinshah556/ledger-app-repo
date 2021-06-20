package com.app.ledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
/**
 * @author jaswin.shah
 * @version $Id: LenderApplication.java, v 0.1 2021-06-21 05:49 AM jaswin.shah Exp $$
 */
@Configuration
@SpringBootApplication
public class LenderApplication {

    /**
     * Recon app main method startup method
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(LenderApplication.class, args);
    }

}
