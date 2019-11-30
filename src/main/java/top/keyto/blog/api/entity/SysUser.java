package top.keyto.blog.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * @author Keyto
 * Created on 2019/10/28
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_users")
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 3023210951055965502L;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "rel_users_roles",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<SysRole> roles;
}
