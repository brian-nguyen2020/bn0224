package com.diy.rental;

import com.diy.rental.repository.MockDatabase;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToolsRentalApplication {


    public static void main(String[] args) {
        SpringApplication.run(com.diy.rental.ToolsRentalApplication.class, args);
    }

    @PostConstruct
    public void init() {
        MockDatabase.createDataBaseRecords();
    }


}