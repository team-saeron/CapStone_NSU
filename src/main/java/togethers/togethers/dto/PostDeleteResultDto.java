package togethers.togethers.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
public class PostDeleteResultDto extends BaseResultDto{

    private Long PostId;

    @Builder
    public PostDeleteResultDto(boolean success, int code, String msg) {
        super(success, code, msg);
    }
}
