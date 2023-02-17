package togethers.togethers.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class PostUpRequestDto {
    private String title;
    private Boolean getType;
    private Boolean roomType;
    private String mounthly;
    private String lease;
    private String context;


}
