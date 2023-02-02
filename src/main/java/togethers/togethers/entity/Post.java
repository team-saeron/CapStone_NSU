package togethers.togethers.entity;

import lombok.Data;
import togethers.togethers.service.form.Postform;

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

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private User user;


    private String title;



    @Column(columnDefinition = "TEXT")
    private String context;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;


    private String mouthly;


    private String lease;


    private int get_type;


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

        this.mouthly = postform.getMouthly();
        this.lease = postform.getLease();
        this.context = postform.getText();
        this.publishDate = new Date();
    }

    public Post(Long post_id, String title, String context, Date publishDate, String mouthly, String lease) {
        this.post_id = post_id;
        this.title = title;
        this.context = context;
        this.publishDate = publishDate;
        this.mouthly = mouthly;
        this.lease = lease;
    }


}
