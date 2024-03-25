package uz.studentsproject.config;

import org.hashids.Hashids;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashidsConfig {

    @Bean
    public Hashids hashids() {
        return new Hashids("this is my salt", 8);
    }
}
