package top.keyto.blog.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Keyto
 * Created on 2019/11/7
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_authors")
public class Author extends BaseEntity {
    private static final long serialVersionUID = 3125669170212581914L;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToOne
    private SysUser owner;
}
