package togethers.togethers.entity;

import lombok.*;
import togethers.togethers.dto.PostUpRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
public class Post {

    public Post(){};

    public Post(PostUpRequestDto postUpRequestDto)
    {
        this.title = postUpRequestDto.getTitle();
        this.context = postUpRequestDto.getText();
        this.publishDate = new Date();
        this.lease = postUpRequestDto.getLease();
        this.mouthly = postUpRequestDto.getMouthly();

        if(postUpRequestDto.getGetType()==true) //true면 룸메만 구하는 경우, false면 룸메랑 집을 구하는경우
            this.RoomMate_type = 0;
        else
            this.RoomMate_type = 1;

        if(postUpRequestDto.getRoomType()==true) //true면 월세, false면 전세
            this.RoomPay_type = 0;
        else
            this.RoomPay_type = 1;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long post_id;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private User user;



    private String title;



    @Column(columnDefinition = "TEXT")
    private String context;

//    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;


    private String mouthly;


    private String lease;


    private Integer RoomMate_type;
    private Integer RoomPay_type;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Like>likes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Category area;

    @OneToMany(mappedBy = "post")
    List<RoomPicture>images = new ArrayList<>();




    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Reply>replies = new ArrayList<>();

    //postForm을 받아와 Post생성자를 통해 DB에 저장할 Post 생성





}
