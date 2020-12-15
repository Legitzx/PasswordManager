package org.legitzxdevelopment.user;

import java.util.ArrayList;

/**
 * Contributor(s): Luciano K
 * Description: User POJO
 */
public class User {
    private String vaultKey; // Hashed masterpass:vaultkey
    private String email;
    private ArrayList<Account> accounts;

    public User(String vaultKey, String email, ArrayList<Account> accounts) {
        this.vaultKey = vaultKey;
        this.email = email;
        this.accounts = accounts;
    }

    public String getVaultKey() {
        return vaultKey;
    }

    public void setVaultKey(String vaultKey) {
        this.vaultKey = vaultKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public String addAccount(Account account) {
        if(exists(account)) {
            return "Account already exists.";
        }
        accounts.add(account);

        return "Success";
    }

    public String updateAccount(Account account) {
        for(Account acc : accounts) {
            if(acc.getName().equals(account.getName())) {
                accounts.remove(acc); // Removes old account
                accounts.add(account); // Adds new account
                return "Success";
            }
        }
        return "The account that you want to update does not exist";
    }

    public String deleteAccount(String name) {
        for(Account acc : accounts) {
            if(acc.getName().equals(name)) {
                accounts.remove(acc);
                return "Success";
            }
        }
        return "Error: Account does not exist with the name: " + name;
    }

    private boolean exists(Account account) {
        for(Account acc: accounts) {
            if(acc.getName().equals(account.getName())) {
                return true;
            }
        }
        return false;
    }
}
