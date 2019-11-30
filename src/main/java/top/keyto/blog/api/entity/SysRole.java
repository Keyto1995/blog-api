package top.keyto.blog.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Keyto
 * Created on 2019/11/10
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_roles")
public class SysRole extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;
}
