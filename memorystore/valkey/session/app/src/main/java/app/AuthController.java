/*
 * Copyright 2025 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * The Auth controller for the application.
 *
 * <p>
 *   The controller contains the following endpoints:
 *   - POST /auth/register - Registers a new user
 *   - POST /auth/login - Logs in a user
 *   - POST /auth/logout - Logs out a user
 *   - POST /auth/verify - Verifies a user's token
 */
package app;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public final class AuthController {

  /**
   * The minimum length of the username.
   */
  private static final int USERNAME_MIN_LENGTH = 3;
  /**
   * The maximum length of the username.
   */
  private static final int USERNAME_MAX_LENGTH = 20;
  /**
   * The minimum length of the password.
   */
  private static final int PASSWORD_MIN_LENGTH = 8;
  /**
   * The maximum length of the password.
   */
  private static final int PASSWORD_MAX_LENGTH = 255;

  /**
   * The data controller to interact with the database.
   */
  private final DataController dataController;

  /**
   * Creates a new AuthController with the given DataController.
   *
   * @param dataControllerParam
   */
  public AuthController(final DataController dataControllerParam) {
    dataController = dataControllerParam;
  }

  /**
   * Registers a new user when '/auth/register' is POSTed to.
   *
   * @param info
   * @return ResponseEntity with the result of the registration
   */
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody final RegisterInfo info) {
    String email = info.getEmail();
    String username = info.getUsername();
    String password = info.getPassword();

    // Validate email
    if (!email.matches(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
      return ResponseEntity.badRequest().body(Global.EMAIL_INVALID);
    }

    // Validate username
    if (!username.matches("^[a-zA-Z0-9._-]+$")) {
      return ResponseEntity.badRequest().body(Global.USERNAME_INVALID);
    } else if (username.length() < USERNAME_MIN_LENGTH
        || username.length() > USERNAME_MAX_LENGTH) {
      return ResponseEntity.badRequest().body(Global.USERNAME_LENGTH);
    }

    // Validate password
    if (password.length() < PASSWORD_MIN_LENGTH
        || password.length() > PASSWORD_MAX_LENGTH) {
      return ResponseEntity.badRequest().body(Global.PASSWORD_LENGTH);
    }

    // Check if email or username is already taken
    if (dataController.checkIfEmailExists(email)) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(Global.EMAIL_ALREADY_REGISTERED);
    }
    if (dataController.checkIfUsernameExists(username)) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(Global.USERNAME_TAKEN);
    }

    // Register user
    dataController.register(email, username, password);
    return ResponseEntity.ok(Global.REGISTERED);
  }

  /**
   * Logs in a user when '/auth/login' is POSTed to.
   *
   * @param info
   * @param response
   * @return ResponseEntity with the result of the login
   */
  @PostMapping("/login")
  public ResponseEntity<String> login(
      @RequestBody final LoginInfo info,
      final HttpServletResponse response) {
    String username = info.getUsername();
    String password = info.getPassword();

    // Attempt to log in
    String token = dataController.login(username, password);

    // Invalid credentials
    if (token == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Global.INVALID_CREDENTIALS);
    }

    // Create and set a cookie
    response.addCookie(Utils.createCookie(token));
    return ResponseEntity.ok(Global.LOGGED_IN);
  }

  /**
   * Logs out a user when '/auth/logout' is POSTed to.
   *
   * @param request
   * @return ResponseEntity with the result of the logout
   */
  @PostMapping("/logout")
  public ResponseEntity<String> logout(final HttpServletRequest request) {
    String token = Utils.getTokenFromCookie(request.getCookies());
    if (token == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Global.INVALID_TOKEN);
    }

    // Logout user
    dataController.logout(token);

    return ResponseEntity.ok(Global.LOGGED_OUT);
  }

  /**
   * Verifies a user's token when '/auth/verify' is POSTed to.
   *
   * @param request
   * @param response
   * @return ResponseEntity with the result of the verification
   */
  @PostMapping("/verify")
  public ResponseEntity<String> verify(
      final HttpServletRequest request,
      final HttpServletResponse response) {
    String token = Utils.getTokenFromCookie(request.getCookies());
    if (token == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Global.INVALID_TOKEN);
    }

    // Verify token and extend session
    String username = dataController.verify(token);
    if (username == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Global.INVALID_TOKEN);
    }

    // Refresh cookie expiration
    Cookie cookie = Utils.createCookie(token);
    response.addCookie(cookie);
    return ResponseEntity.ok(
        new VerifyResponse(username, cookie.getMaxAge()).toJson().toString());
  }
}
