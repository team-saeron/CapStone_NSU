package togethers.togethers.dto.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDetailUpdateDto {
    private String mbti;
    private String wishRoommate;
    private int monthlyFee;
    private String regions;
    private int leaseFee;
    private String lifeCycle;
    private String smoking;
    private String pet;


    public UserDetailUpdateDto(String mbti, String wishRoommate, int monthlyFee, String regions, int leaseFee, String lifeCycle, String smoking, String pet){
        this.mbti=mbti;
        this.wishRoommate = wishRoommate;
        this.monthlyFee = monthlyFee;
        this.regions=regions;
        this.leaseFee = leaseFee;
        this.lifeCycle = lifeCycle;
        this.smoking=smoking;
        this.pet=pet;
    }

}