package top.keyto.blog.api.exception;

/**
 * @author Keyto
 * Created on 2019/12/2
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
