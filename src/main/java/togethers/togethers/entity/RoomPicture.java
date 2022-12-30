package togethers.togethers.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class RoomPicture{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long roompicture_id;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Lob
    private byte[] pictures_1;

    @Lob
    private byte[] pictures_2;

    @Lob
    private byte[] pictures_3;

    @Lob
    private byte[] pictures_4;

    @Lob
    private byte[] pictures_5;

    @Lob
    private byte[] pictures_6;
}
