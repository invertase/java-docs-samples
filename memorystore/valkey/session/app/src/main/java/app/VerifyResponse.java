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
