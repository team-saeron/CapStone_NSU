package togethers.togethers.data.dto;

import lombok.*;
import togethers.togethers.entity.UserDetail;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailSaveDto {
    private String mbti;
    private String wish_roommate;
    private int monthly_fee;
    private String nickname;
    private String regions;
    private int lease_fee;
    private String life_cycle;
    private String smoking;
    private String sex;
    private String pet;

//    public UserDetail toEntity(){
//        return new UserDetail(nickname, regions, mbti, wish_roommate, monthly_fee, lease_fee, sex, pet, smoking, life_cycle);
//    }

//    public UserDetailSaveDto(String mbti, String wish_roommate, int monthly_fee, String nickname, String regions, int lease_fee, String life_cycle, String smoking, String pet, String sex){
//        this.mbti = mbti;
//        this.wish_roommate=wish_roommate;
//        this.monthly_fee = monthly_fee;
//        this.nickname=nickname;
//        this.regions=regions;
//        this.lease_fee=lease_fee;
//        this.life_cycle=life_cycle;
//        this.smoking=smoking;
//        this.pet=pet;
//        this.sex=sex;
//    }
//
//    public UserDetail toEntity(){
//        return UserDetail.builder()
//                .nickname(nickname)
//                .mbti(mbti)
//                .wish_roommate(wish_roommate)
//                .monthly_fee(monthly_fee)
//                .regions(regions)
//                .lease_fee(lease_fee)
//                .life_cycle(life_cycle)
//                .smoking(smoking)
//                .sex(sex)
//                .pet(pet)
//                .build();
//    }
}
