package togethers.togethers.entity;

import lombok.*;
import togethers.togethers.dto.PostEditRequestDto;
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
        this.context = postUpRequestDto.getContext();
        this.publishDate = new Date();
        this.lease = postUpRequestDto.getLease();
        this.mounthly = postUpRequestDto.getMounthly();

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
    private Long postId;

    @OneToOne(mappedBy = "post",fetch = FetchType.LAZY) // user로 mappedBy로 바꾸기
    private User user;



    private String title;



    @Column(columnDefinition = "TEXT")
    private String context;

    //    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;


    private String mounthly;


    private String lease;


    private Integer RoomMate_type;
    private Integer RoomPay_type;


    private Boolean like;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Category area;

    @OneToMany(mappedBy = "post",cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
            orphanRemoval = true)
    List<RoomPicture>images = new ArrayList<>();




    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Reply>replies = new ArrayList<>();

    //postForm을 받아와 Post생성자를 통해 DB에 저장할 Post 생성




    public void PostEdit(PostEditRequestDto postEditRequestDto)
    {
        this.title = postEditRequestDto.getTitle();
        this.context = postEditRequestDto.getContext();
        this.lease = postEditRequestDto.getLease();
        this.mounthly = postEditRequestDto.getMounthly();

        if(postEditRequestDto.getRoomType()==true)
        {
            this.RoomPay_type = 0;
        }else{
            this.RoomPay_type = 1;
        }
    }




}
