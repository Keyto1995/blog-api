package top.keyto.blog.api.exception;

/**
 * @author Keyto
 * Created on 2019/11/8
 */
public class RegisterException extends RuntimeException {
    public RegisterException(String message) {
        super(message);
    }
}
