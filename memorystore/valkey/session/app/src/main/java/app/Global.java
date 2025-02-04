/**
 * Global constants for the application.
 */
package app;

public final class Global {

    /**
     * Private constructor to prevent instantiation.
     */
    private Global() {
    }

    /**
     * Invalid credentials message.
     */
    public static final String INVALID_CREDENTIALS =
        "Invalid username or password";

    /**
     * Invalid token message.
     */
    public static final String INVALID_TOKEN = "Invalid token";

    /**
     * User registered successfully message.
     */
    public static final String REGISTERED = "User registered successfully";

    /**
     * User already registered message.
     */
    public static final String EMAIL_INVALID = "Invalid email format";

    /**
     * Email already registered message.
     */
    public static final String EMAIL_ALREADY_REGISTERED =
        "Email is already registered";

    /**
     * Username invalid message.
     */
    public static final String USERNAME_INVALID =
        "Username must only contain letters, numbers, periods, underscores, "
            + "and hyphens";

    /**
     * Username length message.
     */
    public static final String USERNAME_LENGTH =
        "Username must be between 3 and 20 characters";

    /**
     * Password length message.
     */
    public static final String USERNAME_TAKEN = "Username is already taken";

    /**
     * Password length message.
     */
    public static final String PASSWORD_LENGTH =
        "Password must be between 8 and 255 characters";

    /**
     * Logged in message.
     */
    public static final String LOGGED_IN = "Logged in";

    /**
     * Logged out message.
     */
    public static final String LOGGED_OUT = "Logged out";

    /**
     * Token byte length. 128 bytes is 256 characters.
     * By having a longer token, we can increase security by
     * making it harder for attackers to guess the token, and reduce
     * the likelihood of collisions. 128 bytes is a common length for
     * secure tokens.
     */
    public static final Integer TOKEN_BYTE_LENGTH = 128;

    /**
     * Token expiration time in seconds (30 minutes).
     */
    public static final Integer TOKEN_EXPIRATION = 1800;

    /**
     * Token cookie name.
     */
    public static final String TOKEN_COOKIE_NAME = "token";
}
