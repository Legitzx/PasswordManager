package org.legitzxdevelopment.api;

/**
 * Contributor(s): Luciano K
 * Description: POJO used to read API responses
 */
public class ResponseResult {
    private String result;
    private String token;

    public ResponseResult(String result, String token) {
        this.result = result;
        this.token = token;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
