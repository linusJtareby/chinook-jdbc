package com.chinook.chinookjdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChinookDAO {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public void testDataBaseConnectionI() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.print(String.format("\n>>> Connection to database %s\n", conn.getMetaData().getURL()));
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
}
