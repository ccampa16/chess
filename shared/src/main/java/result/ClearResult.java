package result;

public class ClearResult {
    private String message;
    //private boolean success;
    public ClearResult(String message){
        this.message = message;
//        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
