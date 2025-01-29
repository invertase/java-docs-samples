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

public final class MemorystoreRemoveItemFromBasket {

    /** Replace the Memorystore instance id. */
    private static final String INSTANCE_ID = "INSTANCE_ID";

    /** Replace the Memorystore port, if not the default port. */
    private static final int PORT = 6379;

    /** User ID for managing the basket */
    private static final String USER_ID = "USERID";

    /** Item to be removed from the user's basket */
    private static final String ITEM_ID = "ITEM_ID";

    private MemorystoreRemoveItemFromBasket() {
        // No-op; won't be called
    }

    /**
     * Removes an item from a user's basket in Memorystore.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        // Connect to the Memorystore instance
        JedisPool pool = new JedisPool(INSTANCE_ID, PORT);

        try (Jedis jedis = pool.getResource()) {
            String basketKey = "basket:" + USER_ID;

            // Remove the item from the user's basket
            long removed = jedis.srem(basketKey, ITEM_ID);

            // Check if the item was successfully removed
            if (removed > 0) {
                System.out.printf("Removed item from basket: %s%n", ITEM_ID);
            } else {
                System.out.printf("Item not found in basket: %s%n", ITEM_ID);
            }
        } catch (Exception e) {
            System.err.printf("Error connecting to Redis: %s%n", e.getMessage());
        }
    }
}
