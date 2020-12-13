package org.legitzxdevelopment.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * Contributor(s): Luciano K
 * Description:
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
    public String encrypt(String data, String key) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(AES_KEY_SIZE);

            setKey(key);

            byte[] IV = new byte[GCM_IV_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(IV);

            byte[] cipherText = encryptBase(data.getBytes(), secretKey, IV);

            return Base64.getEncoder().encodeToString(cipherText) + "<IV>" + Base64.getEncoder().encodeToString(IV);
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
