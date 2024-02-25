package result;

public class LoginResult {
    private String username;
    private String authtoken;
    private String errorMessage;

    public LoginResult(String username, String authtoken) {
        this.username = username;
        this.authtoken = authtoken;
    }

    public LoginResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
