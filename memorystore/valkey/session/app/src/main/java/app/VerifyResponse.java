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
 * This class is used to create a response object for the verify endpoint.
 * It contains the username and expiration timestamp of the token.
 */
package app;

import org.json.JSONObject;

public final class VerifyResponse {

  /**
   * The username of the token.
   */
  private final String username;

  /**
   * The expiration time of the token.
   */
  private final int expirationSecs;

  /**
   * Creates a new VerifyResponse with the given username and expiration time.
   *
   * @param usernameParam
   * @param expirationParam
   */
  public VerifyResponse(final String usernameParam, final int expirationParam) {
    username = usernameParam;
    expirationSecs = expirationParam;
  }

  /**
   * Converts the VerifyResponse to a JSON object.
   *
   * @return the JSON object
   */
  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("username", username);
    json.put("expirationSecs", expirationSecs);
    return json;
  }
}
