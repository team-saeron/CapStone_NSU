package togethers.togethers.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long reply_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;


    @Column(columnDefinition = "TEXT")
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="post_id")
    private Post post;
}
