package togethers.togethers.entity;

import lombok.Data;
import togethers.togethers.form.Postform;

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


    private String title;



    @Column(columnDefinition = "TEXT")
    private String context;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;


    private String monthly;


    private String lease;


    private int get_type;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Like>likes = new ArrayList<>();


    @OneToOne
    @JoinColumn(name = "roomType_id")
    private RoomType roomType;


    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Category area;




    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Reply>replies = new ArrayList<>();

    //postForm을 받아와 Post생성자를 통해 DB에 저장할 Post 생성

    public Post(){};
    public Post(Postform postform)
    {
        this.title = postform.getTitle();
        if(postform.isGetType()==true)
        {
            this.get_type = 0;
        }
        else {
            this.get_type = 1;
        }

        this.monthly = postform.getMouthly();
        this.lease = postform.getLease();
        this.context = postform.getText();
        this.publishDate = new Date();

    }


}
