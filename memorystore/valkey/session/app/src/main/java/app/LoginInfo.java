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
