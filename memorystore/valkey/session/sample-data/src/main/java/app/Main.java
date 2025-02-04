package app;

import com.github.javafaker.Faker;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Main {

  /**
   * The maximum number of generated entries.
   */
  private static final int MAX_GENERATED_ENTRIES = 15000;

  /**
   * The maximum length of the username.
   */
  private static final int MAX_USERNAME_LENGTH = 20;

  /**
   * The expiry time for the session in milliseconds.
   */
  private static final long EXPIRY_TIME = 3600000;

  /**
   * The sleep time in milliseconds after an exception occurs.
   */
  private static final long EXCEPTION_SLEEP_TIME = 5000;

  /**
   * The Faker instance for generating fake data.
   */
  private static final Faker FAKER = new Faker();

  /**
   * The Random instance for generating random numbers.
   */
  private static final Random RANDOM = new Random();

  /**
   * Private constructor to prevent instantiation.
   */
  private Main() {
  }

  /**
   * Main method to populate the leaderboard with test data.
   *
   * @param args the command line arguments
   */
  public static void main(final String[] args) {
    // Connect to PostgreSQL
    System.out.println("Connecting to PostgreSQL...");
    JdbcTemplate jdbcTemplate = configureJdbcTemplate();

    // Populate leaderboard with test data
    try {
      System.out.println("Populating accounts...");
      populateAccounts(jdbcTemplate);
    } catch (CannotGetJdbcConnectionException e) {
      System.out.println(
          "Failed to connect to the database. Retrying in 5 seconds...");
      // Sleep for 5 seconds and retry
      try {
        Thread.sleep(EXCEPTION_SLEEP_TIME);
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
      main(args);
    }
  }

  /**
   * Populates the accounts table with test data.
   *
   * @param jdbcTemplate the JDBC template
   */
  private static void populateAccounts(final JdbcTemplate jdbcTemplate) {
    String sql =
        "INSERT INTO account (email, username, password) VALUES (?, ?, ?)";

    // Prepare batch arguments
    List<Object[]> batchArgs = new ArrayList<>();
    for (int i = 0; i < MAX_GENERATED_ENTRIES; i++) {
      String email = FAKER.internet().emailAddress();
      String username = FAKER.name().username();
      username = username.length() > MAX_USERNAME_LENGTH
          ? username.substring(0, MAX_USERNAME_LENGTH)
          : username;
      String password = FAKER.internet().password();

      batchArgs.add(new Object[] {email, username, password});
    }

    // Execute batch update
    jdbcTemplate.batchUpdate(sql, batchArgs);
  }

  /**
   * Configures the JDBC template with the database connection details.
   *
   * @return the configured JDBC template
   */
  private static JdbcTemplate configureJdbcTemplate() {
    String jdbcUrl = System.getenv()
        .getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/postgres");
    String jdbcUsername = System.getenv().getOrDefault("DB_USERNAME", "root");
    String jdbcPassword = System.getenv()
        .getOrDefault("DB_PASSWORD", "password");

    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    jdbcTemplate.setDataSource(
        DataSourceBuilder.create()
            .url(jdbcUrl)
            .username(jdbcUsername)
            .password(jdbcPassword)
            .build());
    return jdbcTemplate;
  }
}
