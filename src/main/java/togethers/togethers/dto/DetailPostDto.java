package togethers.togethers.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import togethers.togethers.entity.Reply;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Builder
public class DetailPostDto {

    private Long postId;

    private Long userId;
    private String Uid;

    private String title;
    private String context;
    private Integer roomPay_type;

    private String mounthly;
    private String lease;

    private String photo_name;
    private String photo_path;

    //월세, 전세

    @Builder.Default
    private List<Reply> replies = new ArrayList<>();
}
