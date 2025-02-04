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
 * Configuration for the Jedis client to connect to the Valkey server.
 */
package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class JedisConfig {

  /**
   * The minimum port number.
   */
  private static final int MIN_PORT = 1;
  /**
   * The maximum port number.
   */
  private static final int MAX_PORT = 65535;

  /**
   * The host of the Valkey server.
   * If not set, defaults to the provided value.
   */
  @Value("${VALKEY_HOST:localhost}")
  private String redisHost;

  /**
   * The port of the Valkey server.
   * If not set, defaults to the provided value.
   */
  @Value("${VALKEY_PORT:6379}")
  private int redisPort;

  /**
   * The password to connect to the Valkey server.
   * If not set, defaults to an empty string.
   */
  @Value("${VALKEY_PASSWORD:}")
  private String redisPassword;

  /**
   * Creates a new Jedis bean to connect to the Valkey server.
   *
   * @return the Jedis bean
   */
  @Bean
  public Jedis jedis() {
    // Validate mandatory properties
    if (redisHost == null || redisHost.isEmpty()) {
      throw new IllegalArgumentException(
          "Redis host (VALKEY_HOST) is not configured");
    }
    if (redisPort < MIN_PORT || redisPort > MAX_PORT) {
      throw new IllegalArgumentException("Redis port (VALKEY_PORT) is invalid");
    }

    Jedis jedis = new Jedis(redisHost, redisPort);

    // Authenticate if a password is set
    if (!redisPassword.isEmpty()) {
      jedis.auth(redisPassword);
    }

    // Verify the connection to the Redis server
    try {
      jedis.ping();
    } catch (Exception e) {
      throw new RuntimeException(
          "Failed to connect to Redis server at " + redisHost + ":" + redisPort,
          e);
    }

    return jedis;
  }
}
