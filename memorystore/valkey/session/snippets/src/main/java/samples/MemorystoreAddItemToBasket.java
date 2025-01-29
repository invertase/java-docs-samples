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
 * WITHOUT WARRANTIES OR IMPLIED WARRANTIES OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public final class MemorystoreAddItemToBasket {

    /** Replace the Memorystore instance id. */
    private static final String INSTANCE_ID = "INSTANCE_ID";

    /** Replace the Memorystore port, if not the default port. */
    private static final int PORT = 6379;

    /** User ID for managing the basket */
    private static final String USER_ID = "USERID";

    /** Item to be added to the user's basket */
    private static final String ITEM_ID = "ITEM_ID";

    private MemorystoreAddItemToBasket() {
        // No-op; won't be called
    }

    /**
     * Adds an item to a user's basket in Memorystore.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        // Connect to the Memorystore instance
        JedisPool pool = new JedisPool(INSTANCE_ID, PORT);

        try (Jedis jedis = pool.getResource()) {
            String basketKey = "basket:" + USER_ID;

            // Add a single item to the user's basket
            jedis.sadd(basketKey, ITEM);
            System.out.printf("Added item to basket: %s%n", ITEM_ID);

            // Verify the item is in the basket
            boolean exists = jedis.sismember(basketKey, ITEM);
            if (exists) {
                System.out.printf("Item successfully added: %s%n", ITEM_ID);
            } else {
                System.out.printf("Failed to add item: %s%n", ITEM_ID);
            }
        } catch (Exception e) {
            System.err.printf("Error connecting to Redis: %s%n", e.getMessage());
        }
    }
}
