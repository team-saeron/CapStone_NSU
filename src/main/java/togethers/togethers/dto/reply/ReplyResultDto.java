package togethers.togethers.dto.reply;

import lombok.*;
import togethers.togethers.dto.BaseResultDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
public class ReplyResultDto extends BaseResultDto {


    private Long userId;
    private Long postId;
    private String comment;

    @Builder
    public ReplyResultDto(boolean success, int code, String msg,
                          Long userId, Long postId, String comment) {
        super(success, code, msg);
        this.userId = userId;
        this.postId = postId;
        this.comment = comment;
    }
}
