package org.legitzxdevelopment;

import org.legitzxdevelopment.encryption.AES;
import org.legitzxdevelopment.util.Util;

/**
 * Contributor(s): Luciano K
 * Description: Main
 */
public class PasswordManager {
    public static void main(String[] args) {
//        // Util Dependency
//        Util util = new Util();
//
//        // Input Manager
//        InputManager inputManager = new InputManager(util);
//
//        // Handle user input
//        inputManager.handle();
        AES aes = new AES();
        String data = "YAY IM GOING to get ENCRYPTED!!!";
        String key = "XdSXg*ELq%}D,k9='nZ?Y[pT)?N2wv_-}_Ja^Ek!>gG'@@}KGX89MPB{aWP$J9K";

        String encryptedData = aes.encrypt(data, key);

        System.out.println(encryptedData);

        int encIndex = encryptedData.indexOf("<IV>");
        System.out.println(encIndex);
        String encryptedDataNoIV = encryptedData.substring(0,encIndex);
        String IV = encryptedData.substring(encIndex + 4);

        System.out.println("DATA: " + encryptedDataNoIV);
        System.out.println("IV: " + IV);

        String decrypted = aes.decrypt(encryptedDataNoIV, key, IV);

        System.out.println("Decrypted: " + decrypted);


    }
}
