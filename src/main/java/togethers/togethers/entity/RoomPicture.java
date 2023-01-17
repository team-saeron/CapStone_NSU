package togethers.togethers.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class RoomPicture{

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String filename;
    private String filepath;
}
