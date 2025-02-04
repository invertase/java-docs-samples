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
 * Responsible for handling the data operations between the API, Valkey,
 * and the database.
 */
package app;

import org.springframework.stereotype.Controller;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Controller
public final class DataController {

  /**
   * Account repository for database operations on accounts table.
   */
  private final AccountRepository accountRepository;

  /**
   * Valkey client for token operations.
   */
  private final Jedis jedis;

  /**
   * Constructs a new DataController with the given account repository and
   * Valkey client.
   *
   * @param accountRepositoryParam the account repository
   * @param jedisParam             the Valkey client
   */
  public DataController(
      final AccountRepository accountRepositoryParam,
      final Jedis jedisParam) {
    accountRepository = accountRepositoryParam;
    jedis = jedisParam;
  }

  /**
   * Registers a new user with the given email, username, and password.
   *
   * @param email    the email of the user
   * @param username the username of the user
   * @param password the password of the user
   */
  public void register(
      final String email,
      final String username,
      final String password) {
    accountRepository.registerUser(email, username, password);
  }

  /**
   * Logs in a user with the given username and password.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return the token for the user
   */
  public String login(final String username, final String password) {
    // Authenticate user
    Optional<Integer> userId = accountRepository.authenticateUser(
        username, password);

    // No user found
    if (userId.isEmpty()) {
      return null;
    }

    // Generate token for the user
    String token = Utils.generateToken(Global.TOKEN_BYTE_LENGTH);

    // Store token in cache
    jedis.setex(token, Global.TOKEN_EXPIRATION, username);

    return token;
  }

  /**
   * Logs out a user with the given token.
   *
   * @param token the token of the user
   */
  public void logout(final String token) {
    jedis.del(token);
  }

  /**
   * Verifies a user with the given token.
   *
   * @param token the token of the user
   * @return the username of the user
   */
  public String verify(final String token) {
    // Retrieve username from Valkey
    String username = jedis.get(token);

    // No username found for the token
    if (username == null) {
      return null;
    }

    // Extend token expiration
    jedis.expire(token, Global.TOKEN_EXPIRATION);

    return username;
  }

  /**
   * Checks if the given email is already registered.
   *
   * @param email the email to check
   * @return true if the email is already registered, false otherwise
   */
  public boolean checkIfEmailExists(final String email) {
    return accountRepository.isEmailRegistered(email);
  }

  /**
   * Checks if the given username is already registered.
   *
   * @param username the username to check
   * @return true if the username is already registered, false otherwise
   */
  public boolean checkIfUsernameExists(final String username) {
    return accountRepository.isUsernameRegistered(username);
  }
}
