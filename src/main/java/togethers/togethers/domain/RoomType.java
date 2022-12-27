package togethers.togethers.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long roomType_id;

    private int roomType_value;
}
// 월,전세인지 나타내는 클래스