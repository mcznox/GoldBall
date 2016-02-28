package znox.Type.Exception;

/**
 * <h1>Exception Class</h1>
 * <h2>Class for throw custom exceptions for errors of mini game</h2>
 *
 * @author zNoX_
 * @version 1.0.4
 */
public class SessionException extends RuntimeException {

    String message;
    String method;

    public SessionException(String method, String message) {
        this.message = message;
        this.method = method;
    }

    @Override
    public String getMessage() { return "Error occurred in '" + method + "' method: " + message + "."; }

}
