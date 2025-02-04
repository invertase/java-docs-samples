/**
 * Data class for holding login information.
 */
package app;

public final class LoginInfo {

  /**
   * The username of the login request.
   */
  private final String username;
  /**
   * The password of the login request.
   */
  private final String password;

  /**
   * Creates a new LoginInfo with the given username and password.
   *
   * @param usernameParam
   * @param passwordParam
   */
  public LoginInfo(final String usernameParam, final String passwordParam) {
    username = usernameParam;
    password = passwordParam;
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
