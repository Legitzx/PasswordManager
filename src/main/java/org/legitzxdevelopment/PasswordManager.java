package org.legitzxdevelopment;

import org.legitzxdevelopment.encryption.AES;
import org.legitzxdevelopment.hashing.HashManager;
import org.legitzxdevelopment.util.Util;

/**
 * Contributor(s): Luciano K
 * Description: Main
 */
public class PasswordManager {
    // Dependencies
    private static InputManager inputManager;
    private static HashManager hashManager;
    private static AES aes;

    public PasswordManager() {
        // Util Dependency
        Util util = new Util();

        // Input Manager
        inputManager = new InputManager(util);

        // Hash Manager
        hashManager = new HashManager();

        // AES Encryption
        aes = new AES();

        // Handle user input
        inputManager.handle();
    }

    public static void main(String[] args) {
        new PasswordManager();
    }
}
