package org.legitzxdevelopment;

import org.legitzxdevelopment.util.Util;

/**
 * Contributor(s): Luciano K
 * Description: Main
 */
public class PasswordManager {
    public static void main(String[] args) {
        // Util Dependency
        Util util = new Util();

        // Input Manager
        InputManager inputManager = new InputManager(util);

        // Handle user input
        inputManager.handle();
    }
}
