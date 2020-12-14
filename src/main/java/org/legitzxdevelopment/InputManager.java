package org.legitzxdevelopment;

import org.legitzxdevelopment.util.Util;

/**
 * Contributor(s): Luciano K
 * Description: Handles interactions with the user
 */
public class InputManager {
    private Util util;

    public InputManager(Util util) {
        this.util = util;
    }

    public void handle() {
        util.clearWindow();

        System.out.println("---Welcome to the Password Manager---");
        System.out.println("Are you logging in or creating a new account?");
        System.out.println("1: Logging in");
        System.out.println("2: Creating new account");

        int option = util.getUserInput("[Password Manager]: ", Integer.class);

        if(option == 1) {
            login();
        } else if (option == 2) {
            registerAccount();
        } else {
            System.out.println("Invalid");
            handle();
        }
    }

    private void registerAccount() {
        util.clearWindow();
        System.out.println("REGISTER");

        String email = util.getUserInput("Enter Email: ", String.class);
        String password = util.getUserInput("Enter Password: ", String.class);

        // Database register method
    }

    private void login() {
        util.clearWindow();
        System.out.println("LOGIN");

        String email = util.getUserInput("Enter Email: ", String.class);
        String password = util.getUserInput("Enter Password: ", String.class);

        // Database login method

        boolean authenticated = false; // Not yet implemented

        if(authenticated) {
            main();
        } else {
            System.out.println("Invalid Credentials");
            login();
        }
    }

    public void main() {
        System.out.println("WELCOME");
        System.out.println("1. Add new account");
        System.out.println("2. Obtain account");
        System.out.println("3. Save");
        System.out.println("4. Logout");

        int option = util.getUserInput("[Password Manager]: ", Integer.class);

        if(option == 1) {
            // ...
        } else if (option == 2) {
            // ...
        } else if (option == 3) {
            // ...
        } else if (option == 4) {
            // ...
        } else {
            System.out.println("Invalid");
            main();
        }
    }
}
