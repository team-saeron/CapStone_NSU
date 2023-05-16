package togethers.togethers.dto.post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class DetailPostDto {

    private Long postId;

    private Long userId;
    private String Uid;

    private String title;
    private String context;
    private String area;
    private Integer roomPayType;

    private String monthly;
    private String lease;


}
