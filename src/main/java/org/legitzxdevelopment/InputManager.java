package org.legitzxdevelopment;

import org.legitzxdevelopment.api.PasswordManagerApi;
import org.legitzxdevelopment.user.Account;
import org.legitzxdevelopment.user.User;
import org.legitzxdevelopment.util.Util;

import java.util.ArrayList;

/**
 * Contributor(s): Luciano K
 * Description: Handles interactions with the user
 */
public class InputManager {
    private PasswordManager main;

    private Util util;
    private PasswordManagerApi passwordManagerApi;

    public InputManager(PasswordManager main) {
        this.main = main;

        util = main.getUtil();
        passwordManagerApi = main.getPasswordManagerApi();
    }

    public void handle() {
        util.clearWindow();

        System.out.println("---Welcome to the Password Manager---");
        System.out.println("Are you logging in or creating a new account?");
        System.out.println("1: Logging in");
        System.out.println("2: Create a new account");

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
        System.out.println("-=[Register]=-");

        String email = util.getUserInput("Enter Email: ", String.class);
        String password = util.getUserInput("Enter Password: ", String.class);

        if(util.isNotEmpty(email, password)) {
            User user = new User(email);

            // Database
            String result = passwordManagerApi.register(user, password);
            System.out.println("[Password Manager Server] " + result);
        }

        handle();
    }

    private void login() {
        util.clearWindow();
        System.out.println("-=[Login]=-");

        String email = util.getUserInput("Enter Email: ", String.class);
        String password = util.getUserInput("Enter Password: ", String.class);

        if(util.isNotEmpty(email, password)) {
            User user = new User(email);

            // Database
            boolean authenticated = passwordManagerApi.login(user, password);
            if(authenticated) {
                System.out.println("[Password Manager Server] Authenticated! Token Valid for 15 Minutes");
                main(user, password, true);
            } else {
                System.out.println("[Password Manager Server] Invalid Credentials");
            }
        }

        handle();
    }

    public void main(User loginObject, String password, boolean first) {
        // Contact Server for User Object - Only use after login
        User user = loginObject;
        if(first) {
            user = passwordManagerApi.get(loginObject, password);
        }

        // Prevents future NullPointers
        if(user.getAccounts() == null) {
            user.setAccounts(new ArrayList<>());
        }

        System.out.println("-=[Welcome]=-");
        System.out.println("1. Add New Account");
        System.out.println("2. View Accounts");
        System.out.println("3. Save & Logout (requires password for verification)");

        int option = util.getUserInput("[Password Manager]: ", Integer.class);

        if(option == 1) {
            String accUrl = util.getUserInput("Enter URL: ", String.class);
            String accName = util.getUserInput("Enter Name of Website: ", String.class);
            String accUsername = util.getUserInput("Enter Username/Email: ", String.class);
            String accPassword = util.getUserInput("Enter Password: ", String.class);

            Account newAcc = new Account(accUrl, accName, accUsername, accPassword);

            user.addAccount(newAcc);

            System.out.println("[Password Manager] Added Account");
            main(user, password, false);
        } else if (option == 2) {
            System.out.println("--------------------------------------------------");
            for(Account c : user.getAccounts()) {
                System.out.println("URL: " + c.getUrl());
                System.out.println("Name: " + c.getName());
                System.out.println("Username : " + c.getUsername());
                System.out.println("Password: " + c.getPassword());
                System.out.println("--------------------------------------------------");
            }

            main(user, password, false);
        } else if (option == 3) {
            System.out.println(passwordManagerApi.update(user, password));
            handle();
        } else if (option == 4) {
            // ...
        } else {
            System.out.println("Invalid");
            main(loginObject, password, false);
        }
    }
}
