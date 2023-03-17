package togethers.togethers.dto;

import lombok.*;

@RequiredArgsConstructor
@Data
public class LikeDto {
    public Boolean like;
    private Long postId;
}
