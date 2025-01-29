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

import java.util.UUID;

public final class MemorystoreLoginUser {

    /** Replace the Memorystore instance id. */
    private static final String INSTANCE_ID = "INSTANCE_ID";

    /** Replace the Memorystore port, if not the default port. */
    private static final int PORT = 6379;

    /** User ID for login */
    private static final String USER_ID = "USERID";

    /** Session expiration time in seconds (30 minutes) */
    private static final int SESSION_TIMEOUT = 1800;

    private MemorystoreLoginUser() {
        // No-op; won't be called
    }

    /**
     * Logs in a user by creating a session in Memorystore.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        // Connect to the Memorystore instance
        JedisPool pool = new JedisPool(INSTANCE_ID, PORT);

        try (Jedis jedis = pool.getResource()) {
            String sessionKey = "session:" + USER_ID;

            // Check if the user is already logged in
            if (jedis.exists(sessionKey)) {
                System.out.printf("User %s is already logged in.%n", USER_ID);
                return;
            }

            // Generate a session token
            String sessionToken = UUID.randomUUID().toString();

            // Store the session token in Redis with an expiration time
            jedis.setex(sessionKey, SESSION_TIMEOUT, sessionToken);
            System.out.printf("User %s logged in with session: %s%n", USER_ID, sessionToken);
        } catch (Exception e) {
            System.err.printf("Error connecting to Redis: %s%n", e.getMessage());
        }
    }
}
