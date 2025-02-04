/**
 * Serve the home page, login page, and register page at the specified routes.
 */
package app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public final class HomeController {

  /**
   * Serves the home page.
   *
   * @param model
   * @return the home page
   */
  @GetMapping("/")
  public String home(final Model model) {
    return "index"; // Refers to templates/index.html
  }

  /**
   * Serves the login page.
   *
   * @param model
   * @return the login page
   */
  @GetMapping("/login")
  public String login(final Model model) {
    return "login"; // Refers to templates/login.html
  }

  /**
   * Serves the register page.
   *
   * @param model
   * @return the register page
   */
  @GetMapping("/register")
  public String logout(final Model model) {
    return "register"; // Refers to templates/register.html
  }
}
