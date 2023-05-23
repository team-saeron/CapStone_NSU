package togethers.togethers.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class AwsFileUrl {
    public AwsFileUrl(Post post,String filepath) {
        this.post = post;
        this.filepath = filepath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String filepath;
}
