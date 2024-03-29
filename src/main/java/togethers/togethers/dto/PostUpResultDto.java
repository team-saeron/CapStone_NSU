package togethers.togethers.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
public class PostUpResultDto extends BaseResultDto{
    private String title;
    private String context;


    @Builder
    public PostUpResultDto(boolean success, int code, String msg, String title, String context) {
        super(success, code, msg);
        this.title = title;
        this.context = context;
    }

}
