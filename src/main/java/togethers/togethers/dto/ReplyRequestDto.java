package togethers.togethers.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Setter
@Getter
public class ReplyRequestDto {

    private String comment;
    private Date PublishedDate;
}
