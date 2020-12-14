package org.legitzxdevelopment.hashing;

/**
 * Contributor(s): Luciano K
 * Description: Provides methods that allow easy access to Bcrypt
 */
public class HashManager {
    public String hash(String input) {
        String salt = BCrypt.gensalt(16);
        return BCrypt.hashpw(input, salt);
    }

    public boolean check(String originalPassword, String hashedPassword) {
        return BCrypt.checkpw(originalPassword, hashedPassword);
    }
}
