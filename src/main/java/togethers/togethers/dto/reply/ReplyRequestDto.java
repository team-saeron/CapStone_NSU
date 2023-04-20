package togethers.togethers.dto.reply;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@Builder
public class ReplyRequestDto {



    private Long postId;
    private Long id;
    private String comment;
}
