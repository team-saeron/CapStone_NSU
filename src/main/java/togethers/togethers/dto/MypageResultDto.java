package togethers.togethers.dto;

import lombok.Builder;

public class MypageResultDto extends SignUpResultDto{
    @Builder
    public MypageResultDto(boolean success, int code, String msg){
        super(success, code, msg);
    }
}
