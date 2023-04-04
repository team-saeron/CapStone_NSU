package togethers.togethers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDetailUpdateDto {
    private String mbti;
    private String wish_roommate;
    private int monthly_fee;
    private String nickname;
    private String regions;
    private int lease_fee;
    private String life_cycle;
    private String smoking;
    private String pet;


    public UserDetailUpdateDto(String mbti, String wish_roommate, int monthly_fee, String nickname, String regions, int lease_fee, String life_cycle, String smoking, String pet){
        this.mbti=mbti;
        this.wish_roommate=wish_roommate;
        this.monthly_fee=monthly_fee;
        this.nickname=nickname;
        this.regions=regions;
        this.lease_fee=lease_fee;
        this.life_cycle=life_cycle;
        this.smoking=smoking;
        this.pet=pet;
    }

}