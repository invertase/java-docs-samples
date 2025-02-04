/**
 * The Auth controller for the application.
 *
 * <p>
 *   The controller contains the following endpoints:
 *   - GET /api/basket - Get all items
 *   - POST /api/basket/add - Add item with quantity
 *   - POST /api/basket/remove - Remove item quantity
 *   - POST /api/basket/clear - Clear entire basket
 */
package app;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import java.util.Map;

@RestController
@RequestMapping("/api/basket")
public final class BasketController {

  /**
   * The Valkey client for token operations.
   */
  private final Jedis jedis;

  /**
   * Constructs a new BasketController with the given Valkey client.
   *
   * @param jedisParam the Valkey client
   */
  public BasketController(final Jedis jedisParam) {
    jedis = jedisParam;
  }

  /**
   * Get all items in the basket.
   *
   * @param request the HTTP request
   * @return the items in the basket
   */
  @GetMapping
  public ResponseEntity<Map<String, String>> getBasket(
      final HttpServletRequest request) {
    String basketKey = getBasketKey(request);
    return ResponseEntity.ok(jedis.hgetAll(basketKey));
  }

  /**
   * Add item with quantity.
   *
   * @param itemId   the item ID
   * @param quantity the quantity
   * @param request  the HTTP request
   * @return the response entity
   */
  @PostMapping("/add")
  public ResponseEntity<String> addItem(
      @RequestParam final String itemId,
      @RequestParam(defaultValue = "1") final int quantity,
      final HttpServletRequest request) {
    String basketKey = getBasketKey(request);
    long newQty = jedis.hincrBy(basketKey, itemId, quantity);
    return ResponseEntity.ok("Quantity updated: " + newQty);
  }

  /**
   * Remove item quantity.
   *
   * @param itemId   the item ID
   * @param quantity the quantity
   * @param request  the HTTP request
   * @return the response entity
   */
  @PostMapping("/remove")
  public ResponseEntity<String> removeItem(
      @RequestParam final String itemId,
      @RequestParam(defaultValue = "1") final int quantity,
      final HttpServletRequest request) {
    String basketKey = getBasketKey(request);
    long newQty = jedis.hincrBy(basketKey, itemId, -quantity);
    if (newQty <= 0) {
      jedis.hdel(basketKey, itemId);
      return ResponseEntity.ok("Item removed");
    }
    return ResponseEntity.ok("Quantity updated: " + newQty);
  }

  /**
   * Clear entire basket.
   *
   * @param request the HTTP request
   * @return the response entity
   */
  @PostMapping("/clear")
  public ResponseEntity<String> clearBasket(final HttpServletRequest request) {
    jedis.del(getBasketKey(request));
    return ResponseEntity.ok("Basket cleared");
  }

  private String getBasketKey(final HttpServletRequest request) {
    String token = Utils.getTokenFromCookie(request.getCookies());
    return "basket:" + token;
  }
}
