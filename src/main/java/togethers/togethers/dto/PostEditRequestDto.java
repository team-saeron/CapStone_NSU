package togethers.togethers.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class PostEditRequestDto {

    private String title;
    private Boolean getType;
    private Boolean roomType;
    private String mounthly;
    private String lease;
    private String context;
}
