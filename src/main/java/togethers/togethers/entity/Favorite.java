package togethers.togethers.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Data
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// pk값, 값이 들어올때마다 pk가 자동으로 증가함
    private Long favorite_id;


    @Column(nullable = false)
    private Boolean MyFavorite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Post post;
}