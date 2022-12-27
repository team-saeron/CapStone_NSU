package togethers.togethers.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Room implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long room_id;

    private int monthly;

    private int lease;

    private int get_type; // 룸메만 구하는지 or 집과 룸메만 구하는지

    @OneToOne
    @JoinColumn(name = "roomType_id")
    private RoomType roomType;
}
