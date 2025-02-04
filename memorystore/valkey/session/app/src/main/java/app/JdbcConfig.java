/**
 * Configuration for the JDBC DataSource to connect to the PostgreSQL server.
 */
package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

  /**
   * The URL of the PostgreSQL server.
   * If not set, defaults to provided value.
   */
  @Value("${DB_URL:jdbc:postgresql://localhost:5432/default_db}")
  private String url;

  /**
   * The username to connect to the PostgreSQL server.
   * If not set, defaults to provided value.
   */
  @Value("${DB_USERNAME:postgres}")
  private String username;

  /**
   * The password to connect to the PostgreSQL server.
   * If not set, defaults to provided value.
   */
  @Value("${DB_PASSWORD:}")
  private String password;

  /**
   * Creates a new DataSource bean to connect to the PostgreSQL server.
   *
   * @return the DataSource bean
   */
  @Bean
  public DataSource dataSource() {
    // Validate mandatory properties
    if (url == null || url.isEmpty()) {
      throw new IllegalArgumentException(
          "Database URL (DB_URL) is not configured");
    }
    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException(
          "Database username (DB_USERNAME) is not configured");
    }

    // Set up the DataSource
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);

    return dataSource;
  }
}
