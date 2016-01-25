package ConnectionObject;

/**
 * Created by Francois on 13/01/2016.
 */
public class LoginObject {
    ErrorObject error = null;
    String token = null;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public ErrorObject getError() {
        return error;
    }

    public void setError(ErrorObject error) {
        this.error = error;
    }

    public class ErrorObject {
        String code = null;
        String message = null;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}