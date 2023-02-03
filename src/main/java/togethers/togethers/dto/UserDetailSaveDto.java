package togethers.togethers.dto;

import lombok.*;
import togethers.togethers.entity.User;
import togethers.togethers.entity.UserDetail;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


    public UserDetail toEntity(User user){
        return UserDetail.builder()
                .nickname(nickname)
                .mbti(mbti)
                .wish_roommate(wish_roommate)
                .monthly_fee(monthly_fee)
                .regions(regions)
                .lease_fee(lease_fee)
                .life_cycle(life_cycle)
                .smoking(smoking)
                .sex(sex)
                .pet(pet)
                .build();
    }
}
