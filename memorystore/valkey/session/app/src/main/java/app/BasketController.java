/**
 * The Auth controller for the application.
 *
 * The controller contains the following endpoints:
 * - GET /api/basket - Gets the basket for the user
 * - POST /api/basket - Updates the basket for the user
 */

package app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

  private final Jedis jedis;

  public BasketController(Jedis jedis) {
    this.jedis = jedis;
  }

  @GetMapping
  public ResponseEntity<String> get(HttpServletRequest request) {
    String token = Utils.getTokenFromCookie(request.getCookies());
    return ResponseEntity.ok(jedis.get("basket-" + token));
  }

  @PostMapping
  public ResponseEntity<String> update(
    @RequestBody(required = false) String items,
    HttpServletRequest request
  ) {
    // Prevent the basket from getting too large to prevent abuse
    if (items != null && items.length() > 1000) {
      return ResponseEntity.badRequest().body("Basket too large");
    }

    String token = Utils.getTokenFromCookie(request.getCookies());
    jedis.set("basket-" + token, items == null ? "" : items);
    return ResponseEntity.ok("Basket updated");
  }
}
