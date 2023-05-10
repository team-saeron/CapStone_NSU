package togethers.togethers.dto.mypage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

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
    private String life_cycle;
    private String wish_roommate;

}
