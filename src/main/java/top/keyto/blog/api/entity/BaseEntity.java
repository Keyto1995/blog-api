package top.keyto.blog.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Keyto
 * Created on 2019/10/28
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(exclude = {"createTime", "modifiedTime"})
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "new"})
public abstract class BaseEntity implements Persistable<Long>, Serializable {
    private static final long serialVersionUID = 982997729664561107L;

    @Id
    @GeneratedValue(generator = "snowflake-idGenerator")
    @GenericGenerator(name = "snowflake-idGenerator", strategy = "top.keyto.blog.api.generator.SnowflakeIdGenerator")
    @Column(name = "id", nullable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "gmt_create", nullable = false, updatable = false)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "gmt_modified", nullable = false)
    private Date modifiedTime;

    @Override
    @Nullable
    public Long getId() {
        return id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return null == getId();
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
    }

}
