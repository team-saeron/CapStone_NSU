package togethers.togethers.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = true,unique = true)
    private Long area_id;

    @Column(length = 50, nullable = false)
    private String area;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "roomType_id")
//    @Column(unique = true)
    private RoomType roomType;
}
