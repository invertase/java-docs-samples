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
