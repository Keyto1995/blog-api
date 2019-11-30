package top.keyto.blog.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

/**
 * @author Keyto
 * Created on 2019/10/29
 */

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_articles")
public class Article extends BaseEntity {
    private static final long serialVersionUID = 115433075100393461L;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publish_date")
    private Date publishDate;

    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinTable(name = "rel_articles_tags",
        joinColumns = {@JoinColumn(name = "article_id")},
        inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
