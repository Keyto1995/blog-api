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
 * Created on 2019/11/7
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_tags")
public class Tag extends BaseEntity {
    private static final long serialVersionUID = 681571265538129485L;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String url;

    @Column
    private String description;

}
