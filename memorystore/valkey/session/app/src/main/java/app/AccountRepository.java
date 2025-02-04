/**
 * Handles CRUD operations for the account table.
 */
package app;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class AccountRepository {

  /**
   * The JdbcTemplate instance.
   */
  private final JdbcTemplate jdbcTemplate;

  /**
   * Constructor.
   *
   * @param jdbcTemplateParam
   */
  public AccountRepository(final JdbcTemplate jdbcTemplateParam) {
    jdbcTemplate = jdbcTemplateParam;
  }

  /**
   * Authenticates a user by checking the username and password
   * in the database.
   *
   * @param username
   * @param password
   * @return the userId if authentication is successful, empty otherwise
   */
  public Optional<Integer> authenticateUser(final String username,
      final String password) {
    try {
      // Fetch hashedPassword and userId in a single query
      Map<String, Object> accountData = jdbcTemplate.queryForMap(
          "SELECT id, password FROM account WHERE username = ?",
          username);

      String hashedPassword = (String) accountData.get("password");
      Integer userId = (Integer) accountData.get("id");

      // Check password validity
      if (hashedPassword != null && BCrypt.checkpw(password, hashedPassword)) {
        return Optional.of(userId); // Authentication successful
      } else {
        return Optional.empty(); // Authentication failed
      }
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty(); // No user found
    }
  }

  /**
   * Registers a new user in the database.
   *
   * @param email
   * @param username
   * @param password
   */
  public void registerUser(final String email, final String username,
      final String password) {
    // Validate input
    if (email == null || username == null || password == null) {
      throw new IllegalArgumentException(
          "Email, username, and password must not be null");
    }

    // Hash the password to securely store it
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

    // Insert user into the database
    jdbcTemplate.update(
        "INSERT INTO account (email, username, password) VALUES (?, ?, ?)",
        email,
        username,
        hashedPassword);
  }

  /**
   * Checks if an email is already registered in the database.
   *
   * @param email
   * @return true if the email is already registered, false otherwise
   */
  public boolean isEmailRegistered(final String email) {
    String sql = "SELECT EXISTS (SELECT 1 FROM account WHERE email = ?)";
    return Boolean.TRUE.equals(
        jdbcTemplate.queryForObject(sql, Boolean.class, email));
  }

  /**
   * Checks if a username is already registered in the database.
   *
   * @param username
   * @return true if the username is already registered, false otherwise
   */
  public boolean isUsernameRegistered(final String username) {
    String sql = "SELECT EXISTS (SELECT 1 FROM account WHERE username = ?)";
    return Boolean.TRUE.equals(
        jdbcTemplate.queryForObject(sql, Boolean.class, username));
  }
}
