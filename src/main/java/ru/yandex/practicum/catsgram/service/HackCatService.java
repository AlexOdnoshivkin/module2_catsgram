package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class HackCatService {

    private Logger log = LoggerFactory.getLogger(getClass());

    public static final String JDBC_URL="jdbc:postgresql://127.0.0.1:5432/cats";
    public static final String JDBC_USERNAME="kitty";
    public static final String JDBC_DRIVER="org.postgresql.Driver";

    public void tryPassword(String jdbcPassword) {
        DriverManagerDataSource dataSourceConst = new DriverManagerDataSource();
        // проверьте подключение с паролем и параметрами БД
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(JDBC_USERNAME);
        dataSource.setPassword(jdbcPassword);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("SELECT 1;");
    }

    public void doHackNow(){
        List<String> catWordList = Arrays.asList("meow", "purr", "purrrrrr", "zzz");
        // проверьте пароли из списка
        for (String password : catWordList) {
            tryPassword(password);
        }
    }
}