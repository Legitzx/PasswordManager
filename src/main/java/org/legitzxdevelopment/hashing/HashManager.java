package org.legitzxdevelopment.hashing;

/**
 * Contributor(s): Luciano K
 * Description: Provides methods that allow easy access to Bcrypt
 */
public class HashManager {
    private final String SALT = "$2a$12$ioWUm.jsGFPqR4m7yrUHI."; // I know using one salt for everything is not the best... This is just to get the ball rolling
    public String hashString(String input) {
        return BCrypt.hashpw(input, SALT);
    }

    private boolean check(String originalPassword, String hashedPassword) {
        return BCrypt.checkpw(originalPassword, hashedPassword);
    }
}
