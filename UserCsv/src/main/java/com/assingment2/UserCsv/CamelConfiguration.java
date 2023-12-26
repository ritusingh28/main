package com.assingment2.UserCsv;

import org.apache.camel.component.sql.SqlComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class CamelConfiguration {

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/user");
        dataSource.setUsername("postgres");
        dataSource.setPassword("Ritu");
        return dataSource;
    }

    @Bean
    public SqlComponent sqlComponent(DataSource dataSource) {
        SqlComponent sqlComponent = new SqlComponent();
        sqlComponent.setDataSource(dataSource);
        return sqlComponent;
    }
}



