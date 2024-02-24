package result;

public class RegisterResult {
    private String username;
    private String authtoken;
    private String errorMessage;
    public RegisterResult(String username, String authtoken){
        this.username = username;
        this.authtoken = authtoken;
    }
    public RegisterResult(String errorMsg){
        this.errorMessage = errorMsg;
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
