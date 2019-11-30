package top.keyto.blog.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.keyto.blog.api.entity.SysRole;

/**
 * @author Keyto
 * Created on 2019/11/10
 */
public interface SysRoleDao extends JpaRepository<SysRole, Long> {
}
