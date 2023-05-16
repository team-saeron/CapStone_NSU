package togethers.togethers.dto.mypage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckIntroductionDto {

    private String nickname;
    private String gender;
    private String area;
    private String room_type;
    private int deposit;
    private int month_fee;
    private String mbti;
    private String pet;
    private String smoking;
    private String lifeCycle;
    private String wishRoommate;

}
