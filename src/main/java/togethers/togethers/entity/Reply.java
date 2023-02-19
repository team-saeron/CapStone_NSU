package togethers.togethers.entity;

import lombok.Builder;
import lombok.Data;
import togethers.togethers.dto.ReplyRequestDto;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Reply {

    public Reply (){};

    public Reply(ReplyRequestDto replyRequestDto)
    {
        this.comment = replyRequestDto.getComment();
        this.publishedDate = replyRequestDto.getPublishedDate();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long reply_id;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;


    @Column(columnDefinition = "TEXT")
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedDate;

    @ManyToOne
    @JoinColumn(name ="post_id")
    private Post post;
}
