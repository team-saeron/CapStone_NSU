package togethers.togethers.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import togethers.togethers.dto.UserDetailSaveDto;
import togethers.togethers.dto.UserDetailUpdateDto;

import javax.persistence.*;

@Entity
@Data
@Table(name="user_detail")
@NoArgsConstructor
public class UserDetail {


    public UserDetail(UserDetailSaveDto dto) {
        this.mbti = dto.getMbti();
        this.wish_roommate = dto.getWish_roommate();
        this.monthly_fee = dto.getMonthly_fee();
        this.regions = dto.getRegions();
        this.lease_fee = dto.getLease_fee();
        this.life_cycle = dto.getLife_cycle();
        this.smoking = dto.getSmoking();
        this.gender = dto.getGender();
        this.pet = dto.getPet();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "userDetail_id",unique = true)
    private Long userDetailId;

    @OneToOne(mappedBy = "userDetail")
    private User user;

    @Column(nullable = false)
    private String mbti;

    @Column(nullable = false)
    @Lob
    private String wish_roommate;

    @Column(nullable = false)
    private int monthly_fee;

    @Column(nullable = false)
    private String regions;
    @Column(nullable = false)
    private int lease_fee;

    @Column(nullable = false)
    private String life_cycle;

    @Column(nullable = false)
    private String smoking;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String pet;





    public void updateUserDetail(UserDetailUpdateDto userDetailUpdateDto){
        this.regions = userDetailUpdateDto.getRegions();
        this.mbti = userDetailUpdateDto.getMbti();
        this.wish_roommate = userDetailUpdateDto.getWish_roommate();
        this.monthly_fee = userDetailUpdateDto.getMonthly_fee();
        this.lease_fee=userDetailUpdateDto.getLease_fee();
        this.pet=userDetailUpdateDto.getPet();
        this.smoking = userDetailUpdateDto.getSmoking();
        this.life_cycle = userDetailUpdateDto.getLife_cycle();
    }



}
