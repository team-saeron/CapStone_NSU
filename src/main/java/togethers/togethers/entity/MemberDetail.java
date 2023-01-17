package togethers.togethers.entity;


import lombok.Data;
import togethers.togethers.form.MemberDetailForm;

import javax.persistence.*;

@Entity
@Data
public class MemberDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    //닉네임
    private Long memberDetail_id;


    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private User user;



    private int mbti;

    @Lob
    private String selfIntro;

    @Lob
    private String wish_roommate;

    //월세
    private String mouthly_fee;

    //보증금
    private String lease_fee;



    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "smoking_id")
    private Smoking smoking;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "roomType_id")
    private RoomType roomType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "lifeCycle_id")
    private LifeCycle lifeCycle;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sex_id")
    private Gender sex;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id")
    private Category category;



}
