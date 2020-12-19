package org.legitzxdevelopment.user;

import org.legitzxdevelopment.encryption.AES;
import org.legitzxdevelopment.hashing.HashManager;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Contributor(s): Luciano K
 * Description: User POJO
 */
public class User {
    private String id; // Hashed masterpass:vaultkey
    private String email;
    private String iv; // Used for encryption
    private ArrayList<Account> accounts;

    public User(String email) {
        this.email = email;
        id = "";
        iv = "";
        accounts = new ArrayList<>();
    }

    public String getIV() {
        return iv;
    }

    public void setIV(String IV) {
        this.iv = IV;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
            return "[Error]: Account already exists";
        }

        accounts.add(account);

        return "[Success]";
    }

    public String updateAccount(Account account) {
        for(Account acc : accounts) {
            if(acc.getName().equals(account.getName())) {
                accounts.remove(acc); // Removes old account
                accounts.add(account); // Adds new account
                return "[Success]";
            }
        }
        return "[Error]: The account that you want to update does not exist";
    }

    public String deleteAccount(String name) {
        for(Account acc : accounts) {
            if(acc.getName().equals(name)) {
                accounts.remove(acc);
                return "[Success]";
            }
        }
        return "[Error]: Account does not exist with the name: " + name;
    }

    private boolean exists(Account account) {
        if(accounts != null) {
            for(Account acc : accounts) {
                if(acc.getName().equals(account.getName())) {
                    return true;
                }
            }
        }

        return false;
    }

    public User secure(HashManager hashManager, AES aes, String password) {
        System.out.println("[SECURE] Hashing and Encrypting before contacting server");

        String vaultKey = "";

        // Create ID if missing
        if(!getId().isEmpty()) {
            // we already have id
        } else {
            // Hash id
            vaultKey = hashManager.hashString(getEmail() + password);
            id = hashManager.hashString(password + vaultKey);
        }

        // Create vault key
        vaultKey = hashManager.hashString(getEmail() + password);

        // Hash email
        if (!getEmail().isEmpty()) {
            setEmail(hashManager.hashString(getEmail()));
        }


        // Manage IV
        if(getIV().isEmpty()) {
            byte[] IV = new byte[AES.GCM_IV_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(IV);

            setIV(Base64.getEncoder().encodeToString(IV));
        }


        // Encrypt Accounts
        if(getAccounts() != null) {
            for(Account acc : getAccounts()) {
                // Hash URL
                if(acc.getUrl() != null) {
                    if (!acc.getUrl().isEmpty()) {
                        String unencryptedUrl = acc.getUrl();
                        acc.setUrl(aes.encrypt(unencryptedUrl, vaultKey, getIV()));
                    }
                }

                // Hash Name
                if(acc.getName() != null) {
                    if (!acc.getName().isEmpty()) {
                        String unencryptedName = acc.getName();
                        acc.setName(aes.encrypt(unencryptedName, vaultKey, getIV()));
                    }
                }

                // Hash Username
                if(acc.getUsername() != null) {
                    if (!acc.getUsername().isEmpty()) {
                        String unencryptedUsername = acc.getUsername();
                        acc.setUsername(aes.encrypt(unencryptedUsername, vaultKey, getIV()));
                    }
                }

                // Hash Password
                if(acc.getPassword() != null) {
                    if (!acc.getPassword().isEmpty()) {
                        String unencryptedPass = acc.getPassword();
                        acc.setPassword(aes.encrypt(unencryptedPass, vaultKey, getIV()));
                    }
                }
            }
        }

        return this;
    }

    public User decrypt(AES aes, HashManager hashManager, String password) {
        String vaultKey = hashManager.hashString(getEmail() + password); // email and password is hashed so its different!!

        // Decrypt Accounts
        if(getAccounts() != null) {
            for(Account acc : getAccounts()) {
                // Hash URL
                if(acc.getUrl() != null) {
                    if (!acc.getUrl().isEmpty()) {
                        String encryptedUrl = acc.getUrl();
                        acc.setUrl(aes.decrypt(encryptedUrl, vaultKey, getIV()));
                    }
                }

                // Hash Name
                if(acc.getName() != null) {
                    if (!acc.getName().isEmpty()) {
                        String encryptedName = acc.getName();
                        acc.setName(aes.decrypt(encryptedName, vaultKey, getIV()));
                    }
                }

                // Hash Username
                if(acc.getUsername() != null) {
                    if (!acc.getUsername().isEmpty()) {
                        String encryptedUsername = acc.getUsername();
                        acc.setUsername(aes.decrypt(encryptedUsername, vaultKey, getIV()));
                    }
                }

                // Hash Password
                if(acc.getPassword() != null) {
                    if (!acc.getPassword().isEmpty()) {
                        String encryptedPass = acc.getPassword();
                        acc.setPassword(aes.decrypt(encryptedPass, vaultKey, getIV()));
                    }
                }
            }
        }

        return this;
    }
}
