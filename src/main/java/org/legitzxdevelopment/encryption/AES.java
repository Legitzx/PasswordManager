package org.legitzxdevelopment.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Contributor(s): Luciano K
 * Description: Utilizes the AES-256 GCM Algorithm
 */
public class AES {
    public final int AES_KEY_SIZE = 256;
    public static final int GCM_IV_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 16;

    private SecretKeySpec secretKey;
    private byte[] key;

    /**
     * Sets the key
     * @param myKey     key
     */
    private void setKey(String myKey) {
        MessageDigest sha = null;

        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encrypts Data
     * @param data      Plain text data
     * @param key       Vault Key
     * @return          AES-256 GCM Encrypted Data - [Format: DATA<IV>IV] - <IV> is a separator, not data
     */
    public String encrypt(String data, String key, String IV) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(AES_KEY_SIZE);

            setKey(key);

            byte[] cipherText = encryptBase(data.getBytes(), secretKey, Base64.getDecoder().decode(IV));

            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Decrypts Data
     * @param data      Encrypted Data
     * @param key       Vault Key
     * @param IV        Initialization Vector
     * @return          Decrypted Data
     */
    public String decrypt(String data, String key, String IV) {
        setKey(key);
        try {
            return decryptBase(Base64.getDecoder().decode(data), secretKey, Base64.getDecoder().decode(IV));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Encrypts data in AES-256 GCM
     */
    private byte[] encryptBase(byte[] plaintext, SecretKey key, byte[] IV) throws Exception {
        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Encryption
        byte[] cipherText = cipher.doFinal(plaintext);

        return cipherText;
    }

    /**
     * Decrypts AES-256 GCM Data
     */
    private String decryptBase(byte[] cipherText, SecretKey key, byte[] IV) throws Exception {
        // Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // Create GCMParameterSpec
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Decryption
        byte[] decryptedText = cipher.doFinal(cipherText);

        return new String(decryptedText);
    }
}

/*
// TODO: USE SAME IV FOR ENCRYPTING ENTIRE ACCOUNT - PROB GONNA HAVE TO ALTER encrypt/decrypt methods
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
 */