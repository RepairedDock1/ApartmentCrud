package jdb.apartment.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/* @Configuration Indicates that a class declares one or more @Bean methods and may be processed by
   the Spring container to generate bean definitions and service requests for those beans
   at runtime */
@Configuration
@Profile("default")
public class JdbcConfig {
  private String MYSQL_URL = "jdbc:mysql://localhost:3306/APT?serverTimezone=UTC";
  private String MY_SQL_USERNAME = "root";
  private String MY_SQL_PASSWORD = "123456789";
  private String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";

  @Bean
  public DataSource mySqlDataSource() {
    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(MYSQL_URL,
        MY_SQL_USERNAME, MY_SQL_PASSWORD);
    driverManagerDataSource.setDriverClassName(MYSQL_DRIVER);

    return driverManagerDataSource;
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
    return new NamedParameterJdbcTemplate(mySqlDataSource());
  }
}
