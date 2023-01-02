package togethers.togethers.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// pk값, 값이 들어올때마다 pk가 자동으로 증가함
    private Long like_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
