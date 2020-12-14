package org.legitzxdevelopment.user;

/**
 * Contributor(s): Luciano K
 * Description: Account POJO
 */
public class Account {
    private String url;
    private String name;
    private String username;
    private String password;

    public Account(String url, String name, String username, String password) {
        this.url = url;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
