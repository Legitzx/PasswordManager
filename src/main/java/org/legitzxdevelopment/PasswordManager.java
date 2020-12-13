package org.legitzxdevelopment;

import org.legitzxdevelopment.hashing.BCrypt;

/**
 * Contributor(s): Luciano K
 * Description:
 */
public class PasswordManager {
    public static void main(String[] args) {
        String originalPassword = "Thet0234)A0234A)$#@a";
        String salt = BCrypt.gensalt(16);
        String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, salt);

        System.out.println(generatedSecuredPasswordHash);

        System.out.println(BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash));

        String s = BCrypt.hashpw(originalPassword, generatedSecuredPasswordHash);
        System.out.println(s);
    }
}
