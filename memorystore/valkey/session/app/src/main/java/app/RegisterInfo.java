/**
 * Data class for holding registration information.
 */
package app;

public final class RegisterInfo {

  /**
   * The email of the registration request.
   */
  private final String email;
  /**
   * The username of the registration request.
   */
  private final String username;
  /**
   * The password of the registration request.
   */
  private final String password;

  /**
   * Creates a new RegisterInfo with the given email, username, and password.
   *
   * @param emailParam
   * @param usernameParam
   * @param passwordParam
   */
  public RegisterInfo(
      final String emailParam,
      final String usernameParam,
      final String passwordParam) {
    email = emailParam;
    username = usernameParam;
    password = passwordParam;
  }

  /**
   * Gets the email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets the username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }
}
