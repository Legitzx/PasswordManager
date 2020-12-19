package org.legitzxdevelopment.api;

import com.google.gson.Gson;
import org.legitzxdevelopment.PasswordManager;
import org.legitzxdevelopment.user.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Contributor(s): Luciano K
 * Description: Interacts with the Password Manager API
 *
 * @website [https://github.com/Legitzx/PasswordManagerApi]
 */
public class PasswordManagerApi {
    // Main
    private PasswordManager main;
    private static String auth = "";

    public PasswordManagerApi(PasswordManager main) {
        this.main = main;
    }

    public boolean login(User user, String password) {
        String result = "";

        try {

            // We need email and password
            URL url = new URL("http://127.0.0.1:8080/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            Gson gson = new Gson();
            String json = gson.toJson(user.secure(main.getHashManager(), main.getAes(), password));

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = response.toString();
            }

            ResponseResult res = gson.fromJson(result, ResponseResult.class);

            // Check if we received auth code
            if(!res.getToken().isEmpty()) {
                auth = res.getToken();
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

        // Talk with login api -> receive results/auth token
    }

    public String register(User user, String password) {
        String result = "";

        try {

            // We need email and password
            URL url = new URL("http://127.0.0.1:8080/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            Gson gson = new Gson();
            String json = gson.toJson(user.secure(main.getHashManager(), main.getAes(), password));

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = response.toString();
            }

            ResponseResult res = gson.fromJson(result, ResponseResult.class);

            return res.getResult();

            // Hash: email:pass -> Hash: pass:key

            // Talk with register api -> receive results
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Failed";
    }

    public User get(User user, String password) {
        String originalEmail = user.getEmail();

        // Talk with get api (requires auth token)
        String result = "";

        try {

            // We need email and password
            URL url = new URL("http://127.0.0.1:8080/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", auth);
            conn.setDoOutput(true);

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = response.toString();
            }

            Gson gson = new Gson();

            // Get encrypted object
            User res = gson.fromJson(result, User.class);

            res.setEmail(originalEmail);

            // Unecrypt

            return res.decrypt(main.getAes(), main.getHashManager(), password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String update(User user, String password) {
        String result = "";

        try {
            URL url = new URL("http://127.0.0.1:8080/update");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", auth);
            conn.setDoOutput(true);

            Gson gson = new Gson();
            String json = gson.toJson(user.secure(main.getHashManager(), main.getAes(), password));

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = response.toString();
            }

            ResponseResult res = gson.fromJson(result, ResponseResult.class);

            return res.getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Failed";
    }
}
