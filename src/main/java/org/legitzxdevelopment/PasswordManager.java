package org.legitzxdevelopment;

import org.legitzxdevelopment.api.PasswordManagerApi;
import org.legitzxdevelopment.encryption.AES;
import org.legitzxdevelopment.hashing.HashManager;
import org.legitzxdevelopment.util.Util;

/**
 * Contributor(s): Luciano K
 * Description: Main
 */
public class PasswordManager {
    // Dependencies
    private InputManager inputManager;
    private HashManager hashManager;
    private AES aes;
    private Util util;
    private PasswordManagerApi passwordManagerApi;

    public PasswordManager() {
        // Util Dependency
        util = new Util();

        // Hash Manager
        hashManager = new HashManager();

        // AES Encryption
        aes = new AES();

        // Password Manager Api
        passwordManagerApi = new PasswordManagerApi(this);

        // Input Manager
        inputManager = new InputManager(this);

        // Handle user input
        inputManager.handle();
    }

    public Util getUtil() {
        return util;
    }

    public PasswordManagerApi getPasswordManagerApi() {
        return passwordManagerApi;
    }

    public HashManager getHashManager() {
        return hashManager;
    }

    public AES getAes() {
        return aes;
    }

    public static void main(String[] args) {
        new PasswordManager();
    }
}
