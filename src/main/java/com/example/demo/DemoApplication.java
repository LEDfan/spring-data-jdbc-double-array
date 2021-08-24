package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private MyEntityRepository myEntityRepository;

    @Bean
    public DataSource dataSource() {
        String url = "jdbc:postgresql://localhost:5432/demo";
        String username = "demo";
        String password = "demo";

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        var entity = new MyEntity(1L, new double[]{1.0, 2.0, 4.0});
        myEntityRepository.save(entity);

        // workaround: 
//        jdbcTemplate.update("INSERT INTO myentity (id, \"values\") VALUES (?, ?)", entity.getId(), entity.getValues());
    }
}
