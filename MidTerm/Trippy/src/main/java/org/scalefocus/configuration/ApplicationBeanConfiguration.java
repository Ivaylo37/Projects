package org.scalefocus.configuration;

import org.scalefocus.util.db.DBConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public DBConnector dbConnector(){
        return new DBConnector();
    }
}
