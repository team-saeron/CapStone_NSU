package togethers.togethers.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long post_id;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String context;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date publishDate;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Like>likes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Category area;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Reply>replies = new ArrayList<>();


}
