package togethers.togethers.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name="user_detail")
@NoArgsConstructor
public class UserDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "userDetail_id",unique = true)
    private Long userDetail_id;

    @OneToOne(mappedBy = "userDetail")
    private User user;

    @Column(nullable = false)
    private String mbti;

//    @Column(nullable = false)
//    @Lob
//    private String selfIntro;

    @Column(nullable = false)
    @Lob
    private String wish_roommate;

    @Column(nullable = false)
    private int monthly_fee;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String regions;
    @Column(nullable = false)
    private int lease_fee;

    @Column(nullable = false)
    private String life_cycle;

    @Column(nullable = false)
    private String smoking;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private String pet;

//    @Column(nullable = false)
//    private String room_type;

    @Builder
    public UserDetail(String nickname, String regions, String mbti, String wish_roommate, int monthly_fee, int lease_fee, String sex, String pet, String smoking, String life_cycle){
        this.regions=regions;
        this.mbti=mbti;
        this.wish_roommate = wish_roommate;
        this.monthly_fee=monthly_fee;
        this.lease_fee = lease_fee;
        this.sex=sex;
        this.pet=pet;
        this.smoking = smoking;
        this.life_cycle = life_cycle;
        this.nickname=nickname;
    }

    public static UserDetail createIntro(String nickname, String regions, String mbti, String wish_roommate, int monthly_fee, int lease_fee, String sex, String pet, String smoking, String life_cycle){
        UserDetail userDetail = new UserDetail();
        userDetail.regions=regions;
        userDetail.mbti=mbti;
        userDetail.wish_roommate = wish_roommate;
        userDetail.monthly_fee=monthly_fee;
        userDetail.lease_fee = lease_fee;
        userDetail.sex=sex;
        userDetail.pet=pet;
        userDetail.smoking = smoking;
        userDetail.life_cycle = life_cycle;
        userDetail.nickname=nickname;
        return userDetail;
    }

}
