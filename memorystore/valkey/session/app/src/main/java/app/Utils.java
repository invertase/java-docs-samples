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
 * Utility class for generating secure tokens and managing cookies.
 */
package app;

import jakarta.servlet.http.Cookie;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Base64;

public final class Utils {

  /**
   * The number of milliseconds in a second.
   */
  private static final int MILLIS_IN_SECOND = 1000;

  /**
   * Private constructor to prevent instantiation.
   */
  private Utils() {
  }

  /**
   * Generates a secure token of the specified length.
   *
   * @param tokenByteLength
   * @return the generated token
   */
  public static String generateToken(final int tokenByteLength) {
    // SecureRandom ensures cryptographic security
    SecureRandom secureRandom = new SecureRandom();
    byte[] randomBytes = new byte[tokenByteLength];
    secureRandom.nextBytes(randomBytes);

    // Encode the random bytes into a URL-safe Base64 string
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
  }

  /**
   * Extracts the token from the cookies.
   *
   * @param cookies
   * @return the token if found, null otherwise
   */
  public static String getTokenFromCookie(final Cookie[] cookies) {
    if (cookies == null) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(Global.TOKEN_COOKIE_NAME)) {
        return cookie.getValue();
      }
    }
    return null;
  }

  /**
   * Creates a cookie with the specified token.
   *
   * @param token
   * @return the created cookie
   */
  public static Cookie createCookie(final String token) {
    Cookie cookie = new Cookie(Global.TOKEN_COOKIE_NAME, token);
    cookie.setPath("/"); // Available across the app
    cookie.setMaxAge(Global.TOKEN_EXPIRATION); // Set expiration

    return cookie;
  }

  /**
   * Gets a future timestamp from the current time, given an additional
   * number of seconds.
   *
   * @param seconds
   * @return the future timestamp
   */
  public static Timestamp getFutureTimestamp(final long seconds) {
    long currentTime = System.currentTimeMillis();
    long futureTime = currentTime + (seconds * MILLIS_IN_SECOND);
    return new Timestamp(futureTime);
  }
}
