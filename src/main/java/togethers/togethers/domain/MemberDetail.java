package togethers.togethers.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MemberDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long memberdetail_id;



    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @Column(nullable = false)
    private int mbti;

    @Column(nullable = false)
    @Lob
    private String selfIntro;

    @Column(nullable = false)
    @Lob
    private String wish_roommate;

    @Column(nullable = false)
    private int mouthly_fee;

    @Column(nullable = false)
    private int lease_fee;



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
    private Sex sex;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id")
    private Category category;





}
