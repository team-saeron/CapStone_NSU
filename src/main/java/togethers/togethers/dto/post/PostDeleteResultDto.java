package togethers.togethers.dto.post;

import lombok.*;
import togethers.togethers.dto.BaseResultDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
public class PostDeleteResultDto extends BaseResultDto {

    private Long PostId;

    @Builder
    public PostDeleteResultDto(boolean success, int code, String msg) {
        super(success, code, msg);
    }
}
