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

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public final class MemorystoreFilterByDesc {

  /** Replace the Memorystore instance id. */
  private static final String INSTANCE_ID = "INSTANCE_ID";

  /** Replace the Memorystore port, if not the default port. */
  private static final int PORT = 6379;

  /** Replace the id of the item to write to Memorystore. */
  private static final String TOKEN = "TOKEN";

  private MemorystoreFilterByDesc() {
    // No-op; won't be called
  }

  /**
   * Writes to Memorystore with a Time-to-live(TTL) value.
   *
   * @param args command-line arguments
   * @throws InterruptedException if the thread sleep is interrupted
   */
  public static void main(final String[] args) throws InterruptedException {
    // Connect to the Memorystore instance
    JedisPool pool = new JedisPool(INSTANCE_ID, PORT);

    try (Jedis jedis = pool.getResource()) {
      // Set a TTL of 10 seconds during entry creation
      final int ttlSeconds = 10;
      jedis.del(TOKEN);

      // Verify the user has been deleted
      System.out.println("Verifying cache.");
      String cachedUser = jedis.exist(TOKEN);

      // Print the cached item if found
      if (cachedUser != null) {
        System.out.printf("Found cached item: %s%n", cachedUser);
      }

      // Print the cached item not found
      if (cachedUser == null) {
        System.out.printf("No cached item found: %s%n", ITEM_ID);
      }
    } catch (Exception e) {
      // Print any errors found in the exception
      System.err.printf("Error connecting to Redis: %s%n", e.getMessage());
    }
  }
}
