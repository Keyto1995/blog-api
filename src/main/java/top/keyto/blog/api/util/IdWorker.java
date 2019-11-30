package top.keyto.blog.api.util;

import java.io.Serializable;

/**
 * @author Keyto
 * Created on 2019/10/29
 */
public interface IdWorker<PK extends Serializable> {
    PK nextId();
}
