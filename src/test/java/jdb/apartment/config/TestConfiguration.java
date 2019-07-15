package jdb.apartment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile({"apitests", "unittests"})
public class TestConfiguration {
  // EmbeddedDatabaseBuilder is a builder that provides a convenient API for
  // constructing an embedded database.
  @Bean
  public EmbeddedDatabase mySqlDataSource(){
    return new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
        .addScript("/sql/Apartment.sql").build();
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
    return new NamedParameterJdbcTemplate(mySqlDataSource());
  }
}
