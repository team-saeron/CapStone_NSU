package togethers.togethers.dto.reply;

import lombok.*;
import togethers.togethers.dto.BaseResultDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
public class ReplyDeleteResultDto extends BaseResultDto {

    private Long userId;
    private Long replyId;


    @Builder
    public ReplyDeleteResultDto(boolean success, int code, String msg) {
        super(success, code, msg);
    }

}
