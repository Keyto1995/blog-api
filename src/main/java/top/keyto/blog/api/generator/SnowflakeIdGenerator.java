package top.keyto.blog.api.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import top.keyto.blog.api.util.SnowflakeIdHelper;

import java.io.Serializable;

/**
 * @author Keyto
 * Created on 2019/10/29
 */
public class SnowflakeIdGenerator extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return SnowflakeIdHelper.nextId();
    }

}
