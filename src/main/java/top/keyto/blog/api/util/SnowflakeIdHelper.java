package top.keyto.blog.api.util;

import top.keyto.blog.api.util.impl.SnowflakeIdWorker;

/**
 * @author Keyto
 * Created on 2019/10/29
 */
public class SnowflakeIdHelper {
    private static IdWorker<Long> idWorker = new SnowflakeIdWorker(0, 0);

    public static Long nextId() {
        return idWorker.nextId();
    }
}
