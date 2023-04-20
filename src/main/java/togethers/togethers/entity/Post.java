package togethers.togethers.entity;

import lombok.*;
import togethers.togethers.dto.post.LeasePostRequestDto;
import togethers.togethers.dto.post.MonthlyPostRequestDto;
import togethers.togethers.dto.post.PostEditRequestDto;
import togethers.togethers.dto.post.PostUpRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Integer;


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
        this.deposit = postUpRequestDto.getLease();

        if(postUpRequestDto.getRoomType()==true) //true면 월세, false면 전세
            this.RoomPay_type = 0;
        else
            this.RoomPay_type = 1;
    }

    public Post(MonthlyPostRequestDto Dto)
    {
        this.title = Dto.getTitle();
        this.context = Dto.getContext();
        this.area = Dto.getAreaName();
        this.deposit = Dto.getDeposit();
        this.monthly = Dto.getMonthly();
        this.management_fee = Dto.getManagement_fee();
        this.publishDate = new Date();
        this.RoomPay_type = 0; //0이면 월세라는 뜻.
    }

    public Post(LeasePostRequestDto Dto)
    {
        this.title = Dto.getTitle();
        this.context = Dto.getContext();
        this.deposit = Dto.getDeposit();
        this.area = Dto.getArea();
        this.management_fee = Dto.getManagement_fee();
        this.publishDate = new Date();
        this.RoomPay_type = 1; //1이면 월세라는 뜻.
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


    private String monthly;


    private String deposit;

    private String management_fee;

    private Integer RoomPay_type;
    private String area;

//    @OneToMany(mappedBy = "post",cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
//            orphanRemoval = true)
//    List<RoomPicture>images = new ArrayList<>();

//    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
//    RoomPicture photo;

    //post가 filename을 알고있는 상태라면? --> post.filename으로 출력?

    private String fileName;






    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Reply>replies = new ArrayList<>();



    public String getUid()
    {
        return this.user.getUid();
    }




    public void PostEdit(PostEditRequestDto postEditRequestDto)
    {
        this.title = postEditRequestDto.getTitle();
        this.context = postEditRequestDto.getContext();
        this.deposit = postEditRequestDto.getLease();
        this.monthly = postEditRequestDto.getMounthly();

        if(postEditRequestDto.getRoomType()==true)
        {
            this.RoomPay_type = 0;
        }else{
            this.RoomPay_type = 1;
        }
    }




}
