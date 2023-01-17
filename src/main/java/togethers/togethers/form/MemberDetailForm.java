package togethers.togethers.form;

import lombok.Data;
import togethers.togethers.entity.*;

@Data
public class MemberDetailForm {
    Long memberDetail_Id;
    int mbti;
    String selfIntro;
    String wish_roommate;
    User user;
    String mouthly_fee;
    String lease_fee;
    int area_id;

    int smoking;
    int pet;
    int roomType;
    int lifeCycle;
    int sex;


}